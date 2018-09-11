package weather.whatstheweatherlike.services;

import android.arch.persistence.room.RoomDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import weather.whatstheweatherlike.AppDatabase;
import weather.whatstheweatherlike.beans.City;

public class DataManager {

    public DataManager() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<City> getCityList() throws IOException, JSONException {
        String file = "src/main/res/city.list.json";

        List<City> cities = new ArrayList<>();
        String json = new String(Files.readAllBytes(Paths.get(file)));
        JSONArray jsonArray = new JSONArray(json);
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            City city = new City();
            city.setId(jsonObject.getInt("id"));
            city.setName(jsonObject.getString("name"));
            city.setCountry(jsonObject.getString("country"));
            JSONObject coord = jsonObject.getJSONObject("coord");
            city.setLat(coord.getDouble("lat"));
            city.setLon(coord.getDouble("lon"));
        }

        return cities;
    }

    public void insertCitiesIntoDatabase(List<City> cityList, AppDatabase appDatabase) {
        for (City city: cityList) {
            appDatabase.cityDao().insertCity(city);
        }
    }

}
