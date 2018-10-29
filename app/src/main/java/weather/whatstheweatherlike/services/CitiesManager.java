package weather.whatstheweatherlike.services;

import android.os.AsyncTask;

//import com.wirefreethought.geodb.client.GeoDbApi;
//import com.wirefreethought.geodb.client.model.CitiesResponse;
//import com.wirefreethought.geodb.client.model.CitySummary;
//import com.wirefreethought.geodb.client.model.GeoDbInstanceType;
//import com.wirefreethought.geodb.client.net.GeoDbApiClient;
//import com.wirefreethought.geodb.client.request.FindCitiesRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import weather.whatstheweatherlike.beans.City;

public class CitiesManager extends AsyncTask<Void, Void, List<City>> {

    private final static String SERVER_DNS = "ec2-18-184-69-104.eu-central-1.compute.amazonaws.com";
    private final static Integer SERVER_PORT = 8081;

    private String cityName;

    public CitiesManager() {
    }

    public CitiesManager(String cityName) {
        this.cityName = cityName;
    }

    public void normalizeCity() {
        StringBuilder newString = new StringBuilder();
        String[] names = cityName.split(" ");
        for (String s: names) {
            if (s.length() > 2) {
                newString.append(String.valueOf(s.charAt(0)).toUpperCase()).append(s.substring(1));
            } else {
                newString.append(s);
            }
        }
        cityName = newString.toString();
    }

//    public List<City> getPossibleCityList() {
//        GeoDbApiClient geoDbApiClient = new GeoDbApiClient(GeoDbInstanceType.FREE);
//        GeoDbApi geoDbApi = new GeoDbApi(geoDbApiClient);
//        CitiesResponse citiesResponse = geoDbApi.findCities(
//                FindCitiesRequest.builder()
//                        .namePrefix(cityName)
//                        .build()
//        );
//        List<City> cityList = new ArrayList<>();
//        for (CitySummary citySummary: citiesResponse.getData()) {
//            City city = new City();
//            city.setName(citySummary.getCity());
//            city.setCountry(citySummary.getCountryCode());
//            city.setLat(citySummary.getLatitude());
//            city.setLon(citySummary.getLongitude());
//            cityList.add(city);
//        }
//        return cityList;
//    }

    private List<City> getPossibleCityList() throws IOException, JSONException {
        //URL url = new URL("http://192.168.1.66:8081/api/get-cities?city=" + cityName);
        URL url = new URL("http://" + SERVER_DNS + ":" + SERVER_PORT.toString() + "/api/get-cities?city=" + cityName);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.connect();
        InputStream inputStream = httpURLConnection.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        String json = byteArrayOutputStream.toString();
        JSONArray jsonArray = new JSONArray(json);
        List<City> cityList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            City city = JsonAdapter.toObject(jsonObject.toString(), City.class);
            cityList.add(city);
        }
        return cityList;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    protected List<City> doInBackground(Void... voids) {
        try {
            return getPossibleCityList();
        } catch (IOException | JSONException e) {
            return null;
        }
    }
}
