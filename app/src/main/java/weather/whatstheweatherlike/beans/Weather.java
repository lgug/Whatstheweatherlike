package weather.whatstheweatherlike.beans;

import weather.whatstheweatherlike.enums.WeatherStatus;

public class Weather {

    private Timing timing;
    private WeatherStatus weather;
    private String weatherDescription;
    private Temperature temperature;
    private Integer humidity;
    private Float pressure;
    private Float windSpeed;
    private Float uviIndex;
    private Integer airPollution;

    public Weather() {
    }

    public Weather(Timing timing, WeatherStatus weather, String weatherDescription, Temperature temperature,
                   Integer humidity, Float pressure, Float windSpeed, Float uviIndex, Integer airPollution) {
        this.timing = timing;
        this.weather = weather;
        this.weatherDescription = weatherDescription;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.uviIndex = uviIndex;
        this.airPollution = airPollution;
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

    public Float getUviIndex() {
        return uviIndex;
    }

    public void setUviIndex(Float uviIndex) {
        this.uviIndex = uviIndex;
    }

    public Integer getAirPollution() {
        return airPollution;
    }

    public void setAirPollution(Integer airPollution) {
        this.airPollution = airPollution;
    }
}
