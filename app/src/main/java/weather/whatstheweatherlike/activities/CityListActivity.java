package weather.whatstheweatherlike.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import weather.whatstheweatherlike.R;
import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.beans.CityList;
import weather.whatstheweatherlike.services.JsonAdapter;

public class CityListActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        List<City> cityList = JsonAdapter.toObject(getIntent().getStringExtra("cities"), CityList.class).getList();
        String date = getIntent().getStringExtra("date");

        if (cityList.size() == 0) {
            Intent intent = new Intent(getApplicationContext(), WeatherResultActivity.class);
            startActivity(intent);
        } else if (cityList.size() == 1) {
            Intent intent = new Intent(getApplicationContext(), WeatherResultActivity.class);
            intent.putExtra("city", JsonAdapter.toJson(cityList.get(0)));
            intent.putExtra("date", date);
            startActivity(intent);
        } else {
            final LinearLayout linearLayout = findViewById(R.id.linearLayout1);
            for (City city : cityList) {
                String text = city.getName() + " (" + city.getCountry() + ")";
                TextView textView = new TextView(getApplicationContext());
                textView.setText(text);
                textView.setTextSize(25);
                textView.setTextColor(getColor(R.color.black));
                textView.setPadding(8, 5, 0, 5);
                textView.setBackgroundResource(R.drawable.rect);

                linearLayout.addView(textView);
                textView.setOnClickListener(new CityClickListener(getApplicationContext(), city, date));
            }
        }
    }
}

class CityClickListener implements View.OnClickListener {

    private final static Class clazz = WeatherResultActivity.class;

    private Context context;
    private City city;
    private String date;

    CityClickListener(Context context, City city, String date) {
        this.context = context;
        this.city = city;
        this.date = date;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("city", JsonAdapter.toJson(city));
        intent.putExtra("date", date);
        //intent.putExtra("location", coords); //TODO da unire a citiesManager
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
