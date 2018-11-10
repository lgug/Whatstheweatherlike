package weather.whatstheweatherlike.beans;

public class CurrentForecast extends Forecast {

    private Weather weather;

    public CurrentForecast(City city) {
        super(city);
    }

    public CurrentForecast(City city, Weather weather) {
        super(city);
        this.weather = weather;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
