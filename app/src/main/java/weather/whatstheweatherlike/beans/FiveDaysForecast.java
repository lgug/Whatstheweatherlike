package weather.whatstheweatherlike.beans;

import java.util.GregorianCalendar;
import java.util.Map;

public class FiveDaysForecast extends Forecast {

    private Map<Long, Weather> forecast;

    public FiveDaysForecast(City city) {
        super(city);
    }

    public FiveDaysForecast(City city, Map<Long, Weather> forecast) {
        super(city);
        this.forecast = forecast;
    }

    public Map<Long, Weather> getForecast() {
        return forecast;
    }

    public void setForecast(Map<Long, Weather> forecast) {
        this.forecast = forecast;
    }

    public void addToForecast(GregorianCalendar date, Weather weather) {
        this.forecast.put(date.getTimeInMillis(), weather);
    }
}
