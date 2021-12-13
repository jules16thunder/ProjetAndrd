package com.example.appprojjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)  findViewById(R.id.button1);
        Button bouton = (Button) findViewById(R.id.button2);
        button.setOnClickListener(onClickListener);
        bouton.setOnClickListener(onClickListener);

    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.button1:

                    TextView ville = findViewById(R.id.ville);


                    Intent CallActivityInformation = new Intent(getApplicationContext(), Resultat.class);


                    CallActivityInformation.putExtra("ville", ville.getText().toString());
                    startActivity(CallActivityInformation);




                    break;

                case R.id.button2:

                    Intent CallPreferencesActivity = new Intent(getApplicationContext(), PreferencesActivity.class);
                    startActivity(CallPreferencesActivity);
                    break;
            }


        }
    };








}


