package SecurityApp.exeptionHandling;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class UserIncorData {
    private String info;

    public UserIncorData() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
