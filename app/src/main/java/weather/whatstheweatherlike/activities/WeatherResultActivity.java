package weather.whatstheweatherlike.activities;

import android.annotation.SuppressLint;
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
import weather.whatstheweatherlike.beans.Weather;
import weather.whatstheweatherlike.enums.WeatherStatus;
import weather.whatstheweatherlike.services.WeatherManager;

public class WeatherResultActivity extends AppCompatActivity {

    WeatherManager weatherManager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_result);

        weatherManager = new WeatherManager();
        Weather weather = null;

        String city = getIntent().getStringExtra("city");
        String date = getIntent().getStringExtra("date");
        try {
            /* temporary - begin */
            String[] nc = city.split(",");
            City tempCity = new City(null, nc[0], nc[1], null, null);
            /* temporary - end */
            String jsonResult = weatherManager.execute(tempCity).get();
            weather = weatherManager.adaptJsonToWeather(jsonResult, city);
            if (weather != null) {
                ImageView imageView = findViewById(R.id.imageView);
                TextView title = findViewById(R.id.textView8);
                TextView subtitle = findViewById(R.id.textView7);

                subtitle.setText(weather.getWeatherDescription());
                switch (weather.getWeather()) {
                    case CLEAR:
                        imageView.setImageResource(R.drawable.daily_clear);
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
                            imageView.setImageResource(R.drawable.daily_slightly_cloudy);
                        } else if (subtitle.getText().equals("scattered clouds")) {
                            imageView.setImageResource(R.drawable.daily_cloudiness);
                        } else {
                            imageView.setImageResource(R.drawable.daily_overcast_clouds);
                        }
                        title.setText(WeatherStatus.CLOUDS.toString());
                        break;
                    case THUNDERSTORM:
                        imageView.setImageResource(R.drawable.thunderstorm);
                        title.setText(WeatherStatus.THUNDERSTORM.toString());
                        break;
                    case ATMOSPHERE:
                        if (subtitle.getText().equals("mist") || subtitle.getText().equals("haze") ||
                                subtitle.getText().equals("fog") || subtitle.getText().equals("smoke")) {
                            title.setText("FOG");
                        } else if (subtitle.getText().equals("dust") || subtitle.getText().equals("sand, dust whirls") ||
                                subtitle.getText().equals("sand") || subtitle.getText().equals("volcanic ash")) {
                            title.setText("DUSTY");
                        } else {
                            title.setText(((String) subtitle.getText()).toUpperCase());
                        }
                        imageView.setImageResource(R.drawable.daily_fog);
                        break;
                    default:
                        throw new Exception("Switch default case occurs");
                }

                TextView temperatureTV = findViewById(R.id.textView6);
                TextView pressureTV = findViewById(R.id.textView5);
                TextView humidityTV = findViewById(R.id.textView4);
                TextView windSpeedTV = findViewById(R.id.textView9);

                temperatureTV.setText(
                        weather.getTemperature().getTemperature().toString() + " °C (" +
                        weather.getTemperature().getMinTemperature().toString() + " °C - " +
                        weather.getTemperature().getMaxTemperature().toString() + " °C)");
                pressureTV.setText("Pressure: " + weather.getPressure().toString() + " hPa");
                humidityTV.setText("Humidity: " + weather.getHumidity().toString() + "%");
                windSpeedTV.setText("Wind speed: " + weather.getWindSpeed().toString() + " m/s");

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
}
