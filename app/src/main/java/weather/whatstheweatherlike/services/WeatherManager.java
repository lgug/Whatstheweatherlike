package weather.whatstheweatherlike.services;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.LongSparseArray;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

import weather.whatstheweatherlike.R;
import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.beans.CurrentForecast;
import weather.whatstheweatherlike.beans.FiveDaysForecast;
import weather.whatstheweatherlike.beans.Forecast;
import weather.whatstheweatherlike.beans.InputData;
import weather.whatstheweatherlike.beans.Temperature;
import weather.whatstheweatherlike.beans.Timing;
import weather.whatstheweatherlike.beans.Weather;
import weather.whatstheweatherlike.enums.WeatherStatus;
import weather.whatstheweatherlike.exceptions.WeatherForThisCityNotAvailableException;
import weather.whatstheweatherlike.utils.Converter;

public class WeatherManager extends AsyncTask<InputData, Void, Forecast> {

    private final static String KEY = "ba331c2494b96ae8ddaefdc0f839c18d";
    private final static String CURRENT_WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?";
    private final static String FORECAST_WEATHER_URL = "https://api.openweathermap.org/data/2.5/forecast?";

    private Exception exception;

    private Forecast getWeather(InputData inputData) throws IOException, JSONException {
        City city = inputData.getCities().getList().get(0);
        Forecast forecast;
        if (inputData.getWeatherDate().isNow()) {
            URL url = new URL(CURRENT_WEATHER_URL +
                    "lat=" + city.getCoords().getLatitude() + "&" +
                    "lon=" + city.getCoords().getLongitude() + "&" +
                    "appid=" + KEY
            );
            String json = getData(url);
            forecast = adaptJsonToWeather(json, city, inputData.isUsedLocation());
        } else {
            URL url = new URL(FORECAST_WEATHER_URL +
                    "lat=" + inputData.getCities().getList().get(0).getCoords().getLatitude() + "&" +
                    "lon=" + inputData.getCities().getList().get(0).getCoords().getLongitude() + "&" +
                    "appid=" + KEY
            );
            String json = getData(url);
            forecast = adaptJsonToWeatherForecast(json, city, inputData.isUsedLocation());
        }
        return forecast;
    }

