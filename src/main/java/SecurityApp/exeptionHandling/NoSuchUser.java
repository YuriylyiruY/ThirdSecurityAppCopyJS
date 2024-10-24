package SecurityApp.exeptionHandling;

public class NoSuchUser extends RuntimeException  {
    public NoSuchUser(String message) {
        super(message);
    }
}
