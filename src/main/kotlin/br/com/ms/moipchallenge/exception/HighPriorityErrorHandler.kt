package br.com.ms.moipchallenge.exception

import br.com.ms.moipchallenge.exception.ErrorObject.Companion.toErrorObject
import br.com.ms.moipchallenge.exception.ErrorResponse.Companion.buildResponse
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
class HighPriorityErrorHandler {

    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException) =
            buildResponse("Invalid data", BAD_REQUEST, ex.bindingResult.fieldErrors.map { toErrorObject(it) })

    @ExceptionHandler(value = [(EntityNotFoundException::class)])
    protected fun handleEntityNotFoundException(ex: EntityNotFoundException) =
            buildResponse("Entity not found", NOT_FOUND, listOf(ErrorObject(ex.message, ex.field, ex.parameter)))

    @ExceptionHandler(value = [(MissingKotlinParameterException::class)])
    protected fun handleMissingKotlinParameterException(ex: MissingKotlinParameterException): ResponseEntity<ErrorResponse> {
        val responseMessage = "Mandatory parameters from ${ex.path[0].fieldName} weren't sent"
        val error = ErrorObject("Missing parameter", ex.path[1].fieldName)
        return buildResponse(responseMessage, BAD_REQUEST, listOf(error))
    }
}
