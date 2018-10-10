package weather.whatstheweatherlike.services;

import android.content.Context;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import weather.whatstheweatherlike.beans.City;

public class JsonAdapter {

    public final static int TREE_METHOD = 1;
    public final static int CALLBACK_METHOD = 2;


    public static List<City> adaptToCityList(Context context, String jsonFileName, int method) throws JSONException, IOException {
        List<City> cities = new ArrayList<>();
        switch (method) {
            case TREE_METHOD:
                InputStream inputStream = context.getAssets().open(jsonFileName);
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
                return cities;
            case CALLBACK_METHOD:
                JsonFactory f = new MappingJsonFactory();
                JsonParser jp = f.createJsonParser(new InputStreamReader(context.getAssets().open(jsonFileName)));

                JsonToken current = jp.nextToken();
                if (current == JsonToken.START_ARRAY) {
                    while (jp.nextToken() != JsonToken.END_ARRAY) {
                        City city = new City();
                        JsonNode jsonNode = jp.readValueAsTree();
                        city.setId(jsonNode.get("id").asInt());
                        city.setName(jsonNode.get("name").asText());
                        city.setCountry(jsonNode.get("country").asText());
                        city.setLat(jsonNode.get("coord").get("lat").asDouble());
                        city.setLon(jsonNode.get("coord").get("lon").asDouble());
                        cities.add(city);
                    }
                }
                return cities;
            default:
                return null;
        }
    }

}
