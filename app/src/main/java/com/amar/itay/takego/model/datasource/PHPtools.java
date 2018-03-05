package com.amar.itay.takego.model.datasource;

import android.content.ContentValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by itay0 on 09/12/2017.
 */

public class PHPtools {

    /**
     * this function help us to getting interaction to the sql by the php page.
     * GET function sends her external information on the url she sends.
     * GET function usually used to transfer a little amount of data (for example: getting the cars list -> can send empty data).
     * @param url to get interaction with the php in our server.
     * @return the output of the php page.
     * @throws Exception in case the connection lost/can't find the url address/connection timed out etc.
     */
    public static String GET(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return response.toString();
        } else {
            return "";
        }
    }

    /**
     * this function help us to getting interaction to the sql by the php page.
     * POST function sends her information in her package she sends.
     * POST function usually used to transfer a big amount of data (for example: add new car -> sends some parameters).
     * @param url to get interaction with the php in our server.
     * @param params to be send to the php page.
     * @return the output of the php page(usually returns one parameter).
     * @throws IOException in case the connection lost/can't find the url address/connection timed out etc.
     */
    public static String POST(String url, ContentValues params) throws IOException {

        //Convert Map<String,Object> into key=value&key=value pairs.
        StringBuilder postData = new StringBuilder();
        for (String param : params.keySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param, "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(params.get(param)), "UTF-8"));
        }

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(postData.toString().getBytes("UTF-8"));
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        else return "";
    }


    /**
     * we get the output from the server by a php page in json format and to work with the information
     * from the output we need to convert it to contentValues.
     * convert json to contentValues.
     * @param jsonObject the Jason to be convert.
     * @return contentVlaues of the data from jason we got.
     * @throws JSONException if a problem occur.
     */
    public static ContentValues JsonToContentValues(JSONObject jsonObject) throws JSONException {
        ContentValues result = new ContentValues();
        Iterator<?> keys = jsonObject.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            Object value = jsonObject.get(key);
            result.put(key, value.toString());
        }
        return result;
    }




}
