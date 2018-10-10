package weather.whatstheweatherlike;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.daos.CityDao;
import weather.whatstheweatherlike.services.JsonAdapter;

import static weather.whatstheweatherlike.services.JsonAdapter.CALLBACK_METHOD;

@Database(entities = {City.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CityDao cityDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "wtwl.db").build();
        }
        return instance;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean populateWithCities(Context context) {
        List<City> cityList;
        try {
            cityList = JsonAdapter.adaptToCityList(context, "city.list.json", CALLBACK_METHOD);
            if (cityList == null) return false;
            for (City city: cityList) {
                instance.cityDao().insertCity(city);
            }
        } catch (IOException | JSONException e) {
            return false;
        }
        return true;
    }
}
