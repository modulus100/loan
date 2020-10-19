package craft.beer.loan.controller.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@EnableWebMvc
@ControllerAdvice
public class LoanControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ LoanException.class })
    public ResponseEntity<Object> handleCustomException(
            Exception ex,
            HttpServletRequest request,
            HttpServletResponse response) {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(
            Exception ex,
            HttpServletRequest request,
            HttpServletResponse response) {

        return ResponseEntity.badRequest().build();
    }
}
