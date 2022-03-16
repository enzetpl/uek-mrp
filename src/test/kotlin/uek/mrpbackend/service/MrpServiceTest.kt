package uek.mrpbackend.service

import org.junit.jupiter.api.Test
import uek.mrpbackend.controller.BedData
import uek.mrpbackend.controller.MrpData
import uek.mrpbackend.controller.MrpRequest

class MrpServiceTest {

    val frameService: FrameService = FrameService()
    val service: MrpService = MrpService(frameService)

    @Test
    fun zad1() {
        val request = MrpRequest(BedData(2, 1, listOf(0,0,0,0,20,0,40), listOf(0,0,0,0,28,0,30)),
        MrpData(22,40,3),
            MrpData(22,40,3),
            MrpData(40,120,2),
            MrpData(10,50,1),
            MrpData(10,50,1),
        )
        service.get(request)
    }
}