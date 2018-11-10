package weather.whatstheweatherlike.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

import java.util.Collections;

import weather.whatstheweatherlike.R;
import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.beans.CityList;
import weather.whatstheweatherlike.beans.InputData;
import weather.whatstheweatherlike.services.JsonAdapter;

public class CityListActivity extends AppCompatActivity {

    private InputData inputData;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        inputData = JsonAdapter.toObject(getIntent().getStringExtra("data"), InputData.class);

        final LinearLayout linearLayout = findViewById(R.id.linearLayout1);
        for (City city : inputData.getCities().getList()) {
            InputData newInputData = new InputData(
                    new CityList(Collections.singletonList(city)),
                    inputData.getWeatherDate(),
                    inputData.isUsedLocation()
            );
            LinearLayout horizontalLinearLayout = new LinearLayout(getApplicationContext());
            horizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLinearLayout.setBackgroundResource(R.drawable.rect);

            TextView textView = new TextView(getApplicationContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextSize(22);
            textView.setTextColor(getColor(R.color.black));
            textView.setPadding(15, 15, 0, 20);
            String text = city.getName() + "\n" + city.getCountryName() + " (" + city.getCountry() + ")";
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(
                    new RelativeSizeSpan(0.7f), text.lastIndexOf("\n") + 1, text.length(), 0
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
            coordsTextView.setLayoutParams(new LinearLayout.LayoutParams(170, LinearLayout.LayoutParams.MATCH_PARENT));
            String coords = city.getCoords().getLatitude() + "\n" + city.getCoords().getLongitude();
            coordsTextView.setGravity(Gravity.END | Gravity.CENTER);
            coordsTextView.setText(coords);
            coordsTextView.setPadding(0, 5, 15, 5);
            coordsTextView.setTextSize(12);
            coordsTextView.setTextColor(getColor(R.color.black));
            horizontalLinearLayout.addView(coordsTextView);

            horizontalLinearLayout.setOnClickListener(new CityClickListener(getApplicationContext(), newInputData));
            linearLayout.addView(horizontalLinearLayout);
        }
    }

}

class CityClickListener implements View.OnClickListener {

    private final static Class clazz = WeatherResultActivity.class;

    private Context context;
    private InputData inputData;

    CityClickListener(Context context, InputData inputData) {
        this.context = context;
        this.inputData = inputData;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("data", JsonAdapter.toJson(inputData));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
