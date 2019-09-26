package com.stms.Customers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.stms.CommonScreens.FeedBackActivity;
import com.stms.CommonScreens.FieldActivity;
import com.stms.CommonScreens.FieldActivityUpload;
import com.stms.CommonScreens.Profile;
import com.stms.CommonScreens.UpdateOffers;
import com.stms.Launch.Login;
import com.stms.R;
import com.stms.utils.Config;

public class CustomerHomePage extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    Button servicecall,fieldActivity,complaint,updates;
    DrawerLayout drawer;
    TextView userName,userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        drawer= findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close){
            //closing keyboard if it is open
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View heder=navigationView.getHeaderView( 0 );

        userName=heder.findViewById(R.id.username2);
        userType=heder.findViewById(R.id.user_type2);
        userName.setText(Config.getUser_name(getApplicationContext()));
        userType.setText(Config.getLoginUserType(getApplicationContext()));

        servicecall=findViewById(R.id.servicecall);
        fieldActivity=findViewById(R.id.fieldactivity);
        complaint=findViewById(R.id.complaint);
        updates=findViewById(R.id.updates);


        servicecall.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(getApplicationContext(),ServiceCall.class);
        startActivity(i);
            }
});
        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Customer_dashboard.class);
                startActivity(i);
            }
        });
        updates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),UpdateOffers.class);
                startActivity(i);
            }
        });
        fieldActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), FieldActivityUpload.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.admin_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.home)
        {

        }

       else if (id == R.id.profile)
        {

            Intent i=new Intent(getApplicationContext(), Profile.class);
            startActivity(i);

        }
        else if (id == R.id.feedback)
        {
            Intent i=new Intent(getApplicationContext(), CustomerFeedbackPage.class);
            startActivity(i);

        }
        else if (id == R.id.servicecall)
        {
            Intent i=new Intent(getApplicationContext(), ServiceCall.class);
            startActivity(i);

        }

        else if (id == R.id.complaints)
        {
            Intent i=new Intent(getApplicationContext(), Customer_dashboard.class);
            startActivity(i);

        }

        else if (id == R.id.logout)
        {
            logoutAlert();
        }
        else if(id==R.id.fieldActivity){
            Intent i=new Intent(getApplicationContext(), FieldActivityUpload.class);
            startActivity(i);

        }
        else if(id==R.id.updates){
            Intent i=new Intent(getApplicationContext(), UpdateOffers.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logoutAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustomerHomePage.this);

        // Setting Dialog Title
        alertDialog.setTitle("Alert!");

        // Setting Dialog Message
        alertDialog
                .setMessage("Do you really want to logout?");

        // On pressing Settings button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Config.saveLoginStatus(CustomerHomePage.this, "No");

                        Intent i = new Intent(CustomerHomePage.this, Login.class);
                        startActivity(i);
                        finish();
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }


}
