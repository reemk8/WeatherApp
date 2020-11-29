package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // we"ll make HTTP request to this URL to retrieve weather conditions
    //String weatherWebserviceURL = "http://api.openweathermap.org/data/2.5/weather?q=ariana,tn&appid=2156e2dd5b92590ab69c0ae1b2d24586&units=metric";
    ProgressDialog pDialog;
    // Textview to show temperature and description
    TextView temperature, description;
    ImageView weatherBackground;
    // JSON object that contains weather information
    JSONObject jsonObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "http://api.openweathermap.org/data/2.5/weather?q=riyadh&appid=dce0289d5a9d14fd7bfa8c717225969a &units=metric";


        temperature = (TextView) findViewById(R.id.temperature);
        description = (TextView) findViewById(R.id.description);
        weatherBackground = (ImageView) findViewById(R.id.weatherbackground);
        weather(url);


    }

    public void test(String url) {
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Reem", "Response received");
                        Log.d("Reem", response.toString ());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Reem", "Error retrieving URL");
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);

    }

    public void weather(String url) {
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Reem", "Response received");
                        Log.d("Reem-JSON", response.toString());
                        try {
                            String town = response.getString("name");
                            Log.d("Reem-town", town);
                            JSONObject jsonMain = response.getJSONObject("main");
                            double temp = jsonMain.getDouble("temp");
                            Log.d("Reem-temp", String.valueOf (temp));
                       temperature.setText(String.valueOf (temp));
                            description.setText(town);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Receive Error", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "Error retrieving URL");
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }

    public void chooseBackground(JSONArray jsonWeatherArray) {
        try {
            for (int i = 0; i < jsonWeatherArray.length(); i++) {
                JSONObject oneObject = jsonWeatherArray.getJSONObject(i);
                // Pulling items from the array
                String weather = oneObject.getString("main");
                Log.d("Reem-clouds", weather);
                if (weather.equals("Clear")) {
                    Glide.with(this)
                            .load("https://media-cdn.tripadvisor.com/media/photo-s/04/b5/0f/ff/alinn-sarigerme-boutique.jpg")
                            .into(weatherBackground);
                }

                if (weather.equals("Cloudy")) {
                    Glide.with(this)
                            .load("https://pics.freeartbackgrounds.com/fullhd/Cloudy_Sky_Background-1520.jpg")
                            .into(weatherBackground);
                }

                if (weather.equals("Rainy")) {
                    Glide.with(this)
                            .load("https://blackburnnews.com/wp-content/uploads/2016/02/canstockphoto14713476.jpg")
                            .into(weatherBackground);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONArray Error", e.toString());
        }
    }
}

