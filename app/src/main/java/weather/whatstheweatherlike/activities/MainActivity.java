package weather.whatstheweatherlike.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import weather.whatstheweatherlike.R;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random();
        int[] colors = new int[]{
                R.color.buttonsColor01,
                R.color.buttonsColor02,
                R.color.buttonsColor03,
                R.color.buttonsColor04,
                R.color.buttonsColor05,
                R.color.buttonsColor06
        };
        int color = colors[random.nextInt(colors.length)];

        Button weatherButton = findViewById(R.id.button);
        weatherButton.setBackgroundColor(getColor(color));
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeatherInputsActivity.class);
                startActivity(intent);
            }
        });

        Button supportButton = findViewById(R.id.button3);
        supportButton.setBackgroundColor(getColor(color));
        supportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SupportActivity.class);
                startActivity(intent);
            }
        });

        Button infoAndSettingButton = findViewById(R.id.button4);
        infoAndSettingButton.setBackgroundColor(getColor(color));
        infoAndSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //...
            }
        });
    }
}
