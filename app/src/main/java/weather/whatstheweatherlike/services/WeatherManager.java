package weather.whatstheweatherlike.services;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.beans.Temperature;
import weather.whatstheweatherlike.beans.Weather;
import weather.whatstheweatherlike.enums.WeatherStatus;
import weather.whatstheweatherlike.utils.Converter;

public class WeatherManager {

    private final static String KEY = "ba331c2494b96ae8ddaefdc0f839c18d";
    private final static String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?";

    public String getWeather(City city) throws IOException {
        String place = city.getName() + "," + city.getCountry();
        URL url = new URL(WEATHER_URL + "q=" + place + "&appid=" + KEY);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        InputStream inputStream = httpsURLConnection.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        return byteArrayOutputStream.toString();
    }

    public Weather adaptJsonToWeather(String json, City city) throws JSONException {
        Weather weather = new Weather();

        JSONObject root = new JSONObject(json);
        JSONObject mainObject = root.getJSONObject("main");
        JSONObject weatherObject = root.getJSONArray("weather").getJSONObject(0);

        Temperature temperature = new Temperature(
                Converter.fromKelvinToCelsius((float)(mainObject.getDouble("temp"))),
                Converter.fromKelvinToCelsius((float)(mainObject.getDouble("temp_min"))),
                Converter.fromKelvinToCelsius((float)(mainObject.getDouble("temp_max")))
        );

        weather.setCity(city);
        weather.setWeather(WeatherStatus.valueOf(weatherObject.getString("main").toUpperCase()));
        weather.setWeatherDescription(weatherObject.getString("description"));
        weather.setPressure((float)(mainObject.getDouble("pressure")));
        weather.setHumidity(mainObject.getInt("humidity"));
        weather.setWindSpeed((float)(root.getJSONObject("wind").getDouble("speed")));
        weather.setTemperature(temperature);

        return weather;
    }

}
