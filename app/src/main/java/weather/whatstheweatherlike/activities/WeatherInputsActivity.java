package weather.whatstheweatherlike.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import weather.whatstheweatherlike.R;

public class WeatherInputsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_inputs);

        Button goButton = findViewById(R.id.button2);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = ((EditText) findViewById(R.id.editText4)).getText().toString();
                String date = ((EditText) findViewById(R.id.editText3)).getText().toString();

                Intent intent = new Intent(getApplicationContext(), WeatherResultActivity.class);
                intent.putExtra("city", city);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

    }
}
