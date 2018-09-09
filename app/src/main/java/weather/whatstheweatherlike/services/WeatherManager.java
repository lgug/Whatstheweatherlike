package weather.whatstheweatherlike.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import weather.whatstheweatherlike.beans.City;

public class WeatherManager {

    private final static String KEY = "ba331c2494b96ae8ddaefdc0f839c18d";
    private final static String WEATHER_URL = "api.openweathermap.org/data/2.5/weather?";

    public String getWeather(City city) throws IOException {
        String place = city.getName() + ", " + city.getCountry();
        URL url = new URL(WEATHER_URL + "weather=" + place + "&appid=" + KEY);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        InputStream inputStream = httpsURLConnection.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        return byteArrayOutputStream.toString();
    }

}
