package com.scott.annie.timer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("state", "onCreate() ");

        Button b = (Button) (findViewById(R.id.button));
        Button b1 = (Button) (findViewById(R.id.button2));

        b.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View view) {
                                     Log.i("Button Click ", "onClick:CLICKED ");
                                     Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                                     startActivity(intent);
                                 }
                             });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("Button Click", "onClick: CLICKED");
                Intent intent2 = new Intent(getApplicationContext(), TimerListActivity.class);
                startActivity(intent2);
            }
        });
    }
}