    private String getData(URL url) throws IOException {

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

    private FiveDaysForecast adaptJsonToWeatherForecast(String json, City city, boolean coordsOnly) throws JSONException {
        Map<List<GregorianCalendar>, Weather> map = new HashMap<>();

        JSONObject root = new JSONObject(json);
        JSONArray listWeather = root.getJSONArray("list");
        JSONObject cityObject = root.getJSONObject("city");

        if (coordsOnly) {
            city.setName(cityObject.getString("name"));
            city.setCountry(cityObject.getString("country"));
        }

        for(int i = 0; i < listWeather.length(); i++) {
            Weather weather = new Weather();
            JSONObject pendingObject = listWeather.getJSONObject(i);

            JSONObject mainObject = pendingObject.getJSONObject("main");
            JSONObject weatherObject = pendingObject.getJSONArray("weather").getJSONObject(0);
            JSONObject windObject = pendingObject.getJSONObject("wind");

            Timing timing = new Timing(
                    new Date(TimeUnit.MILLISECONDS.convert(pendingObject.getLong("dt"), TimeUnit.SECONDS)),
                    new Date(),
                    new Date(new Date().getTime() - 1),
                    new Date(new Date().getTime() + 1)
            );

            Temperature temperature = new Temperature(
                    (float)(mainObject.getDouble("temp")),
                    (float)(mainObject.getDouble("temp_max")),
                    (float)(mainObject.getDouble("temp_min"))
            );

            weather.setWeather(WeatherStatus.valueOf(weatherObject.getString("main").toUpperCase()));
            weather.setWeatherDescription(weatherObject.getString("description"));
            weather.setPressure((float)(mainObject.getDouble("pressure")));
            weather.setHumidity(mainObject.getInt("humidity"));
            weather.setWindSpeed((float)(windObject.getDouble("speed")));
            weather.setTemperature(temperature);
            weather.setTiming(timing);

            String[] stringsDate = pendingObject.getString("dt_txt").split(" ")[0].split("-");
            String[] stringsTime = pendingObject.getString("dt_txt").split(" ")[1].split(":");
            GregorianCalendar gregorianCalendar1 = new GregorianCalendar(
                    Integer.parseInt(stringsDate[0]),
                    Integer.parseInt(stringsDate[1]),
                    Integer.parseInt(stringsDate[2])
            );
            GregorianCalendar gregorianCalendar2 = new GregorianCalendar(
                    Integer.parseInt(stringsDate[0]),
                    Integer.parseInt(stringsDate[1]),
                    Integer.parseInt(stringsDate[2]),
                    Integer.parseInt(stringsTime[0]),
                    Integer.parseInt(stringsTime[1])
            );
            map.put(Arrays.asList(gregorianCalendar1,gregorianCalendar2), weather);
        }

        return new FiveDaysForecast(city, keepOnlyDaily(map));
    }

    private CurrentForecast adaptJsonToWeather(String json, City city, boolean coordsOnly) throws JSONException {
        Weather weather = new Weather();

        JSONObject root = new JSONObject(json);
        JSONObject mainObject = root.getJSONObject("main");
        JSONObject weatherObject = root.getJSONArray("weather").getJSONObject(0);
        JSONObject sysObject = root.getJSONObject("sys");

        if (coordsOnly) {
            city.setName(root.getString("name"));
            city.setCountry(sysObject.getString("country"));
        }

        Temperature temperature = new Temperature(
                (float)(mainObject.getDouble("temp")),
                (float)(mainObject.getDouble("temp_max")),
                (float)(mainObject.getDouble("temp_min"))
        );

        Timing timing = new Timing(
                new Date(TimeUnit.MILLISECONDS.convert(root.getLong("dt"), TimeUnit.SECONDS)),
                new Date(),
                new Date(TimeUnit.MILLISECONDS.convert(sysObject.getLong("sunrise"), TimeUnit.SECONDS)),
                new Date(TimeUnit.MILLISECONDS.convert(sysObject.getLong("sunset"), TimeUnit.SECONDS))
        );

        weather.setWeather(WeatherStatus.valueOf(weatherObject.getString("main").toUpperCase()));
        weather.setWeatherDescription(weatherObject.getString("description"));
        weather.setPressure((float)(mainObject.getDouble("pressure")));
        weather.setHumidity(mainObject.getInt("humidity"));
        weather.setWindSpeed((float)(root.getJSONObject("wind").getDouble("speed")));
        weather.setTemperature(temperature);
        weather.setTiming(timing);

        return new CurrentForecast(city, weather);
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @SuppressLint("UseSparseArrays")
    private Map<Long, Weather> keepOnlyDaily(Map<List<GregorianCalendar>, Weather> map) {
        Map<Long, Weather> result = new HashMap<>();

        Map<GregorianCalendar, List<Weather>> weatherSituationMap = new HashMap<>();
        for (List<GregorianCalendar> gregorianCalendars: map.keySet()) {
            if (!weatherSituationMap.containsKey(gregorianCalendars.get(0))) {
                weatherSituationMap.put(gregorianCalendars.get(0), new ArrayList<Weather>());
            }
            weatherSituationMap.get(gregorianCalendars.get(0)).add(map.get(gregorianCalendars));
        }
        for (GregorianCalendar gregorianCalendar: weatherSituationMap.keySet()) {
            Map<String, Integer> countingMap = new HashMap<>();
            for (Weather weather : weatherSituationMap.get(gregorianCalendar)) {
                if (!countingMap.containsKey(weather.getWeatherDescription())) {
                    countingMap.put(weather.getWeatherDescription(), 0);
                }
                countingMap.put(weather.getWeatherDescription(), countingMap.get(weather.getWeatherDescription()) + 1);
            }
            String max = null;
            for (String wd : countingMap.keySet()) {
                if (max == null || countingMap.get(wd) > countingMap.get(max)) {
                    max = wd;
                }
            }
            int avg = countingMap.get(max) / 2;
            int count = 0;
            for (Weather weather : weatherSituationMap.get(gregorianCalendar)) {
                if (weather.getWeatherDescription().equals(max)) {
                    if (count == avg) {
                        result.put(gregorianCalendar.getTimeInMillis(), weather);
                        break;
                    } else {
                        count++;
                    }
                }
            }
        }
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"SetTextI18n"})
    public void setTemperatureInView(View view, SharedPreferences sharedPreferences, Temperature temperature) {
        TextView minTemperature = view.findViewById(R.id.textView11);
        TextView maxTemperature = view.findViewById(R.id.textView6);
        TextView avgTemperature = view.findViewById(R.id.textView10);

        //  0 °C = 273.15 K
        // 12 °C = 285.15 K
        // 20 °C = 293.15 K
        // 30 °C = 303.15 K
        // 36 °C = 309.15 K

        int color = R.color.black;
        if (temperature.getTemperature() < 273.15) {
            color = R.color.temperature01;
        } else if (temperature.getTemperature() >= 273.15 && temperature.getTemperature() < 285.15) {
            color = R.color.temperature02;
        } else if (temperature.getTemperature() >= 285.15 && temperature.getTemperature() < 293.15) {
            color = R.color.temperature03;
        } else if (temperature.getTemperature() >= 293.15 && temperature.getTemperature() < 303.15) {
            color = R.color.temperature04;
        } else if (temperature.getTemperature() >= 303.15 && temperature.getTemperature() < 309.15) {
            color = R.color.temperature05;
        } else if (temperature.getTemperature() >= 309.15) {
            color = R.color.temperature06;
        }
        avgTemperature.setText(Converter.convertTemperature(sharedPreferences, temperature.getTemperature()));
        avgTemperature.setTextColor(view.getContext().getResources().getColor(color, null));
        minTemperature.setText(Converter.convertTemperature(sharedPreferences, temperature.getMinTemperature()));
        minTemperature.setTextColor(view.getContext().getResources().getColor(R.color.temperatureMin_day, null));
        maxTemperature.setText(Converter.convertTemperature(sharedPreferences,temperature.getMaxTemperature()));
        maxTemperature.setTextColor(view.getContext().getResources().getColor(R.color.temperatureMax_day, null));
    }

    @Override
    protected Forecast doInBackground(InputData... inputData) {
        try {
             return getWeather(inputData[0]);
        } catch (FileNotFoundException e1) {
            exception = new WeatherForThisCityNotAvailableException(inputData[0].getCities().getList().get(0).getName());
            return null;
        } catch (JSONException e2) {
            exception = e2;
            return null;
        } catch (IOException e3) {
            exception = e3;
            return null;
        }
    }

    public Exception getException() {
        return exception;
    }
}
