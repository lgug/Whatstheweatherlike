package weather.whatstheweatherlike.beans;

public abstract class Forecast {

    private City city;

    public Forecast() {
    }

    public Forecast(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
