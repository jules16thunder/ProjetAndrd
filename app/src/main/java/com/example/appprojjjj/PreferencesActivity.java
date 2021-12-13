package com.example.appprojjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Button buttonStandard = (Button) findViewById(R.id.buttonStandard);
        Button buttonMetric = (Button) findViewById(R.id.buttonMetric);
        Button buttonImperial = (Button) findViewById(R.id.buttonImperial);
        TextView test = findViewById(R.id.textTest);
        Context context = getApplicationContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
        String valueStr=prefs.getString(SHARED_PREF_USER_INFO, "Metric");
        test.setText(valueStr);
        buttonStandard.setOnClickListener(onClickListener);
        buttonMetric.setOnClickListener(onClickListener);
        buttonImperial.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.buttonStandard:

                    TextView test = findViewById(R.id.textTest);

                    Context context = getApplicationContext();
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor=prefs.edit();
                    String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
                    editor.putString(SHARED_PREF_USER_INFO, "standard");
                    editor.commit();
                    String valueStr=prefs.getString(SHARED_PREF_USER_INFO, "Metric");
                    test.setText(valueStr);
                    break;

                case R.id.buttonMetric:

                    test = findViewById(R.id.textTest);

                    context = getApplicationContext();
                    prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    editor = prefs.edit();
                    SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
                    editor.putString(SHARED_PREF_USER_INFO, "Metric");
                    editor.commit();
                    valueStr = prefs.getString(SHARED_PREF_USER_INFO, "Metric");
                    test.setText(valueStr);

                    break;

                case R.id.buttonImperial:

                    test = findViewById(R.id.textTest);

                    context = getApplicationContext();
                    prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    editor = prefs.edit();
                    SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
                    editor.putString(SHARED_PREF_USER_INFO, "Imperial");
                    editor.commit();
                    valueStr = prefs.getString(SHARED_PREF_USER_INFO, "Metric");
                    test.setText(valueStr);

                    break;
            }
        }

    };

}