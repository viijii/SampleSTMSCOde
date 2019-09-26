/*
Initial Declaration :-
        file name :- MainActivity.java
        Purpose :- this is the first page which appear after login page, this class is used to initazi the menu item
        Methods :- overridden methods are used.
        Developer Name :- praveen
        Release Number:- 1.2.3



        Future Declaration :-
        Ongoing Changes
        Developer Name :-“Name Of the Developer” and Last Updated on “Date”
        Release Number:- “Enter Ur Release Number”
        change description :- “Reason for Ur change”
        change date:- “Enter the date of file change”
        Change Number :- “increment the count whenever you change the file content”


*/





package com.stms.Launch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.stms.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            {
                return true;
            }
        return super.onOptionsItemSelected(item);
    }
}
