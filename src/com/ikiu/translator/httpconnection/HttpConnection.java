package com.ikiu.translator.httpconnection;

import com.oracle.javafx.jmx.json.JSONException;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by fahime on 1/31/16.
 */
public class HttpConnection {
    private static String LOG_TAG = "HttpConnection";

    private static HttpURLConnection createConnection(String urlString) {
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        try {
            url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpURLConnection;
    }


    public static HttpResponse get(String url) {
//        Log.i(LOG_TAG, "get");
        HttpResponse response = new HttpResponse();
        HttpURLConnection httpURLConnection = createConnection(url);
        try {
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");
//            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            response = getHttpResponse(httpURLConnection);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
        return response;
    }

    private static HttpResponse getHttpResponse(HttpURLConnection httpURLConnection) {
        HttpResponse response = new HttpResponse();
        String responseBody = "";

        try {
            httpURLConnection.connect();
            try {
                responseBody = convertInputStreamToString(httpURLConnection.getInputStream());
            } catch (Exception ignore) {
            }
            response.setResponseString(responseBody);
            response.setResponseCode(httpURLConnection.getResponseCode());
//            response.setResponseJson(new JSONObject(responseBody));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    protected static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = "";
        StringBuilder result = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line).append("\r\n");
        }
            /* Close Stream */
        if (inputStream != null) {
            inputStream.close();
        }
        return result.toString();
    }
}
