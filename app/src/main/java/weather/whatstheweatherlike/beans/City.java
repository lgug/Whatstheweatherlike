package weather.whatstheweatherlike.beans;

public class City {

    private String name;
    private String country;
    private String countryName;
    private Double lat;
    private Double lon;

    public City() {
    }

    public City(String name, String country, String countryName, Double lat, Double lon) {
        this.name = name;
        this.country = country;
        this.countryName = countryName;
        this.lat = lat;
        this.lon = lon;
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
