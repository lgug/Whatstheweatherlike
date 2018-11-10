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
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import weather.whatstheweatherlike.R;
import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.beans.CityList;
import weather.whatstheweatherlike.beans.Coords;
import weather.whatstheweatherlike.beans.InputData;
import weather.whatstheweatherlike.beans.Temperature;
import weather.whatstheweatherlike.beans.WeatherDate;
import weather.whatstheweatherlike.services.CitiesManager;
import weather.whatstheweatherlike.services.JsonAdapter;
import weather.whatstheweatherlike.utils.Converter;

public class WeatherInputsActivity extends AppCompatActivity {

    private final static Long oneDayInMillis = 24 * 3600 * 1000L;

    private Button goButton;

    private CitiesManager citiesManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location mLocation = null;

    private boolean usedLocation = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_inputs);

        final Drawable gpsWhite = getDrawable(R.drawable.gps_white);
        final EditText cityEditText = findViewById(R.id.editText4);
        final EditText dateEditText = findViewById(R.id.editText3);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Long today = (new Date()).getTime();
        final Spinner calendarSpinner = findViewById(R.id.spinner5);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.calendar_spinner_item, Arrays.asList(
                        "Now",
                        simpleDateFormat.format(new Date(today + oneDayInMillis)),
                        simpleDateFormat.format(new Date(today + oneDayInMillis * 2)),
                        simpleDateFormat.format(new Date(today + oneDayInMillis * 3)),
                        simpleDateFormat.format(new Date(today + oneDayInMillis * 4)),
                        simpleDateFormat.format(new Date(today + oneDayInMillis * 5))
        ));
        calendarSpinner.setAdapter(adapter);
        calendarSpinner.setSelected(false);
        calendarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dateEditText.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                dateEditText.setText("");
            }
        });

        final Button locationButton = findViewById(R.id.button5);
        locationButton.setBackground(gpsWhite);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                synchronized (this) {
                    if (locationButton.getBackground() != gpsWhite) {
                        locationButton.setBackground(gpsWhite);
                        cityEditText.setText("");
                        cityEditText.setEnabled(true);
                        usedLocation = false;
                        return;
                    } else {
                        locationButton.setBackgroundResource(R.drawable.gps_yellow);
                    }
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
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ignored) { }
                            if (location != null) {
                                mLocation = location;
                                locationButton.setBackgroundResource(R.drawable.gps_green);
                                cityEditText.setText("my location...");
                                cityEditText.setEnabled(false);
                                usedLocation = true;
                            } else {
                                Toast.makeText(getApplicationContext(), "You must turn on your GPS location!", Toast.LENGTH_LONG).show();
                                locationButton.setBackgroundResource(R.drawable.gps_red);
                            }
                        }
                    });
                    fusedLocationProviderClient.getLastLocation().addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Your location is not available", Toast.LENGTH_LONG).show();
                            locationButton.setBackgroundResource(R.drawable.gps_red);
                        }
                    });
                }
            }
        });

        Button calendarButton = findViewById(R.id.button6);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarSpinner.performClick();
            }
        });

        goButton = findViewById(R.id.button2);
        goButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                citiesManager = new CitiesManager();
                String city = ((EditText) findViewById(R.id.editText4)).getText().toString();
                String date = ((EditText) findViewById(R.id.editText3)).getText().toString();

                if (city.equals("")) {
                    Toast.makeText(getApplicationContext(),"Error! City field must not be empty.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (date.equals("")) {
                    Toast.makeText(getApplicationContext(), "Error! Date field must not be empty.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!date.matches(Converter.DATE_PATTERN) && !date.toUpperCase().equals("NOW")) {
                    Toast.makeText(getApplicationContext(), "Error! Date field is not valid.", Toast.LENGTH_SHORT).show();
                    return;
                }

                goButton.setEnabled(false);

                citiesManager.setCityName(city);
                List<City> cities = null;
                try {
                    cities = citiesManager.execute().get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                InputData inputData = new InputData();
                inputData.setUsedLocation(usedLocation);
                if (usedLocation)
                    inputData.setCities(new CityList(Collections.singletonList(
                            new City(new Coords(
                                    mLocation.getLatitude(),
                                    mLocation.getLongitude()
                            ))
                    )));
                else inputData.setCities(new CityList(cities));
                if (date.toUpperCase().equals(WeatherDate.NOW)) {
                    inputData.setWeatherDate(new WeatherDate(null, true));
                } else {
                    inputData.setWeatherDate(new WeatherDate(Converter.getGregorianCalendar(date), false));
                }

                if (cities != null) {
                    Intent nextIntent;
                    if (usedLocation || (cities.size() == 0) || (cities.size() == 1))
                        nextIntent = new Intent(getApplicationContext(), WeatherResultActivity.class);
                    else nextIntent = new Intent(getApplicationContext(), CityListActivity.class);
                    nextIntent.putExtra("data", JsonAdapter.toJson(inputData));
                    startActivity(nextIntent);
                } else {
                    Toast.makeText(getApplicationContext(),"Error! City field is not valid", Toast.LENGTH_SHORT).show();
                    goButton.setEnabled(true);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        goButton.setEnabled(true);
    }
}
