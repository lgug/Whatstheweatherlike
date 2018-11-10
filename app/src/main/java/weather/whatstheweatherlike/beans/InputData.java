package weather.whatstheweatherlike.beans;

public class InputData {

    private CityList cities;
    private WeatherDate weatherDate;
    private boolean usedLocation;

    public InputData() {
    }

    public InputData(CityList cities, WeatherDate weatherDate, boolean usedLocation) {
        this.cities = cities;
        this.weatherDate = weatherDate;
        this.usedLocation = usedLocation;
    }

    public CityList getCities() {
        return cities;
    }

    public void setCities(CityList cities) {
        this.cities = cities;
    }

    public WeatherDate getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(WeatherDate weatherDate) {
        this.weatherDate = weatherDate;
    }

    public boolean isUsedLocation() {
        return usedLocation;
    }

    public void setUsedLocation(boolean usedLocation) {
        this.usedLocation = usedLocation;
    }
}
