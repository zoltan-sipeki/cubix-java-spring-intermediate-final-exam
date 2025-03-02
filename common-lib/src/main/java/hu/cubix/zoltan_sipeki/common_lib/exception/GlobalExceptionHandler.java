package hu.cubix.zoltan_sipeki.common_lib.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handle(InvalidInputException ex) {
        var message = new StringBuilder();
        for (var error : ex.getErrors().entrySet()) {
            message.append(error.getKey());
            message.append(": ");
            message.append(error.getValue());
            message.append(" | ");
        }
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message.toString())).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handle(MethodArgumentNotValidException e) {
        var message = new StringBuilder();
        for (var error : e.getFieldErrors()) {
            message.append(error.getField());
            message.append(": ");
            message.append(error.getDefaultMessage());
            message.append(" | ");
        }
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message.toString())).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handle(HandlerMethodValidationException ex) {
        var errors = ex.getParameterValidationResults().stream().map(r -> {
            var arg = r.getMethodParameter().getParameterName();
            var message = r.getResolvableErrors().stream().map(MessageSourceResolvable::getDefaultMessage).toList()
                    .toString();
            return arg + ": " + message;
        }).toList().toString().replaceAll("\\[|\\]", "");
        return ResponseEntity
                .of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors)).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handle(EntityNotFoundException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage())).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handle(EntityAlreadyExistsException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage())).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handle(IllegalArgumentException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage())).build();
    }

    @ExceptionHandler
    public ResponseEntity<ProblemDetail> handle(BadCredentialsException ex) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid username or password."))
                .build();
    }
}
