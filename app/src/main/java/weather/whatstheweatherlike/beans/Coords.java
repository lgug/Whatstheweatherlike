package weather.whatstheweatherlike.beans;

import android.location.Location;

public class Coords {

    private Double latitude;
    private Double longitude;

    public Coords() {
    }

    public Coords(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public static Coords getInstanceFromLocation(Location location) {
        Coords coords = new Coords();
        coords.setLatitude(location.getLatitude());
        coords.setLongitude(location.getLongitude());
        return coords;
    }
}
