package weather.whatstheweatherlike.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import weather.whatstheweatherlike.R;
import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.beans.CityList;
import weather.whatstheweatherlike.beans.Coords;
import weather.whatstheweatherlike.services.JsonAdapter;

public class CityListActivity extends AppCompatActivity {

    private List<City> cityList;
    private String date;
    private Coords location;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        cityList = JsonAdapter.toObject(getIntent().getStringExtra("cities"), CityList.class).getList();
        date = getIntent().getStringExtra("date");
        location = JsonAdapter.toObject(getIntent().getStringExtra("location"), Coords.class);

        if (cityList.size() == 0) {
            if (location != null) {
                startWeatherResultActivity(null);
            } else {
                Intent intent = new Intent(getApplicationContext(), WeatherResultActivity.class);
                startActivity(intent);
            }
        } else if (cityList.size() == 1) {
            startWeatherResultActivity(cityList.get(0));
        } else {
            final LinearLayout linearLayout = findViewById(R.id.linearLayout1);
            for (City city : cityList) {
                LinearLayout horizontalLinearLayout = new LinearLayout(getApplicationContext());
                horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                horizontalLinearLayout.setBackgroundResource(R.drawable.rect);

                TextView textView = new TextView(getApplicationContext());
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setTextSize(23);
                textView.setTextColor(getColor(R.color.black));
                textView.setPadding(8, 15, 0, 15);
                String text = city.getName() + "\n" + city.getCountryName() + " (" + city.getCountry() + ")";
                SpannableString spannableString = new SpannableString(text);
                spannableString.setSpan(
                        new RelativeSizeSpan(0.75f), text.lastIndexOf("\n") + 1, text.length(), 0
                );
                spannableString.setSpan(
                        new StyleSpan(Typeface.BOLD_ITALIC), 0, text.lastIndexOf("\n"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                spannableString.setSpan(
                        new StyleSpan(Typeface.ITALIC), text.lastIndexOf("\n") + 1, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                textView.setText(spannableString);
                horizontalLinearLayout.addView(textView);

                TextView coordsTextView = new TextView(getApplicationContext());
                coordsTextView.setLayoutParams(new LinearLayout.LayoutParams(120, LinearLayout.LayoutParams.MATCH_PARENT, Gravity.END | Gravity.CENTER));
                String coords = city.getLat() + "\n" + city.getLon();
                coordsTextView.setText(coords);
                coordsTextView.setPadding(0, 5, 8, 5);
                coordsTextView.setTextSize(12);
                coordsTextView.setTextColor(getColor(R.color.black));
                horizontalLinearLayout.addView(coordsTextView);

                linearLayout.addView(horizontalLinearLayout);
                textView.setOnClickListener(new CityClickListener(getApplicationContext(), city, date));
            }
        }
    }

    // TODO improve this method
    private void startWeatherResultActivity(City city) {
        Intent intent = new Intent(getApplicationContext(), WeatherResultActivity.class);
        intent.putExtra("city", JsonAdapter.toJson(city));
        intent.putExtra("date", date);
        intent.putExtra("location", JsonAdapter.toJson(location));
        startActivity(intent);
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
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
