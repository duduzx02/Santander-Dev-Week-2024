package me.dio.sdw24.adapters.in;

import me.dio.sdw24.domain.exception.ChampionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ChampionNotFoundException.class)
    public ResponseEntity<ApiError> handleDomainException(ChampionNotFoundException domainError){
        return ResponseEntity.unprocessableEntity().body(new ApiError(domainError.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleDomainException(Exception unexpextedError){
        String message = "erro inesperado";
        logger.error(message, unexpextedError);
        return ResponseEntity.unprocessableEntity().body(new ApiError(message));
    }

    public record ApiError(String message){

    }
}
