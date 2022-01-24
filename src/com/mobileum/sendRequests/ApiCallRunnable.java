package com.mobileum.sendRequests;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class ApiCallRunnable implements Runnable {
    private final String url;

    ApiCallRunnable(String url) {
        this.url = url;
    }

    @Override
    public void run() {

        //String url = "http://127.0.0.1:3001";
        String userCredentials = "user:pass";
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
        HttpURLConnection conn = null;
        try {
            URL siteURL = new URL(url);

            // HttpURLConnection: A URLConnection with support for HTTP-specific features. See the spec for details.

            // openConnection(): Returns a URLConnection instance that represents a connection to the remote object referred to by the URL.
            conn = (HttpURLConnection) siteURL.openConnection();
            conn.setRequestProperty("Authorization", basicAuth);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("GET");
            // connect(): Opens a communications link to the resource referenced by this URL, if such a connection has not already been established.
            conn.connect();

            // Delay between calls
            //Thread.sleep(2000);


            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {

                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            // System.out.println(content.toString());
            try {

                JSONObject jsonObject = new JSONObject(content.toString());
                // <handle response>
                // <---------------->
                //
                // <---------------->
                // </handle response>

            } catch (JSONException err) {
                System.out.println(content);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        System.out.println(Thread.currentThread().getName() + " Done");
    }
}
