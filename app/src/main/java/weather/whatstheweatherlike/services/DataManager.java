package weather.whatstheweatherlike.services;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import weather.whatstheweatherlike.beans.City;

public class DataManager {

    public DataManager() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<City> getCityList(Context context) throws IOException, JSONException {
//        String fileName = "city.list.json";
        String[] files = new String[]{"city1.json", "city2.json", "city3.json"};

        List<City> cities = new ArrayList<>();

        for(String file:files) {
            InputStream inputStream = context.getAssets().open(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            JSONArray jsonArray = new JSONArray(json.toString());
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                City city = new City();
                city.setId(jsonObject.getInt("id"));
                city.setName(jsonObject.getString("name"));
                city.setCountry(jsonObject.getString("country"));
                JSONObject coord = jsonObject.getJSONObject("coord");
                city.setLat(coord.getDouble("lat"));
                city.setLon(coord.getDouble("lon"));
                cities.add(city);
            }
        }


        return cities;
    }

}
