package weather.whatstheweatherlike.services;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import weather.whatstheweatherlike.R;
import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.beans.Temperature;
import weather.whatstheweatherlike.beans.Timing;
import weather.whatstheweatherlike.beans.Weather;
import weather.whatstheweatherlike.enums.WeatherStatus;
import weather.whatstheweatherlike.utils.Converter;

public class WeatherManager extends AsyncTask<City, Void, String> {

    private final static String KEY = "ba331c2494b96ae8ddaefdc0f839c18d";
    private final static String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?";

    public String getWeather(String city) throws IOException {
        String[] nc = city.split(",");
        return getWeather(new City(null, nc[0], nc[1], null, null));
    }

    public String getWeather(City city) throws IOException {
        URL url = new URL(WEATHER_URL +
                "lat=" + city.getLat() + "&" +
                "lon=" + city.getLon() + "&" +
                "appid=" + KEY
        );
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
        JSONObject sysObject = root.getJSONObject("sys");

        Temperature temperature = new Temperature(
                Converter.round(Converter.fromKelvinToCelsius((float)(mainObject.getDouble("temp"))), 1),
                Converter.round(Converter.fromKelvinToCelsius((float)(mainObject.getDouble("temp_max"))), 1),
                Converter.round(Converter.fromKelvinToCelsius((float)(mainObject.getDouble("temp_min"))), 1)
        );

        Timing timing = new Timing(
                new Date(TimeUnit.MILLISECONDS.convert(root.getLong("dt"), TimeUnit.SECONDS)),
                new Date(),
                new Date(TimeUnit.MILLISECONDS.convert(sysObject.getLong("sunrise"), TimeUnit.SECONDS)),
                new Date(TimeUnit.MILLISECONDS.convert(sysObject.getLong("sunset"), TimeUnit.SECONDS))
        );

        weather.setCity(city);
        weather.setWeather(WeatherStatus.valueOf(weatherObject.getString("main").toUpperCase()));
        weather.setWeatherDescription(weatherObject.getString("description"));
        weather.setPressure((float)(mainObject.getDouble("pressure")));
        weather.setHumidity(mainObject.getInt("humidity"));
        weather.setWindSpeed((float)(root.getJSONObject("wind").getDouble("speed")));
        weather.setTemperature(temperature);
        weather.setTiming(timing);

        return weather;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"SetTextI18n"})
    public void setTemperatureInView(View view, Temperature temperature) {
        TextView minTemperature = view.findViewById(R.id.textView11);
        TextView maxTemperature = view.findViewById(R.id.textView6);
        TextView avgTemperature = view.findViewById(R.id.textView10);

        int color = R.color.black;
        if (temperature.getTemperature() < 0) {
            color = R.color.temperature01;
        } else if (temperature.getTemperature() >= 0 && temperature.getTemperature() < 12) {
            color = R.color.temperature02;
        } else if (temperature.getTemperature() >= 12 && temperature.getTemperature() < 22) {
            color = R.color.temperature03;
        } else if (temperature.getTemperature() >= 22 && temperature.getTemperature() < 30) {
            color = R.color.temperature04;
        } else if (temperature.getTemperature() >= 30 && temperature.getTemperature() < 36) {
            color = R.color.temperature05;
        } else if (temperature.getTemperature() >= 36) {
            color = R.color.temperature06;
        }
        avgTemperature.setText(temperature.getTemperature() + " °C");
        avgTemperature.setTextColor(view.getContext().getResources().getColor(color, null));
        minTemperature.setText(temperature.getMinTemperature() + " °C");
        minTemperature.setTextColor(view.getContext().getResources().getColor(R.color.temperatureMin_day, null));
        maxTemperature.setText(temperature.getMaxTemperature() + " °C");
        maxTemperature.setTextColor(view.getContext().getResources().getColor(R.color.temperatureMax_day, null));
    }

    @Override
    protected String doInBackground(City... cities) {
        try {
            return getWeather(cities[0]);
        } catch (IOException e) {
            return null;
        }
    }
}
