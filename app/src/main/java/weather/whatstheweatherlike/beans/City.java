package weather.whatstheweatherlike.beans;

public class City {

    private String name;
    private String country;
    private String countryName;
    private Coords coords;

    public City() {
    }

    public City(Coords coords) {
        this.coords = coords;
    }

    public City(String name, String country, String countryName, Coords coords) {
        this.name = name;
        this.country = country;
        this.countryName = countryName;
        this.coords = coords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }
}
