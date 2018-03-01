package com.scott.annie.timer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TimerListActivity extends AppCompatActivity {

    ListView timerlist;
    ArrayList<String> times;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_list);

        timerlist = (ListView) findViewById(R.id.listview);
        gettimerlist();
    }

    void gettimerlist() {
        times = new ArrayList<String>();
        try {
            SQLiteDatabase db = this.openOrCreateDatabase("timedb", MODE_PRIVATE, null);
            Cursor c = db.rawQuery("Select * from timer", null);
            c.moveToFirst();

            int index = c.getColumnIndex("time");
            int dateindex = c.getColumnIndex("date");

            while (c != null) {
                times.add("\n\nTimer set - " + c.getString(index) + "\n\n Created at - " + c.getString(dateindex) + "\n\n");
                c.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, times);
            timerlist.setAdapter(adapter);

        }
    }
}
