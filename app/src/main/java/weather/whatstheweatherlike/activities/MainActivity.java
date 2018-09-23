package weather.whatstheweatherlike.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import weather.whatstheweatherlike.AppDatabase;
import weather.whatstheweatherlike.R;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
//        if (!db.populateWithCities(getApplicationContext()))
//            Toast.makeText(getApplicationContext(),"Unable to retrieve city list. Retry!", Toast.LENGTH_LONG).show();

        Button weatherButton = findViewById(R.id.button);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeatherInputsActivity.class);
                startActivity(intent);
            }
        });

        Button supportButton = findViewById(R.id.button3);
        supportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SupportActivity.class);
                startActivity(intent);
            }
        });
    }
}
