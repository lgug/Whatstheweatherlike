package weather.whatstheweatherlike.beans;

import java.util.Date;

public class Timing {

    private Date operationTime;
    private Date sunriseTime;
    private Date sunsetTime;

    public Timing() {
    }

    public Timing(Date operationTime, Date sunriseTime, Date sunsetTime) {
        this.operationTime = operationTime;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
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
