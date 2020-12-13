package com.github.kataokanagi.youtubeapi;

import com.github.kataokanagi.utils.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.Map;

public class JSONHttpRequest {

    private static final String TAG = "JSONHttpRequest";

    private Proxy proxy;
    private String method;
    private String baseUrl;
    private Map<String, String> params;
    private Map<String, String> headers;
    private String payload;

    private HttpURLConnection urlConnection;

    public JSONHttpRequest(String method, String baseUrl) {
        this.method = method;
        this.baseUrl = baseUrl;
    }

    public JSONHttpRequest(Proxy proxy, String method, String baseUrl) {
        this.proxy = proxy;
        this.method = method;
        this.baseUrl = baseUrl;
    }

    // URL parameters
    public JSONHttpRequest setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    // Custom http request headers
    public JSONHttpRequest setHeaders(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    // POST payload (optional)
    public JSONHttpRequest setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    public JSONHttpRequest doRequest() {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);

        if (params != null) {
            boolean first = true;
            for (Map.Entry<String, String> entry : this.params.entrySet()) {
                if (first) {
                    urlBuilder.append('?');
                    first = false;
                } else {
                    urlBuilder.append('&');
                }

                urlBuilder.append(entry.getKey());
                urlBuilder.append('=');
                urlBuilder.append(entry.getValue());
            }
        }

        String urlStr = urlBuilder.toString();
        Log.d(TAG, urlStr);

        try {
            URL url = new URL(urlStr);

            if (this.proxy != null) {
                urlConnection = (HttpURLConnection) url.openConnection(this.proxy);
            } else {
                urlConnection = (HttpURLConnection) url.openConnection();
            }
            urlConnection.setRequestMethod(this.method);
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Content-Type", "application/json");

            if (this.headers != null) {
                for (Map.Entry<String, String> entry : this.headers.entrySet()) {
                    urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if (this.payload != null) {
                urlConnection.setDoOutput(true);
                urlConnection.setFixedLengthStreamingMode(this.payload.length());
            }

            urlConnection.connect();
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + urlStr);
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
            e.printStackTrace();
        }

        return this;
    }

    public String getResponse() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line = "";
            StringBuilder responseBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }

            return responseBuilder.toString();
        } catch (IOException e) {
            Log.e(TAG, "result() IOException: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
