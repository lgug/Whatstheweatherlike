package weather.whatstheweatherlike;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import weather.whatstheweatherlike.beans.City;
import weather.whatstheweatherlike.daos.CityDao;

@Database(entities = {City.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CityDao cityDao();
}
