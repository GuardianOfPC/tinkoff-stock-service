package com.alexeyodinochenko.tinkoffservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception

@ControllerAdvice
class ExceptionController: ResponseEntityExceptionHandler() {

    @ExceptionHandler(StockNotFoundException::class)
    fun handleNotFound(ex: Exception): ResponseEntity<ErrorDto> {
        return ResponseEntity<ErrorDto>(ErrorDto(ex.localizedMessage), HttpStatus.NOT_FOUND)
    }
}