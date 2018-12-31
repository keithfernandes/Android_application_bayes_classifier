package com.iwander.iwander;


import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ApiConnector {
    String result = "";
    HttpClient httpClient;
    HttpPost httpPost;
    String url = "";
    HttpResponse httpResponse;
    String responseText;

    public String registerUser(String username, String password, String userType, String age, String demetiaLevel, String phone)
    {
        try {
            url = Utility.BASE_URL+"register.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("username" , username));
            nameValuePairs.add(new BasicNameValuePair("password" , password));
            nameValuePairs.add(new BasicNameValuePair("userType" , userType));
            nameValuePairs.add(new BasicNameValuePair("age" , age));
            nameValuePairs.add(new BasicNameValuePair("dementiaLevel" , demetiaLevel));
            nameValuePairs.add(new BasicNameValuePair("phone" ,phone));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = httpClient.execute(httpPost);
            responseText = EntityUtils.toString(httpResponse.getEntity());

            Log.d("ECurrency", responseText);
            //responseText = responseText.substring(0,20);
            JSONObject jobject = new JSONObject(responseText);
            responseText = jobject.getString("message");
            return responseText;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return e.getMessage();
        }


    }

    public String checkLogin(String username, String password, String userType)
    {
        try {
            url = Utility.BASE_URL+"login.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("username" , username));
            nameValuePairs.add(new BasicNameValuePair("password" , password));
            nameValuePairs.add(new BasicNameValuePair("userType" , userType));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = httpClient.execute(httpPost);
            responseText = EntityUtils.toString(httpResponse.getEntity());
            Log.e("ECurrency", responseText);
            //responseText = responseText.substring(0,20);
            JSONObject jobject = new JSONObject(responseText);
            responseText = jobject.getString("message");
            return responseText;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    public String uploadData(String longitude, String latitude, String username, String time, boolean hasUserCrossed)
    {
        try {
            String urlData = "http://maps.google.com/?q="+latitude+","+longitude;
            url = Utility.BASE_URL+"setLocation.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("longitude" , longitude));
            nameValuePairs.add(new BasicNameValuePair("latitude" , latitude));
            nameValuePairs.add(new BasicNameValuePair("username" , username));
            nameValuePairs.add(new BasicNameValuePair("time" , time));
            nameValuePairs.add(new BasicNameValuePair("hasUserCrossed", String.valueOf(hasUserCrossed)));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = httpClient.execute(httpPost);
            responseText = EntityUtils.toString(httpResponse.getEntity());
             //responseText = responseText.substring(0,20);
            JSONObject jobject = new JSONObject(responseText);
            responseText = jobject.getString("message");
            return responseText;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public JSONArray fetchLocationDetails(String username)
    {
        JSONArray jsonArray = new JSONArray();
        try {
            url = Utility.BASE_URL+"fetchLocationDetails.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("username" , username));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = httpClient.execute(httpPost);
            responseText = EntityUtils.toString(httpResponse.getEntity());
            Log.e("ECurrency", responseText);
            //responseText = responseText.substring(0,20);
            jsonArray = new JSONArray(responseText);

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

        return jsonArray;


    }



}