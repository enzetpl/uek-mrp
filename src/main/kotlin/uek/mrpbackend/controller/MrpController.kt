package uek.mrpbackend.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import uek.mrpbackend.service.Dataaa
import uek.mrpbackend.service.MrpService

@RestController
@RequestMapping("/api/tables")
class MrpController(val mrpService: MrpService) {

    @PostMapping
    fun getTables(@RequestBody mrpRequest: MrpRequest): MrpResponse {
        return mrpService.get(mrpRequest)
    }


    @ExceptionHandler(NotEnoughItemException::class)
    fun handleContentNotAllowedException(cnae: NotEnoughItemException): ResponseEntity<ApiError> {

        return ResponseEntity<ApiError>(ApiError(cnae.msg), HttpStatus.BAD_REQUEST)
    }
}

data class ApiError(val message: String)

data class NotEnoughItemException(val msg: String) : RuntimeException(msg)

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
    val available: List<Int>
)
