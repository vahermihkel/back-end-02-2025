package ee.mihkel.veebipood.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionCatcher {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(RuntimeException e) {
        ErrorMessage message = new ErrorMessage();
        message.setMessage(e.getMessage());
        message.setTimestamp(new Date());
        message.setCode(400);
        return ResponseEntity.badRequest().body(message);
    }
}
