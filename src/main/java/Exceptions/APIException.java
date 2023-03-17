package Exceptions;

// cannot possibly recover when API calls don't work
public class APIException extends RuntimeException {
    public APIException() {
        super("Unable to send an API request");
    }
    public APIException(String msg) {
        super(msg);
    }

}
