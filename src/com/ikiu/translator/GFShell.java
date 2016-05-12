package com.ikiu.translator;


import com.ikiu.tagger.util.ConfigurationTask;
import com.ikiu.translator.httpconnection.HttpConnection;
import com.ikiu.translator.httpconnection.HttpResponse;
import sun.jvm.hotspot.debugger.SymbolLookup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by fahime on 3/3/16.
 */
public class GFShell {
    private static String temp;
    private static String URL;
    private static Boolean isServerRunning = false;

    static {
//        runServer();
        URL = "http://localhost:41296";
        temp = getTemp();
        ConfigurationTask configurationTask = ConfigurationTask.getInstance();
        importGrammars(configurationTask.getEnglishFile(), configurationTask.getPersianFile());
    }

    static public void runServer() {
        if (executeCommand("gf -server").contains("Starting HTTP server"))
            isServerRunning = true;
    }

    static public void changeLanguageFiles()
    {
        ConfigurationTask configurationTask = ConfigurationTask.getInstance();
        importGrammars(configurationTask.getEnglishFile(), configurationTask.getPersianFile());
    }
    static private void importGrammars(String englishGrammar, String persianGrammar) {
        String engParam = "";
        try {
            engParam = URLEncoder.encode(englishGrammar, "UTF-8").replace("+", "%20");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpResponse response = HttpConnection.get(URL + "/gfshell?dir=" + temp + "&command=i+" + engParam);

        String perParam = "";
        try {
            perParam = URLEncoder.encode(persianGrammar, "UTF-8").replace("+", "%20");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpResponse response2 = HttpConnection.get(URL + "/gfshell?dir=" + temp + "&command=i+" + perParam);
    }

    static private String getTemp() {
        ConfigurationTask configurationTask = ConfigurationTask.getInstance();
        if (configurationTask.getTemp() != null && !configurationTask.getTemp().equals(""))
            return configurationTask.getTemp();

        HttpResponse response = HttpConnection.get(URL + "/new");
        String temp=response.getResponseString().replace("\r\n","");
        configurationTask.setTemp(temp);
        return temp;
    }

    private static String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

    static public String parse(String src) {
        String param = "";
        try {
            param = URLEncoder.encode("\"" + src + "\"", "UTF-8").replace("+", "%20");
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpResponse response = HttpConnection.get(URL + "/gfshell?dir=" + temp + "&command=p+" + param);
        return response.getResponseString();
    }

    static public String linearize(String src) {
        String param = "";
        try {
            param = URLEncoder.encode(src, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpResponse response = HttpConnection.get(URL + "/gfshell?dir=" + temp + "&command=l+" + param);
        return response.getResponseString();
    }

    static public String parseAndLinearize(String srcLang, String src, String destLang) {
        if(srcLang.contains("Pes"))
            try {
                src = URLEncoder.encode(src,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        String param = "";
        try {
            param = URLEncoder.encode("\"" + src + "\" | l -lang=" + destLang, "UTF-8").replace("+", "%20");
            ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpResponse response = HttpConnection.get(URL + "/gfshell?dir=" + temp + "&command=p+-lang=" + srcLang + "+" + param);
        return response.getResponseString();
    }
}
