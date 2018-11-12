package weather.whatstheweatherlike.services;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetAddress;

public class TryConnectionManager extends AsyncTask<Void, Void, String>{

    private String tryConnection() {
        boolean ping;
        try {
            InetAddress inetAddress = InetAddress.getByName("www.google.com");
            ping = inetAddress.isReachable(2000);
        } catch (IOException e) {
            ping = false;
        }
        if (ping) {
            boolean serverPing;
            try {
                InetAddress cityServerAddress = InetAddress.getByName("ec2-3-120-27-26.eu-central-1.compute.amazonaws.com");
                serverPing = cityServerAddress.isReachable(1000);
            } catch (IOException e) {
                serverPing = false;
            }
            if (serverPing) return "OK";
            else return "Server is unreachable. Please retry";
        } else {
            return "No Internet connection...";
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        return tryConnection();
    }
}
