package weather.whatstheweatherlike.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

import weather.whatstheweatherlike.R;
import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.beans.CityList;
import weather.whatstheweatherlike.services.CitiesManager;
import weather.whatstheweatherlike.services.JsonAdapter;

public class WeatherInputsActivity extends AppCompatActivity {

    private CitiesManager citiesManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String coords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_inputs);

        Button locationButton = findViewById(R.id.button5);
        final WeatherInputsActivity instance = this;
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(instance);
                if (ActivityCompat.checkSelfPermission(instance, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(instance, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        coords = String.valueOf(location.getLatitude()) + ";" +
                                String.valueOf(String.valueOf(location.getLongitude()));
                    }
                });
            }
        });

        citiesManager = new CitiesManager();
        Button goButton = findViewById(R.id.button2);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = ((EditText) findViewById(R.id.editText4)).getText().toString();
                citiesManager.setCityName(city);
                List<City> cities = null;
                try {
                    cities = citiesManager.execute().get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                String date = ((EditText) findViewById(R.id.editText3)).getText().toString();

                Intent popUpIntent = new Intent(getApplicationContext(), CityListActivity.class);
                popUpIntent.putExtra("cities", JsonAdapter.toJson(new CityList(cities)));
                popUpIntent.putExtra("date", date);
                startActivity(popUpIntent);
            }
        });

    }
}
