package com.example.appprojjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class Resultat extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);


        AsyncWeatherJSONData task = new AsyncWeatherJSONData();
        AsyncBitmapDownloader task2 = new AsyncBitmapDownloader();
        String icon = null;
        try {
            JSONObject lejson = task.execute().get();
            if (lejson!=null) {
                JSONArray weather = lejson.getJSONArray("weather");
                JSONObject weather0 = (JSONObject) weather.get(0);
                icon = weather0.getString("icon");
            }
            else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Context context = getApplicationContext();
                        CharSequence text = "Cette ville n'existe pas";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        task2.execute(icon);
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncWeatherJSONData extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {

            Context context = getApplicationContext();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
            String mesure=prefs.getString(SHARED_PREF_USER_INFO, "Metric");

            TextView temperatureview = findViewById(R.id.temperature);
            TextView cityview = findViewById(R.id.city);
            ImageView image = findViewById(R.id.image);
            Bundle extras = getIntent().getExtras();
            String ville = new String(extras.getString("ville"));
            URL url = null;
            HttpURLConnection urlConnection = null;
            String result = null;
            InputStream in = null;

            try {
                url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + ville + "&appid=82406a41f668a93dcb6e31246defec67&units="+mesure);
                urlConnection = (HttpURLConnection) url.openConnection(); // Open
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (in == null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Context context = getApplicationContext();
                        CharSequence text = "Cette ville n'existe pas";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });



                return null;
            }
            result = readStream(in); // Read stream
            urlConnection.disconnect();
            JSONObject json = null;
            try {
                json = new JSONObject(result);
                JSONObject main = json.getJSONObject("main");
                double temp = main.getDouble("temp");
                String tempe = String.valueOf(temp);
                String cityname = json.getString("name");
                JSONArray weather = json.getJSONArray("weather");
                JSONObject weather0 = (JSONObject) weather.get(0);
                String icon = weather0.getString("icon");


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        temperatureview.setText(tempe);
                        cityview.setText(cityname);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json; // returns the result
        }

        private String readStream(InputStream is) {
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = is.read();
                while (i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "";
            }
        }


    }

    private class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {

            String icon = strings[0];
            ImageView image = (ImageView) findViewById(R.id.image);
            TextView test  = findViewById(R.id.textView5);
            URL url = null;
            HttpURLConnection urlConnection = null;
            Bitmap bm = null;
            try {
                url = new URL("https://openweathermap.org/img/w/"+icon+".png");
                urlConnection = (HttpURLConnection) url.openConnection(); // Open
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStream in = null; // Stream
            try {
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (in == null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Context context = getApplicationContext();
                        CharSequence text = "Cette ville n'existe pas";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });

                return null;
            }
            bm = BitmapFactory.decodeStream(in);
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            urlConnection.disconnect();

            Bitmap finalBm = bm;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    image.setImageBitmap(finalBm);
                    test.setText(icon);
                }
            });

            return bm;
        }


    }


}