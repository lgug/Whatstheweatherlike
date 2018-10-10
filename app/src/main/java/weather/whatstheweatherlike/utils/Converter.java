package weather.whatstheweatherlike.utils;

public class Converter {

    public static Float fromFahrenheitToCelsius(Float fahrenheit) {
        return (fahrenheit - 32) / 1.8F;
    }

    public static Float fromKelvinToCelsius(Float kelvin) {
        return kelvin - 273.15F;
    }

    public static Float round(Float number, int decimals) {
        double tempNumber = number * Math.pow(10, decimals);
        return (float) (Math.round(tempNumber) / Math.pow(10, decimals));
    }

}
