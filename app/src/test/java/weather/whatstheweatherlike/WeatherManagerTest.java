package weather.whatstheweatherlike;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.beans.Weather;
import weather.whatstheweatherlike.services.WeatherManager;

public class WeatherManagerTest {

    private final City city = new City(6541865, "Verona", "IT", 45.434189, 10.99779);

    private WeatherManager weatherManager;
    private String pendingJSON;

    @Test
    public void testRestfulCall() throws IOException {
        weatherManager = new WeatherManager();
        pendingJSON = weatherManager.getWeather(city);
    }

    @Test
    public void testWeatherBean() throws IOException, JSONException {
        testRestfulCall();
        Weather weather = weatherManager.adaptJsonToWeather(pendingJSON, city);
        Assert.assertTrue(weather != null);
    }
}