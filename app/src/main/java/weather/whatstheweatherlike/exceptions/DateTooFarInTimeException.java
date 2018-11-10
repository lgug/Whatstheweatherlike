package weather.whatstheweatherlike.exceptions;

public class DateTooFarInTimeException extends Exception {

    public DateTooFarInTimeException() {
        super("The date you selected is too far in the future.\n" +
                "Please wait until it is more close");
    }

}
