package weather.whatstheweatherlike.activities;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

import weather.whatstheweatherlike.R;
import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.beans.Temperature;
import weather.whatstheweatherlike.beans.Weather;
import weather.whatstheweatherlike.enums.WeatherStatus;
import weather.whatstheweatherlike.services.JsonAdapter;
import weather.whatstheweatherlike.services.WeatherManager;

public class WeatherResultActivity extends AppCompatActivity {

    WeatherManager weatherManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_result);

        weatherManager = new WeatherManager();
        Weather weather;
        City city;
        String date;

        try {
            city = JsonAdapter.toObject(getIntent().getStringExtra("city"), City.class);
            date = getIntent().getStringExtra("date");
            String jsonResult = weatherManager.execute(city).get();
            weather = weatherManager.adaptJsonToWeather(jsonResult, city);
            if (weather != null) {
                ImageView imageView = findViewById(R.id.imageView);
                TextView title = findViewById(R.id.textView8);
                TextView subtitle = findViewById(R.id.textView7);
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
                        title.setText(WeatherStatus.SNOW.toString());
                        break;
                    case RAIN:
                        if (subtitle.getText().equals("light rain")) {
                            imageView.setImageResource(R.drawable.light_rain);
                            title.setText(WeatherStatus.DRIZZLE.toString());
                        } else {
                            imageView.setImageResource(R.drawable.rain);
                            title.setText(WeatherStatus.RAIN.toString());
                        }
                        break;
                    case DRIZZLE:
                        imageView.setImageResource(R.drawable.light_rain);
                        title.setText(WeatherStatus.DRIZZLE.toString());
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
                        title.setText(WeatherStatus.CLOUDS.toString());
                        break;
                    case THUNDERSTORM:
                        imageView.setImageResource(R.drawable.thunderstorm);
                        title.setText(WeatherStatus.THUNDERSTORM.toString());
                        break;
                    case MIST:
                        setAtmosphereCase(WeatherStatus.MIST, isNight);
                        break;
                    case FOG:
                        setAtmosphereCase(WeatherStatus.FOG, isNight);
                        break;
                    case DUST:
                        setAtmosphereCase(WeatherStatus.DUST, isNight);
                        break;
                    case HAZE:
                        setAtmosphereCase(WeatherStatus.HAZE, isNight);
                        break;
                    case SAND:
                        setAtmosphereCase(WeatherStatus.SAND, isNight);
                        break;
                    case SMOKE:
                        setAtmosphereCase(WeatherStatus.SMOKE, isNight);
                        break;
                    case SQUALLS:
                        setAtmosphereCase(WeatherStatus.SQUALLS, isNight);
                        break;
                    case TORNADO:
                        setAtmosphereCase(WeatherStatus.TORNADO, isNight);
                    default:
                        throw new Exception("Switch default case occurs");
                }

                TextView cityNameTV = findViewById(R.id.textView16);
                TextView pressureTV = findViewById(R.id.textView5);
                TextView humidityTV = findViewById(R.id.textView4);
                TextView windSpeedTV = findViewById(R.id.textView9);

                cityNameTV.setText(weather.getCity().getName() + " (" + weather.getCity().getCountry() + ")");
                weatherManager.setTemperatureInView(getWindow().getDecorView().getRootView(), weather.getTemperature());
                pressureTV.setText(weather.getPressure().toString() + " hPa");
                humidityTV.setText(weather.getHumidity().toString() + "%");
                windSpeedTV.setText(weather.getWindSpeed().toString() + " m/s");

                if (isNight) {
                    getWindow().getDecorView().setBackgroundResource(R.drawable.fh);
                    int color = getColor(R.color.white);
                    ((TextView) findViewById(R.id.textView13)).setTextColor(color);
                    ((TextView) findViewById(R.id.textView14)).setTextColor(color);
                    ((TextView) findViewById(R.id.textView15)).setTextColor(color);
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

            } else {
                errorScreen("Unable to find the weather...");
            }
        } catch (Exception e) {
            errorScreen("Error during the operation!");
        }
    }

    private void errorScreen(String message) {
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.umbrella);
        TextView title = findViewById(R.id.textView8);
        TextView subtitle = findViewById(R.id.textView7);
        title.setVisibility(View.INVISIBLE);
        subtitle.setText(message);
    }

    private void setAtmosphereCase(WeatherStatus weatherStatus, boolean isNight) {
        ImageView imageView = findViewById(R.id.imageView);
        if (isNight) imageView.setImageResource(R.drawable.nightly_fog);
        else imageView.setImageResource(R.drawable.daily_fog);
        TextView title = findViewById(R.id.textView8);
        title.setText(weatherStatus.toString());
    }
}
