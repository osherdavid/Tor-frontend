package com.eph.tor;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public abstract class HTTPRequest extends Thread {
    private String baseServerAddress = "http://192.168.1.17:5000";
    protected String optionalUrl = "";
    private int timeout = 5000;
    protected CallBackFunction callback = null;
    protected CallBackFunction cleanup = null;

    public abstract <T> boolean task(String result);

    @Override
    public void run() {
        try {
            URL url = new URL(baseServerAddress + optionalUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(this.timeout);
            conn.setReadTimeout(this.timeout);
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // read the response
                InputStream inputStream = conn.getInputStream();
                // do something with the response
                Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                if (this.callback != null) {
                    this.callback.callback(this.task(result));
                }
                else {
                    this.task(result);
                }
            }
        } catch (IOException e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
            if (this.cleanup != null) {
                this.cleanup.callback(e);
            }
        }
    }
}
