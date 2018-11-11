package weather.whatstheweatherlike.beans;

import weather.whatstheweatherlike.enums.WeatherStatus;

public class AdvancedWeather extends Weather {

    private String uvRay;
    private String airPollution;

    public AdvancedWeather() {
    }

    public AdvancedWeather(String uvRay, String airPollution) {
        this.uvRay = uvRay;
        this.airPollution = airPollution;
    }

    public AdvancedWeather(Timing timing, WeatherStatus weather, String weatherDescription,
                           Temperature temperature, Integer humidity, Float pressure,
                           Float windSpeed, String uvRay, String airPollution) {
        super(timing, weather, weatherDescription, temperature, humidity, pressure, windSpeed);
        this.uvRay = uvRay;
        this.airPollution = airPollution;
    }

    public String getUvRay() {
        return uvRay;
    }

    public void setUvRay(String uvRay) {
        this.uvRay = uvRay;
    }

    public String getAirPollution() {
        return airPollution;
    }

    public void setAirPollution(String airPollution) {
        this.airPollution = airPollution;
    }
}
