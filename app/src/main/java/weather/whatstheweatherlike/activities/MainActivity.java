package weather.whatstheweatherlike.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import weather.whatstheweatherlike.AppDatabase;
import weather.whatstheweatherlike.R;
import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.services.DataManager;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "wtwl").build();
//
//        DataManager dataManager = new DataManager();
//        try {
//            List<City> cityList = dataManager.getCityList();
//            dataManager.insertCitiesIntoDatabase(cityList, db);
//        } catch (IOException ignored) {
//        } catch (JSONException ignored) {}

//        Button weatherButton = findViewById(R.id.button);
//        weatherButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
//                startActivity(intent);
//            }
//        });
//
        Button weatherButton = findViewById(R.id.button);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeatherInputsActivity.class);
                startActivity(intent);
            }
        });
    }
}
