package weather.whatstheweatherlike.beans;

import java.util.Date;

public class Timing {

    private Date operationTime;
    private Date currentTime;
    private Date sunriseTime;
    private Date sunsetTime;

    public Timing() {
    }

    public Timing(Date operationTime, Date currentTime, Date sunriseTime, Date sunsetTime) {
        this.operationTime = operationTime;
        this.currentTime = currentTime;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public Date getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(Date sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public Date getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(Date sunsetTime) {
        this.sunsetTime = sunsetTime;
    }
}
