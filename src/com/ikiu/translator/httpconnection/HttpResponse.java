package com.ikiu.translator.httpconnection;


import org.json.simple.JSONObject;

/**
 * Created by fahime on 1/31/16.
 */
public class HttpResponse {
    private String responseString;
    private JSONObject responseJson;
    private int responseCode = Integer.MAX_VALUE;

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public JSONObject getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(JSONObject responseJson) {
        this.responseJson = responseJson;
    }
}
