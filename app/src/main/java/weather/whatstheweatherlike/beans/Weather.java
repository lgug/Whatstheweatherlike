package weather.whatstheweatherlike.beans;

import weather.whatstheweatherlike.enums.WeatherStatus;

public class Weather {

    private City city;
    private Timing timing;
    private WeatherStatus weather;
    private String weatherDescription;
    private Temperature temperature;
    private Integer humidity;
    private Float pressure;
    private Float windSpeed;

    public Weather() {
    }

    public Weather(City city, Timing timing, WeatherStatus weather, String weatherDescription,
                   Temperature temperature, Integer humidity, Float pressure, Float windSpeed) {
        this.city = city;
        this.timing = timing;
        this.weather = weather;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Timing getTiming() {
        return timing;
    }

    public void setTiming(Timing timing) {
        this.timing = timing;
    }

    public WeatherStatus getWeather() {
        return weather;
    }

    public void setWeather(WeatherStatus weather) {
        this.weather = weather;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }
}
