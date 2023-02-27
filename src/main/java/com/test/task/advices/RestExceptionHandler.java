package com.test.task.advices;

import com.test.task.dto.ApiErrorDto;
import com.test.task.exception.ParsingFileException;
import com.test.task.exception.ValidateFileException;
import java.time.LocalDateTime;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidateFileException.class)
    protected ResponseEntity<Object> handleValidateFileException(ValidateFileException ex) {
        return new ResponseEntity(ApiErrorDto.builder()
                .message("Validation error")
                .timestamp(LocalDateTime.now())
                .debugMessage(ex.getLocalizedMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ParsingFileException.class)
    protected ResponseEntity<Object> handleParsingFileException(ValidateFileException ex) {
        return new ResponseEntity(ApiErrorDto.builder()
                .message("Parsing error")
                .timestamp(LocalDateTime.now())
                .debugMessage(ex.getLocalizedMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
