package weather.whatstheweatherlike.beans;

public class Temperature {

    private Float temperature;
    private Float maxTemperature;
    private Float minTemperature;

    public Temperature() {
    }

    public Temperature(Float temperature, Float maxTemperature, Float minTemperature) {
        this.temperature = temperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Float minTemperature) {
        this.minTemperature = minTemperature;
    }
}
