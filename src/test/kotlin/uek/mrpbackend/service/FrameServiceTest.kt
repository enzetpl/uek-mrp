package uek.mrpbackend.service

import org.junit.jupiter.api.Test

import uek.mrpbackend.controller.MrpData

class FrameServiceTest {

    val frameService: FrameService = FrameService()

    @Test
    fun blaty() {
        val requestedItems =
            mutableListOf(
                RequestedItem(0, 0),
                RequestedItem(1, 0),
                RequestedItem(2, 0),
                RequestedItem(3, 28),
                RequestedItem(4, 0),
                RequestedItem(5, 30),
                RequestedItem(6, 0),
                )
        val mrpData = MrpData(22, 40, 3)
        frameService.getTables(requestedItems, mrpData)
    }

    @Test
    fun plyta() {
        val requestedItems =
            mutableListOf(
                RequestedItem(0, 40),
                RequestedItem(1, 0),
                RequestedItem(2, 0),
                RequestedItem(3, 0),
                RequestedItem(4, 0),
                RequestedItem(5, 0),
                RequestedItem(6, 0),
            )
        val mrpData = MrpData(10, 50, 1)
        frameService.getTables(requestedItems, mrpData)
    }

    @Test
    fun legs() {
        val requestedItems =
            mutableListOf(
                RequestedItem(0, 0),
                RequestedItem(1, 0),
                RequestedItem(2, 0),
                RequestedItem(3, 112),
                RequestedItem(4, 0),
                RequestedItem(5, 120),
                RequestedItem(6, 0),
            )
        val mrpData = MrpData(40, 120, 2)
        frameService.getTables(requestedItems, mrpData)
    }
}