package ru.perm.v.shopkotlin.rest

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/echo")
@Slf4j
@Api(tags = ["Echo for test"])
class EchoCtrl {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    @GetMapping("/{mes}")
    @ApiOperation("echo message")
    fun echoStr(
        @PathVariable("mes")
        @ApiParam(name = "mes", value = "any string") mes: String
    ): String {
        logger.info("GET $mes")
        return mes
    }
}