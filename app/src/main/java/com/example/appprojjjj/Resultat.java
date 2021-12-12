package com.example.appprojjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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




        AsyncWeatherJSONData task = new AsyncWeatherJSONData();
        task.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncWeatherJSONData extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... strings) {
            TextView affichage = findViewById(R.id.textView3);
            Bundle extras = getIntent().getExtras();
            String ville = new String(extras.getString("ville"));
            URL url = null;
            HttpURLConnection urlConnection = null;
            String result = null;
            InputStream in = null;

            try {
                url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+ville+"&appid=82406a41f668a93dcb6e31246defec67");
                urlConnection = (HttpURLConnection) url.openConnection(); // Open
                in = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (in==null){
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
                affichage.setText(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
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