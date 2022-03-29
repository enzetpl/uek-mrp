package uek.mrpbackend.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import uek.mrpbackend.controller.*

class MrpServiceTest {

    val frameService: FrameService = FrameService()
    val service: MrpService = MrpService(frameService)

    @Test
    fun `Przemek - przyklad 1`() {
        val request = MrpRequest(
            BedData(0, 1, listOf(0, 0, 0, 5, 0, 0, 25), listOf(0, 0, 0, 20, 0, 0, 10)),
            MrpData(0, 5, 2),
            MrpData(0, 5, 2),
            MrpData(0, 20, 1),
            MrpData(0, 100, 1),
            MrpData(0, 30, 2),
        )
        val a = service.get(request)

        assertThat(a.frame.totalDemand).isEqualTo(listOf(0,0,20,0,0,10,0))
        assertThat(a.frame.plannedReceipts).isEqualTo(listOf(0,0,0,0,0,0,0))
        assertThat(a.frame.expectedInStock).isEqualTo(listOf(0,0,0,0,0,0, 0))
        assertThat(a.frame.totalDemand).isEqualTo(listOf(0,0,20,0,0,10,0))
        assertThat(a.frame.plannedOrders).isEqualTo(listOf(20,0,0,10,0,0,0))
        assertThat(a.frame.plannedOrderIntake).isEqualTo(listOf(0,0,20,0,0,10,0))

        assertThat(a.stand.totalDemand).isEqualTo(listOf(0,0,20,0,0,10,0))
        assertThat(a.stand.plannedReceipts).isEqualTo(listOf(0,0,0,0,0,0,0))
        assertThat(a.stand.expectedInStock).isEqualTo(listOf(0,0,0,0,0,0, 0))
        assertThat(a.stand.totalDemand).isEqualTo(listOf(0,0,20,0,0,10,0))
        assertThat(a.stand.plannedOrders).isEqualTo(listOf(20,0,0,10,0,0,0))
        assertThat(a.stand.plannedOrderIntake).isEqualTo(listOf(0,0,20,0,0,10,0))

        assertThat(a.legs.totalDemand).isEqualTo(listOf(0,0,80,0,0,40,0))
        assertThat(a.legs.plannedReceipts).isEqualTo(listOf(0,0,0,0,0,0,0))
        assertThat(a.legs.expectedInStock).isEqualTo(listOf(0,0,0,0,0,0, 0))
        assertThat(a.legs.totalDemand).isEqualTo(listOf(0,0,80,0,0,40,0))
        assertThat(a.legs.plannedOrders).isEqualTo(listOf(0,80,0,0,40,0,0))
        assertThat(a.legs.plannedOrderIntake).isEqualTo(listOf(0,0,80,0,0,40,0))

        assertThat(a.smallBoards.totalDemand).isEqualTo(listOf(400,0,0,200,0,0,0))
        assertThat(a.smallBoards.plannedReceipts).isEqualTo(listOf(400,0,0,0,0,0,0))
        assertThat(a.smallBoards.expectedInStock).isEqualTo(listOf(0,0,0,0,0,0, 0))
        assertThat(a.smallBoards.totalDemand).isEqualTo(listOf(400,0,0,200,0,0,0))
        assertThat(a.smallBoards.plannedOrders).isEqualTo(listOf(0,0,200,0,0,0,0))
        assertThat(a.smallBoards.plannedOrderIntake).isEqualTo(listOf(0,0,0,200,0,0,0))

        assertThat(a.bigBoards.totalDemand).isEqualTo(listOf(80,0,0,40,0,0,0))
        assertThat(a.bigBoards.plannedReceipts).isEqualTo(listOf(80,0,0,0,0,0,0))
        assertThat(a.bigBoards.expectedInStock).isEqualTo(listOf(0,0,0,20,20,20, 20))
        assertThat(a.bigBoards.totalDemand).isEqualTo(listOf(80,0,0,40,0,0,0))
        assertThat(a.bigBoards.plannedOrders).isEqualTo(listOf(0,60,0,0,0,0,0))
        assertThat(a.bigBoards.plannedOrderIntake).isEqualTo(listOf(0,0,0,60,0,0,0))
    }
}