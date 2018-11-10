package weather.whatstheweatherlike.activities;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import weather.whatstheweatherlike.R;

public class InfoAndSettingActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_and_setting);

        final SharedPreferences sharedPreferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String initValue;
        Integer initPosition;

        final Spinner temperatureSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.temperature_array, R.layout.spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperatureSpinner.setAdapter(adapter1);
        initValue = sharedPreferences.getString("Temperature", null);
        if (initValue != null) {
            initPosition = adapter1.getPosition(initValue);
            temperatureSpinner.setSelection(initPosition);
        }

        final Spinner pressureSpinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.pressure_array, R.layout.spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pressureSpinner.setAdapter(adapter2);
        initValue = sharedPreferences.getString("Pressure", null);
        if (initValue != null) {
            initPosition = adapter2.getPosition(initValue);
            pressureSpinner.setSelection(initPosition);
        }

        final Spinner windSpeedSpinner = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.windsped_array, R.layout.spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        windSpeedSpinner.setAdapter(adapter3);
        initValue = sharedPreferences.getString("Wind speed", null);
        if (initValue != null) {
            initPosition = adapter3.getPosition(initValue);
            windSpeedSpinner.setSelection(initPosition);
        }

        final Spinner languageSpinner = findViewById(R.id.spinner4);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,
                R.array.language_array, R.layout.spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter4);
        initValue = sharedPreferences.getString("Language", null);
        if (initValue != null) {
            initPosition = adapter4.getPosition(initValue);
            languageSpinner.setSelection(initPosition);
        }


        final Button button = findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Temperature", temperatureSpinner.getSelectedItem().toString());
                editor.putString("Pressure", pressureSpinner.getSelectedItem().toString());
                editor.putString("Wind speed", windSpeedSpinner.getSelectedItem().toString());
                editor.putString("Language", languageSpinner.getSelectedItem().toString());
                editor.commit();
                button.setEnabled(false);
                button.setText("SAVED");
            }
        });


    }
}

