package weather.whatstheweatherlike.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

import weather.whatstheweatherlike.R;
import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.beans.CityList;
import weather.whatstheweatherlike.beans.Coords;
import weather.whatstheweatherlike.services.CitiesManager;
import weather.whatstheweatherlike.services.JsonAdapter;

public class WeatherInputsActivity extends AppCompatActivity {

    private CitiesManager citiesManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location mLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_inputs);

        final Button locationButton = findViewById(R.id.button5);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, 1337);
                }

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            mLocation = location;
                        } else {
                            Toast.makeText(getApplicationContext(), "You must turn on your GPS location!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                fusedLocationProviderClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Your location is not available", Toast.LENGTH_LONG).show();
                    }
                });

                if (mLocation != null) {
                    locationButton.setBackgroundResource(R.drawable.gps_green);
                    EditText cityEditText = findViewById(R.id.editText4);
                    cityEditText.setText("my location");
                } else {
                    locationButton.setBackgroundResource(R.drawable.gps_red);
                }
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
                popUpIntent.putExtra("location", JsonAdapter.toJson(Coords.getInstanceFromLocation(mLocation)));
                startActivity(popUpIntent);
            }
        });

    }
}
