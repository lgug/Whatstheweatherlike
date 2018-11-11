package weather.whatstheweatherlike.services;

import android.os.AsyncTask;

import weather.whatstheweatherlike.beans.AdvancedWeather;
import weather.whatstheweatherlike.beans.CurrentForecast;

public class AdvancedDataWeatherManager extends AsyncTask<CurrentForecast, Void, AdvancedWeather> {

    private static final String KEY = "5abbab06a4bb2db86621e0f48a5ccc3d";

    public String getUviRay() {
        return null;
    }

    @Override
    protected AdvancedWeather doInBackground(CurrentForecast... currentForecasts) {
        return null;
    }
}
