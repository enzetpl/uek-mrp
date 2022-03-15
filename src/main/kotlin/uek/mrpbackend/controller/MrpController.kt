package uek.mrpbackend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uek.mrpbackend.service.Dataaa
import uek.mrpbackend.service.MrpService

@RestController
@RequestMapping("/api/tables")
class MrpController(val mrpService: MrpService) {

    @GetMapping
    fun getTables(@RequestBody mrpRequest: MrpRequest): MrpResponse {
        return mrpService.get(mrpRequest)
    }
}

data class MrpRequest(
    val bed: BedData,
    val frame: MrpData,
    val stand: MrpData,
    val legs: MrpData,
    val smallBoards: MrpData,
    val bigBoards: MrpData
)

data class BedData(
    val startAvaibility: Int,
    val realizationTime: Int,
    val expectedDemand: List<Int>,
    val production: List<Int>
)

data class MrpData(
    val startAvaibility: Int,
    val batchSize: Int,
    val realizationTime: Int
)

data class MrpResponse(
    val bed: GhpTable,
    val frame: Dataaa,
    val stand: Dataaa,
    val legs: Dataaa,
    val smallBoards: Dataaa,
    val bigBoards: Dataaa,
)

data class GhpTable(
    val expectedDemand: List<Int>,
    val production: List<Int>,
    val available :List<Int>
)
