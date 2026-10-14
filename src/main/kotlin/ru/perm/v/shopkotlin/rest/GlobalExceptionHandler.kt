package ru.perm.v.shopkotlin.rest

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * Глобальный обработчик исключений для REST-контроллеров.
 * Преобразует исключения в HTTP-ответы с корректными статус-кодами.
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    data class ErrorResponse(
        val status: Int,
        val error: String,
        val message: String,
        val timestamp: String
    )

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResourceNotFound(ex: ResourceNotFoundException): ErrorResponse {
        logger.warn("Resource not found: {}", ex.message)
        return ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = "Not Found",
            message = ex.message ?: "Resource not found",
            timestamp = LocalDateTime.now(ZoneOffset.UTC).toString()
        )
    }

    @ExceptionHandler(ResourceConflictException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleResourceConflict(ex: ResourceConflictException): ErrorResponse {
        logger.warn("Resource conflict: {}", ex.message)
        return ErrorResponse(
            status = HttpStatus.CONFLICT.value(),
            error = "Conflict",
            message = ex.message ?: "Resource conflict",
            timestamp = LocalDateTime.now(ZoneOffset.UTC).toString()
        )
    }

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidation(ex: ValidationException): ErrorResponse {
        logger.warn("Validation error: {}", ex.message)
        return ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Bad Request",
            message = ex.message ?: "Validation error",
            timestamp = LocalDateTime.now(ZoneOffset.UTC).toString()
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(ex: Exception): ErrorResponse {
        logger.error("Unexpected exception: {}", ex.message, ex)
        return ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Internal Server Error",
            message = ex.message ?: "Unknown error",
            timestamp = LocalDateTime.now(ZoneOffset.UTC).toString()
        )
    }
}

/**
 * Исключение — ресурс не найден (404).
 */
class ResourceNotFoundException(message: String) : RuntimeException(message)

/**
 * Исключение — конфликт ресурсов (409).
 */
class ResourceConflictException(message: String) : RuntimeException(message)

/**
 * Исключение — ошибка валидации (400).
 */
class ValidationException(message: String) : RuntimeException(message)
