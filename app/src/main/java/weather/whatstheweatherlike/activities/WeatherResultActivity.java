package weather.whatstheweatherlike.activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.util.Collections;

import weather.whatstheweatherlike.R;
import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.beans.Coords;
import weather.whatstheweatherlike.beans.CurrentForecast;
import weather.whatstheweatherlike.beans.FiveDaysForecast;
import weather.whatstheweatherlike.beans.Forecast;
import weather.whatstheweatherlike.beans.InputData;
import weather.whatstheweatherlike.beans.Temperature;
import weather.whatstheweatherlike.beans.Weather;
import weather.whatstheweatherlike.enums.WeatherStatus;
import weather.whatstheweatherlike.exceptions.DateTooFarInTimeException;
import weather.whatstheweatherlike.exceptions.NoCitiesFoundWithThisNameException;
import weather.whatstheweatherlike.services.JsonAdapter;
import weather.whatstheweatherlike.services.WeatherManager;
import weather.whatstheweatherlike.utils.Converter;

public class WeatherResultActivity extends AppCompatActivity {

    WeatherManager weatherManager;
    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_result);

        sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        weatherManager = new WeatherManager();
        Forecast forecast;
        Weather weather = null;
        InputData inputData;

        try {
            inputData = JsonAdapter.toObject(getIntent().getStringExtra("data"), InputData.class);
            if (inputData.getCities().getList().size() == 0)
                throw new NoCitiesFoundWithThisNameException();
            forecast = weatherManager.execute(inputData).get();
            if (forecast != null) {
                if (forecast instanceof FiveDaysForecast) {
                    weather = ((FiveDaysForecast) forecast).getForecast().get(inputData.getWeatherDate().getTime().getTimeInMillis());
                    if (weather == null) throw new DateTooFarInTimeException();
                } else if (forecast instanceof CurrentForecast) {
                    weather = ((CurrentForecast) forecast).getWeather();
                }
            } else {
                throw weatherManager.getException();
            }
            ImageView imageView = findViewById(R.id.imageView);
            TextView title = findViewById(R.id.textView8);
            TextView subtitle = findViewById(R.id.textView7);
            assert weather != null;
            boolean isNight = !(
                    weather.getTiming().getCurrentTime().after(weather.getTiming().getSunriseTime())
                    && weather.getTiming().getCurrentTime().before(weather.getTiming().getSunsetTime())
            );

            subtitle.setText(weather.getWeatherDescription());
            switch (weather.getWeather()) {
                case CLEAR:
                    if (isNight) imageView.setImageResource(R.drawable.nightly_clear);
                    else imageView.setImageResource(R.drawable.daily_clear);
                    title.setText(WeatherStatus.CLEAR.toString());
                    break;
                case SNOW:
                    imageView.setImageResource(R.drawable.snow);
                    title.setText("SNOWY");
                    break;
                case RAIN:
                    if (subtitle.getText().equals("light rain")) {
                        imageView.setImageResource(R.drawable.light_rain);
                        title.setText("DRIZZLY");
                    } else {
                        imageView.setImageResource(R.drawable.rain);
                        title.setText("RAINY");
                    }
                    break;
                case DRIZZLE:
                    imageView.setImageResource(R.drawable.light_rain);
                    title.setText("DRIZZLY");
                    break;
                case CLOUDS:
                    if (subtitle.getText().equals("few clouds")) {
                        if (isNight) imageView.setImageResource(R.drawable.nightly_slightly_cloudy);
                        else imageView.setImageResource(R.drawable.daily_slightly_cloudy);
                    } else if (subtitle.getText().equals("scattered clouds")) {
                        if (isNight) imageView.setImageResource(R.drawable.nightly_cloudiness);
                        else imageView.setImageResource(R.drawable.daily_cloudiness);
                    } else {
                        if (isNight) imageView.setImageResource(R.drawable.nightly_cloudiness); //TODO change image resource
                        else imageView.setImageResource(R.drawable.daily_overcast_clouds);
                    }
                    title.setText("CLOUDY");
                    break;
                case THUNDERSTORM:
                    imageView.setImageResource(R.drawable.thunderstorm);
                    title.setText("THUNDERY");
                    break;
                case SQUALLS:
                    imageView.setImageResource(R.drawable.thunderstorm);
                    title.setText("SQUALLY");
                    break;
                case MIST:
                    setAtmosphereCase("MISTY", isNight);
                    break;
                case FOG:
                    setAtmosphereCase("FOGGY", isNight);
                    break;
                case DUST:
                    setAtmosphereCase("DUSTY", isNight);
                    break;
                case HAZE:
                    setAtmosphereCase("HAZY", isNight);
                    break;
                case SAND:
                    setAtmosphereCase("SANDY", isNight);
                    break;
                case SMOKE:
                    setAtmosphereCase("SMOKY", isNight);
                    break;
                case TORNADO:
                    setAtmosphereCase(WeatherStatus.TORNADO.toString(), isNight);
                default:
                    throw new Exception("Switch default case occurs");
            }

            TextView cityNameTV = findViewById(R.id.textView16);
            TextView pressureTV = findViewById(R.id.textView5);
            TextView humidityTV = findViewById(R.id.textView4);
            TextView windSpeedTV = findViewById(R.id.textView9);

            cityNameTV.setText(forecast.getCity().getName() + " (" + forecast.getCity().getCountry() + ")");
            weatherManager.setTemperatureInView(getWindow().getDecorView().getRootView(), sharedPreferences, weather.getTemperature());
            pressureTV.setText(Converter.convertPressure(sharedPreferences, weather.getPressure()));
            humidityTV.setText(weather.getHumidity().toString() + "%");
            windSpeedTV.setText(Converter.convertWindSpeed(sharedPreferences, weather.getWindSpeed()));
            weatherManager.setUviIndexInView(getWindow().getDecorView().getRootView(), weather.getUviIndex(), isNight);
            weatherManager.setAirQualityInView(getWindow().getDecorView().getRootView(), weather.getAirPollution(), isNight);

            if (isNight) {
                getWindow().getDecorView().setBackgroundResource(R.drawable.fh);
                int color = getColor(R.color.white);
                ((TextView) findViewById(R.id.textView13)).setTextColor(color);
                ((TextView) findViewById(R.id.textView14)).setTextColor(color);
                ((TextView) findViewById(R.id.textView15)).setTextColor(color);
                ((TextView) findViewById(R.id.textView23)).setTextColor(color);
                ((TextView) findViewById(R.id.textView25)).setTextColor(color);
                ((TextView) findViewById(R.id.textView24)).setTextColor(color);
                ((TextView) findViewById(R.id.textView26)).setTextColor(color);
                pressureTV.setTextColor(color);
                humidityTV.setTextColor(color);
                windSpeedTV.setTextColor(color);

                TextView minTempTV = findViewById(R.id.textView11);
                minTempTV.setTextColor(getColor(R.color.temperatureMin_night));
                TextView avgTempTV = findViewById(R.id.textView10);
                avgTempTV.setShadowLayer(5, 0, 0, getColor(R.color.white));
                TextView maxTempTV = findViewById(R.id.textView6);
                maxTempTV.setTextColor(getColor(R.color.temperatureMax_night));
                title.setTextColor(getColor(R.color.weatherResultNightTitle));
                title.setShadowLayer(15, 0, 0, getColor(R.color.colorPrimaryDark));
                subtitle.setTextColor(getColor(R.color.weatherResultNightSubtitle));

            } else {
                getWindow().getDecorView().setBackgroundResource(R.drawable.cloudsfghgf);
            }
        } catch (Exception e) {
            errorScreen(e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void errorScreen(String message) {
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.umbrella);
        TextView title = findViewById(R.id.textView8);
        TextView subtitle = findViewById(R.id.textView7);
        TextView cityNameTV = findViewById(R.id.textView16);
        LinearLayout linearLayout1 = findViewById(R.id.linearL1);
        LinearLayout linearLayout2 = findViewById(R.id.linearL2);
        LinearLayout linearLayout3 = findViewById(R.id.LinearL3);
        LinearLayout linearLayout4 = findViewById(R.id.linearL4);
        LinearLayout linearLayout5 = findViewById(R.id.linearL5);
        LinearLayout linearLayout6 = findViewById(R.id.linearL6);
        subtitle.setText(message);
        subtitle.setTextColor(getColor(R.color.white));
        cityNameTV.setPadding(0,0,0, 180);
        cityNameTV.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        linearLayout1.setVisibility(View.GONE);
        linearLayout2.setVisibility(View.GONE);
        linearLayout3.setVisibility(View.GONE);
        linearLayout4.setVisibility(View.GONE);
        linearLayout5.setVisibility(View.GONE);
        linearLayout6.setVisibility(View.GONE);
    }

    private void setAtmosphereCase(String weatherStatus, boolean isNight) {
        ImageView imageView = findViewById(R.id.imageView);
        if (isNight) imageView.setImageResource(R.drawable.nightly_fog);
        else imageView.setImageResource(R.drawable.daily_fog);
        TextView title = findViewById(R.id.textView8);
        title.setText(weatherStatus);
    }
}
