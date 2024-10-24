package SecurityApp.exeptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler

    public ResponseEntity<UserIncorData> makeExp(NoSuchUser exp) {
        UserIncorData userIncorData = new UserIncorData();
        userIncorData.setInfo(exp.getMessage());
        return new ResponseEntity<>(userIncorData, HttpStatus.NOT_FOUND);


    }
    @ExceptionHandler

    public ResponseEntity<UserIncorData> makeExp(Exception exp) {
        UserIncorData userIncorData = new UserIncorData();
        userIncorData.setInfo(exp.getMessage());
        return new ResponseEntity<>(userIncorData, HttpStatus.BAD_REQUEST);


    }
}