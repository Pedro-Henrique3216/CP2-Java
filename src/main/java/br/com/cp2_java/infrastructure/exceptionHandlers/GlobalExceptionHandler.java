package br.com.cp2_java.infrastructure.exceptionHandlers;

import br.com.cp2_java.dtos.Errors;
import br.com.cp2_java.exceptions.InsufficientStock;
import br.com.cp2_java.exceptions.NotFound;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Errors> handleNotFound(NotFound ex, HttpServletRequest request) {
        Errors errorResponse = new Errors(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                List.of(ex.getMessage()),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Errors> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> messages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        Errors errorResponse = new Errors(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                messages,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(InsufficientStock.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Errors> handleInsufficientStock(InsufficientStock ex, HttpServletRequest request) {
        Errors errorResponse = new Errors(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Insufficient Stock",
                List.of(ex.getMessage()),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

}
