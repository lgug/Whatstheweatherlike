package weather.whatstheweatherlike.activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import java.io.IOException;

import weather.whatstheweatherlike.R;
import weather.whatstheweatherlike.beans.Weather;
import weather.whatstheweatherlike.fragments.WeatherInputsFragment;
import weather.whatstheweatherlike.services.WeatherManager;

public class WeatherActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Fragment inputsFragment = new WeatherInputsFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, inputsFragment);
        fragmentTransaction.commit();

//        final Button goButton = findViewById(R.id.button2);
//        goButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                goButton.setVisibility(View.INVISIBLE);
//
//                //get inputs
//
//                WeatherManager weatherManager = new WeatherManager();
//                Weather weather;
//                try {
//                    String jsonWeather = weatherManager.getWeather(null);
//                    weather = weatherManager.adaptJsonToWeather(jsonWeather,null);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                //usare un pezzo di activity, guardare la documentazione
//                //use of weather
//            }
//        });
    }

}
