package weather.whatstheweatherlike;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.beans.CityList;
import weather.whatstheweatherlike.beans.Coords;
import weather.whatstheweatherlike.beans.Forecast;
import weather.whatstheweatherlike.beans.InputData;
import weather.whatstheweatherlike.beans.Weather;
import weather.whatstheweatherlike.beans.WeatherDate;
import weather.whatstheweatherlike.services.WeatherManager;

public class WeatherManagerTest {

    private final City city = new City(
            "Vertona",
            "IT",
            "Italy",
            new Coords(45.434189, 10.99779)
    );
    private final InputData inputData = new InputData(
            new CityList(Collections.singletonList(city)),
            new WeatherDate(null, true),
            false
    );

    private WeatherManager weatherManager;

    @Test
    public void testRestfulCall() throws IOException, ExecutionException, InterruptedException {
        weatherManager = new WeatherManager();
        //Forecast forecast = weatherManager.execute(inputData).get();

    }
}