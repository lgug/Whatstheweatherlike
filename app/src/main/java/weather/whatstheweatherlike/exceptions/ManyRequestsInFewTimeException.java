package weather.whatstheweatherlike.exceptions;

public class ManyRequestsInFewTimeException extends Exception {

    public ManyRequestsInFewTimeException() {
        super("You have done too many request in few times.\nRetry in a hour");
    }
}
