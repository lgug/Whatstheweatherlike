package weather.whatstheweatherlike.utils;

import android.content.SharedPreferences;

import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {

    public final static String DATE_PATTERN =  "(\\d{4})[-/]?(\\d{1,2})[-/]?(\\d{1,2})";

    public static String convertTemperature(SharedPreferences sharedPreferences, Float temperature) {
        String tempSetting = sharedPreferences.getString("Temperature", "Celsius (°C)");
        switch (tempSetting) {
            case "Celsius (°C)":
                Float celsius = fromKelvinToCelsius(temperature);
                if (celsius > -100 && celsius < 100)
                    return round(celsius, 1) + " °C";
                else
                    return (int)(float)round(celsius, 0) + " °C";
            case "Fahrenheit (°F)":
                Float fahrenheit = fromKelvinToFahrenheit(temperature);
                if (fahrenheit > -100 && fahrenheit < 100)
                    return round(fahrenheit, 1) + " °F";
                else
                    return (int)(float)round(fahrenheit, 0) + " °F";
            case "Kelvin (K)":
                if (temperature < 100)
                    return round(temperature, 1) + " K";
                else
                    return (int)(float)round(temperature, 0) + " K";
        }
        return null;
    }

    public static Float fromKelvinToFahrenheit(Float kelvin) {
        return (fromKelvinToCelsius(kelvin) * 1.8F) + 32;
    }

    public static Float fromKelvinToCelsius(Float kelvin) {
        return kelvin - 273.15F;
    }

    public static Float round(Float number, int decimals) {
        double tempNumber = number * Math.pow(10, decimals);
        return (float) (Math.round(tempNumber) / Math.pow(10, decimals));
    }

    public static Float fromHPaToBar(Float hPa) {
        return hPa / 1000;
    }

    public static Float fromHPaToNm2(Float hPa) {
        return hPa * 100;
    }

    public static Float fromMsToMph(Float ms) {
        return ms * 2.23694F;
    }

    public static Float fromMsToKmh(Float ms) {
        return ms * 3.6F;
    }

    public static Float fromMsToKnots(Float ms) {
        return ms * 1.943844F;
    }

    public static GregorianCalendar getGregorianCalendar(String date) {
        Pattern pattern = Pattern.compile(DATE_PATTERN);
        Matcher matcher =pattern.matcher(date);
        if (matcher.matches())
            return new GregorianCalendar(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3))
            );
        else return null;
    }

    public static String convertPressure(SharedPreferences sharedPreferences, Float pressure) {
        String presSetting = sharedPreferences.getString("Pressure", "hPa");
        switch (presSetting) {
            case "hPa":
                return (int)(float)round(pressure, 0) + " hPa";
            case "N/m²":
                return (int)(float)round(fromHPaToNm2(pressure), 0) + " N/m²";
            case "Bar":
                return round(fromHPaToBar(pressure), 2) + " bar";

        }
        return null;
    }

    public static String convertWindSpeed(SharedPreferences sharedPreferences, Float windSpeed) {
        String wsSetting = sharedPreferences.getString("Wind speed", "m/s");
        switch (wsSetting) {
            case "m/s":
                return windSpeed + " m/s";
            case "mph":
                return round(fromMsToMph(windSpeed), 1) + " mph";
            case "km/h":
                Float kmh = fromMsToKmh(windSpeed);
                if (kmh < 100)
                    return round(kmh, 1) + " km/h";
                else
                    return (int)(float)round(kmh, 0) +" km/h";
            case "Knots":
                Integer knots = (int)(float)round(fromMsToKnots(windSpeed), 0);
                if (knots == 1)
                    return knots + " knot";
                else
                    return knots + " knots";
        }
        return null;
    }

}
