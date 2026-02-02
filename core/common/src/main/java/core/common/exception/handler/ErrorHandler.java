package core.common.exception.handler;

import core.common.exception.ApiError;
import core.common.exception.BadRequestException;
import core.common.exception.ConflictException;
import core.common.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import core.common.exception.dto.ErrorResponse;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        log.warn("NOT_FOUND: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        log.warn("BAD_REQUEST: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(ConflictException ex, HttpServletRequest request) {
        log.warn("CONFLICT: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ApiError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherApiErrors(ApiError ex, HttpServletRequest request) {
        log.warn("INTERNAL_SERVER_ERROR: {}", ex.getMessage());
        return buildErrorResponse(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse buildErrorResponse(ApiError ex, HttpServletRequest request, HttpStatus status) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.name(),
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}