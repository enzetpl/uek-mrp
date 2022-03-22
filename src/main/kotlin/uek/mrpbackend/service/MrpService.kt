package uek.mrpbackend.service

import org.springframework.stereotype.Service
import uek.mrpbackend.controller.GhpTable
import uek.mrpbackend.controller.MrpRequest
import uek.mrpbackend.controller.MrpResponse

@Service
class MrpService(val frameService: FrameService) {

    private val BOARDS_IN_STAND: Int = 20
    private val BOARDS_IN_FRAME: Int = 4
    private val LEGS_IN_BED: Int = 4

    fun get(mrpRequest: MrpRequest): MrpResponse {
        val requestedFrames = mutableListOf<RequestedItem>()
        val requestedLegs = mutableListOf<RequestedItem>()
            val requestedStands = mutableListOf<RequestedItem>()

        var avaibility = mrpRequest.bed.startAvaibility
        val ghpAvailable = mrpRequest.bed.production.mapIndexed { index, i ->
            avaibility = avaibility - mrpRequest.bed.expectedDemand[index] + i
            avaibility
        }
        List(mrpRequest.bed.expectedDemand.size) { index ->
            if (index - mrpRequest.bed.realizationTime >= 0) {
                requestedFrames += RequestedItem(
                    index - mrpRequest.bed.realizationTime,
                    mrpRequest.bed.production[index]
                )
                requestedLegs += RequestedItem(
                    index - mrpRequest.bed.realizationTime,
                    mrpRequest.bed.production[index] * LEGS_IN_BED
                )
                requestedStands += RequestedItem(
                    index - mrpRequest.bed.realizationTime,
                    mrpRequest.bed.production[index]
                )
            }
        }
        while(requestedLegs.size < mrpRequest.bed.production.size) {
            requestedFrames += RequestedItem(requestedFrames.size, 0)
            requestedStands += RequestedItem(requestedStands.size, 0)
            requestedLegs += RequestedItem(requestedLegs.size, 0)
        }
        val frameTable = frameService.getTables(requestedFrames, mrpRequest.frame, "frame")
        val standTable = frameService.getTables(requestedStands, mrpRequest.stand, "stand")
        val legsTable = frameService.getTables(requestedLegs, mrpRequest.legs, "legs")
        val bigBoards = frameService.getTables(frameTable.plannedOrders.mapIndexed { index, i ->
            RequestedItem(
                index,
                i * BOARDS_IN_FRAME
            )
        }, mrpRequest.bigBoards, "bigBoards")
        val smallBoards = frameService.getTables(standTable.plannedOrders.mapIndexed { index, i ->
            RequestedItem(
                index,
                i * BOARDS_IN_STAND
            )
        }, mrpRequest.smallBoards, "smallBoards")
        return MrpResponse(
            GhpTable(mrpRequest.bed.expectedDemand, mrpRequest.bed.production, ghpAvailable),
            frameTable,
            standTable,
            legsTable,
            smallBoards,
            bigBoards
        )
    }
}

data class RequestedItem(
    val week: Int,
    val size: Int
)
