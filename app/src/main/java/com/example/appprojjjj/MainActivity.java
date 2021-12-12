package com.example.appprojjjj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)  findViewById(R.id.button1);
        button.setOnClickListener(onClickListener);
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.button1:

                    TextView temp = findViewById(R.id.textView);
                    TextView ville = findViewById(R.id.ville);


                    Intent CallActivityInformation = new Intent(getApplicationContext(), Resultat.class);


                    CallActivityInformation.putExtra("ville", ville.getText().toString());
                    startActivity(CallActivityInformation);




                    break;
            }


        }
    };








}


