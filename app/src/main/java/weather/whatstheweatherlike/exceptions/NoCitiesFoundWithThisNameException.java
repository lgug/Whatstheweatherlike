package weather.whatstheweatherlike.exceptions;

public class NoCitiesFoundWithThisNameException extends Exception {

    public NoCitiesFoundWithThisNameException() {
        super("There is no city with this name");
    }
}
