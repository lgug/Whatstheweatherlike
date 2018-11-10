package weather.whatstheweatherlike.beans;

import java.util.GregorianCalendar;

public class WeatherDate {

    public static final String NOW = "NOW";

    private GregorianCalendar time;
    private boolean now;

    public WeatherDate() {
    }

    public WeatherDate(GregorianCalendar time, boolean now) {
        this.time = time;
        this.now = now;
    }

    public GregorianCalendar getTime() {
        return time;
    }

    public void setTime(GregorianCalendar time) {
        this.time = time;
    }

    public boolean isNow() {
        return now;
    }

    public void setNow(boolean now) {
        this.now = now;
    }
}
