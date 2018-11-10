package weather.whatstheweatherlike.exceptions;

public class WeatherForThisCityNotAvailableException extends Exception {

    public WeatherForThisCityNotAvailableException(String city) {
        super("The weather for the city '" + city +"' is not available");
    }
}
