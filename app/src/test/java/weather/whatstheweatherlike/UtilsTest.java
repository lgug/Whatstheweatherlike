package weather.whatstheweatherlike;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import weather.whatstheweatherlike.utils.Converter;

public class UtilsTest {

    private long exampleTime = 1537722677;
    private float exampleFloat1 = 16.48535f;
    private float exampleFloat2 = 43.12439f;


    @Test
    public void DateConverterTest() {
        String date = new Date().toString();
        Date date1 = new Date(TimeUnit.MILLISECONDS.convert(exampleTime, TimeUnit.SECONDS));

        float newNumber1 = Converter.round(exampleFloat1, 1);
        float newNumber2 = Converter.round(exampleFloat2, 1);
        Assert.assertEquals(newNumber1, 16.5f, 0);
        Assert.assertEquals(newNumber2, 43.1f, 0);
    }

}
