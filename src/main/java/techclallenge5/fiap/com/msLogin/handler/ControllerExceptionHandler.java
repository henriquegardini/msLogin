package techclallenge5.fiap.com.msLogin.handler;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import techclallenge5.fiap.com.msLogin.exception.InvalidPasswordException;
import techclallenge5.fiap.com.msLogin.exception.TokenCreationException;
import techclallenge5.fiap.com.msLogin.exception.UnauthorizedException;
import techclallenge5.fiap.com.msLogin.exception.UserNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), List.of(ex.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException ex) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage(), List.of(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), List.of(ex.getMessage()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        return buildResponseEntity(HttpStatus.UNAUTHORIZED, ex.getMessage(), List.of(ex.getMessage()));
    }

    @ExceptionHandler(TokenCreationException.class)
    public ResponseEntity<Object> handleTokenCreationException(TokenCreationException ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), List.of(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), List.of(ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, "Invalid request body", List.of(ex.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), List.of(ex.getMessage()));
    }


    private ResponseEntity<Object> buildResponseEntity(final HttpStatus httpStatus,
                                                       final String message,
                                                       final List<String> errors) {
        final ApiError apiError =
                new ApiError(httpStatus.getReasonPhrase(), httpStatus.value(), errors, message, LocalDateTime.now());

        return ResponseEntity.status(httpStatus).body(apiError);
    }
}