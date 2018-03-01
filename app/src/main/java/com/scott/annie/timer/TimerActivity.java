package com.scott.annie.timer;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimerActivity extends AppCompatActivity {

    SeekBar seekBar;
    int progressChangedValue = 0;
    private static final long START_TIME_IN_MILLS = 60000;
    private TextView mTextViewCountDown;
    private Button mStartPause;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long text = START_TIME_IN_MILLS;
    private long mTimeLeftInMillis = START_TIME_IN_MILLS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        mTextViewCountDown = findViewById(R.id.time_text_view);
        mStartPause = findViewById(R.id.startbutton);
        seekBar = findViewById(R.id.timer_seekbar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean user) {
                progressChangedValue = i;
                Log.i("PROGRESS", String.valueOf(i));
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(progressChangedValue * 1000), TimeUnit.MILLISECONDS.toMinutes(progressChangedValue * 1000) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(progressChangedValue * 1000)), TimeUnit.MILLISECONDS.toSeconds(progressChangedValue * 1000) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(progressChangedValue * 1000)));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(progressChangedValue * 1000), TimeUnit.MILLISECONDS.toMinutes(progressChangedValue * 1000) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(progressChangedValue * 1000)), TimeUnit.MILLISECONDS.toSeconds(progressChangedValue * 1000) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(progressChangedValue * 1000)));

            }
        });

        mStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mStartPause.setText("start");
        mStartPause.setVisibility(View.INVISIBLE);

        previoustimelist(mTimeLeftInMillis + "",new Date()+"");
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onFinish() {
                seekBar.setProgress(0);
                mTimerRunning = false;
                mStartPause.setText("Start");

            }

            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                seekBar.setProgress((int) (millisUntilFinished / 1000));
            }

        }.start();

        mTimerRunning = true;
        mStartPause.setText("Stop");


    }

    public void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String Timeleftformatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(Timeleftformatted);
    }

    void previoustimelist(String time, String date) {
        try {
            SQLiteDatabase db = this.openOrCreateDatabase("timedb", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS timer(date VARCHAR , time VARCHAR)");
            Log.i("date:", date);
            db.execSQL("INSERT INTO timer(date , time) VALUES('" + date + "','" + time + "')");
            Toast.makeText(this, "TIMER STARTED AND SAVED IN PREVIOUS TIMERS ", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

