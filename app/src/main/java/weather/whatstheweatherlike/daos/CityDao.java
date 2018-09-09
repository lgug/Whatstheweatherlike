package weather.whatstheweatherlike.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.sql.SQLException;
import java.util.List;

import weather.whatstheweatherlike.beans.City;

@Dao
public interface CityDao {

    @Query(value = "SELECT * FROM CITIES")
    List<City> getAllCities() throws SQLException;

    @Query(value = "SELECT * FROM CITIES WHERE CITY_NAME=:name")
    List<City> getCitiesByName(String name) throws SQLException;

    @Query(value = "SELECT * FROM CITIES WHERE COUNTRY=:country")
    List<City> getCitiesByCountry(String country);

    @Query(value = "SELECT * FROM CITIES WHERE LAT=:lat AND LON=:lon")
    City getCityByCoord(Double lat, Double lon);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCity(City city);

}
