package uek.mrpbackend.service

import org.springframework.stereotype.Service
import uek.mrpbackend.controller.MrpData
import kotlin.math.absoluteValue

@Service
class FrameService {
    fun getTables(requestedFrames: List<RequestedItem>, mrpData: MrpData, type: String): Dataaa {
        val finalData = Dataaa(type, requestedFrames.size)
        requestedFrames.mapIndexed { index, requestedItem ->
            finalData.totalDemand[index] = requestedItem.size
            val expectedMinusOneWeek = if(index - 1 >= 0) finalData.expectedInStock[index - 1] else mrpData.startAvaibility
            val expectedInStock = (expectedMinusOneWeek - requestedItem.size)
            if(expectedInStock < 0) {
                val netDemand = expectedInStock.absoluteValue
                finalData.netDemand[index] = netDemand
                val plannedOrderIntake = getPlannedIntake(netDemand, mrpData.batchSize)
                if(plannedOrderIntake > 0) {
                    if(index - mrpData.realizationTime >= 0) {
                        finalData.plannedOrders[index - mrpData.realizationTime] = plannedOrderIntake*mrpData.batchSize
                        finalData.plannedOrderIntake[index] = plannedOrderIntake*mrpData.batchSize
                        finalData.expectedInStock[index] = expectedMinusOneWeek + plannedOrderIntake*mrpData.batchSize - requestedItem.size
                    } else {
                        finalData.plannedReceipts[index] = requestedItem.size - expectedMinusOneWeek
                        finalData.expectedInStock[index] = 0
                    }
                }
            } else {
                finalData.expectedInStock[index] = expectedInStock
            }
        }
        return finalData
    }

    private fun getPlannedIntake(netDemand: Int, batchSize: Int): Int {
        var stan = netDemand*(-1)
        var parties = 0
        while (stan < 0) {
            stan += batchSize
            parties ++
        }
        return parties
    }

}

class Dataaa(val type: String, size: Int) {

    val totalDemand: MutableList<Int>
    val plannedReceipts: MutableList<Int>
    val expectedInStock: MutableList<Int>
    val netDemand: MutableList<Int>
    val plannedOrders: MutableList<Int>
    val plannedOrderIntake: MutableList<Int>

    init {
        totalDemand = MutableList(size) { 0 }
        plannedReceipts = MutableList(size) { 0 }
        expectedInStock = MutableList(size) { 0 }
        netDemand = MutableList(size) { 0 }
        plannedOrders = MutableList(size) { 0 }
        plannedOrderIntake = MutableList(size) { 0 }
    }
}

