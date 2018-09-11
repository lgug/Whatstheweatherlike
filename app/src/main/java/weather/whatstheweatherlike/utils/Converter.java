package weather.whatstheweatherlike.utils;

public class Converter {

    public static Float fromFahrenheitToCelsius(Float fahrenheit) {
        return (fahrenheit - 32) / 1.8F;
    }

    public static Float fromKelvinToCelsius(Float kelvin) {
        return kelvin - 273.15F;
    }

}
