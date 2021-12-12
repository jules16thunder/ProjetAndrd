package com.example.appprojjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Resultat extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        //Bundle extras = getIntent().getExtras();
        //String ville = new String(extras.getString("ville"));


        String url = new String("http://api.openweathermap.org/data/2.5/weather?q=Paris&appid=82406a41f668a93dcb6e31246defec67");
        AsyncWeatherJSONData task = new AsyncWeatherJSONData();
        task.execute(url);
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncWeatherJSONData extends AsyncTask<String, Void, JSONObject> {
        @Override

        protected JSONObject doInBackground(String... strings) {
            TextView affichage = findViewById(R.id.textView3);
            URL url = null;
            HttpURLConnection urlConnection = null;
            String result = null;
            try {
                url = new URL("https://api.openweathermap.org/data/2.5/weather?q=Paris&appid=82406a41f668a93dcb6e31246defec67");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                urlConnection = (HttpURLConnection) url.openConnection(); // Open
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream in = null;
            try {
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            result = readStream(in); // Read stream
            urlConnection.disconnect();
            JSONObject json = null;
            try {
                json = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            affichage.setText("ca avance");
            return json; // returns the result
        }

        private String readStream(InputStream is) {
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = is.read();
                while(i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "";
            }
        }





    }



}