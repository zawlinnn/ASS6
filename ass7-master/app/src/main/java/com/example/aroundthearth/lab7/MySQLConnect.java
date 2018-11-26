package com.example.aroundthearth.lab7;

import android.app.Activity;
import android.os.Build;
import android.os.StrictMode;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnect {
    private final Activity main;
    private List<String> list;
    private String URL="http://192.168.0.101/" , GET_URL = "get_post.php" , SENT_URL = "sent_post.php";

    public MySQLConnect(){ main = null; }

    public MySQLConnect(Activity mainA){
        main = mainA;
        list = new ArrayList<String>();
    }
    public List<String> getData() {
        String url = URL + GET_URL;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
                Toast.makeText(main, list.get(0), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(main, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(main.getApplicationContext());
        requestQueue.add(stringRequest);

        return list;
    }

    public void showJSON(String response){
        String std_id = "";
        String std_name = "";
        String std_tel = "";
        String std_email = "";
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i=0; i < result.length(); i++){
                JSONObject collectData = result.getJSONObject(i);
                std_id = collectData.getString("std_id");
                std_name = collectData.getString("std_name");
                std_tel = collectData.getString("std_tel");
                std_email = collectData.getString("std_email");
                list.add(std_id);
                list.add(std_name);
                list.add(std_tel);
                list.add(std_email);
            }

        }catch (JSONException ex){ex.printStackTrace();}

    }


    public void sentData(String std_id, String std_name, String std_tel,String std_email){
        StrictMode.enableDefaults();
        if (Build.VERSION.SDK_INT > 9){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("std_id",std_id));
            nameValuePairs.add(new BasicNameValuePair("std_name",std_name));
            nameValuePairs.add(new BasicNameValuePair("std_tel",std_tel));
            nameValuePairs.add(new BasicNameValuePair("std_email",std_email));
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL+SENT_URL);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            httpClient.execute(httpPost);

            Toast.makeText(main, "Completed.", Toast.LENGTH_LONG).show();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
