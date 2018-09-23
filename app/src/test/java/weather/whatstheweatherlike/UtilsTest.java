package weather.whatstheweatherlike;

import android.text.format.Time;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UtilsTest {

    private long exampleTime = 1537722677;

    @Test
    public void DateConverterTest() {
        String date = new Date().toString();
        Date date1 = new Date(TimeUnit.MILLISECONDS.convert(exampleTime, TimeUnit.SECONDS));

    }

}
