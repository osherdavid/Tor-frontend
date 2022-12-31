package com.eph.tor;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public abstract class HTTPRequest extends Thread {
    private String baseServerAddress = "http://tor-backend.oa.r.appspot.com";
    protected String optionalUrl = "";
    private int timeout = 5000;
    protected CallBackFunction callback = null;
    protected CallBackFunction cleanup = null;
    protected String requestType = "GET";
    protected String postParams = "";
    public abstract <T> boolean task(String result);

    @Override
    public void run() {
        try {
            URL url = new URL(baseServerAddress + optionalUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(this.timeout);
            conn.setReadTimeout(this.timeout);

            switch (this.requestType) {
                case "GET":
                    this.sendGet(conn);
                    break;
                case "POST":
                    this.sendPost(conn);
                    break;
            }

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
            else {
                throw new IOException("Got Error: " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
            if (this.cleanup != null) {
                this.cleanup.callback(e);
            }
        }
    }

    public void sendPost(HttpURLConnection conn) throws IOException {
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream out = new BufferedOutputStream(conn.getOutputStream());

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
        writer.write(this.postParams);
        writer.flush();
        writer.close();
        out.close();
        conn.connect();
    }

    public void sendGet(HttpURLConnection conn) throws IOException {
        conn.setRequestMethod("GET");
        conn.connect();
    }
}
