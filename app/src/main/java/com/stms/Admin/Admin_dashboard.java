/*
Initial Declaration :-
        file name :- AdminDashboard.java.
        Purpose :-  To display the Requested,Approved,Rejected ideas.
        Methods :-  getData(),updatest(),setupViewPager(),logoutAlert().
        Developer Name :-
        Release Number:- 1.2.3

        Future Declaration :-
        Ongoing Changes
        Developer Name :-“Name Of the Developer” and Last Updated on “Date”
        Release Number:- “Enter Ur Release Number”
        change description :- “Reason for Ur change”
        change date:- “Enter the date of file change”
        Change Number :- “increment the count whenever you change the file content”
*/
package com.stms.Admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.stms.Adapters.ViewPagerAdapter;
import com.stms.CommonScreens.FieldActivity;
import com.stms.CommonScreens.FieldActivityUpload;
import com.stms.CommonScreens.Profile;
import com.stms.CommonScreens.UpdateOffers;
import com.stms.Employess.Employee_dashboard;
import com.stms.Launch.Login;
import com.stms.R;
import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;

public class Admin_dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    ViewPager viewPager;
    TabLayout tabLayout;
    ConnectionDetector cd;
    DrawerLayout drawer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView( R.layout.activity_admin_dashboard );

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("AdminDashboard");


         drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
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


        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Config.showLoader(Admin_dashboard.this);



        tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = findViewById(R.id.pager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        cd = new ConnectionDetector(Admin_dashboard.this);

    }




    /* this method is used to update the spinner selected item to status for particular user  */



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

        if (id == R.id.profile)
        {
            Intent i=new Intent(getApplicationContext(), Profile.class);
            startActivity(i);


        }
        else if (id == R.id.manageCustomers)

        {
            Intent i=new Intent(getApplicationContext(),View_customers.class);
            startActivity(i);



        }
        else if (id == R.id.manageEmployees)
        {
            Intent i=new Intent(getApplicationContext(),ViewEmployees.class);
            startActivity(i);
        }

        else if (id == R.id.manageVehicles)
        {
//
            Intent i=new Intent(getApplicationContext(),View_Vehicals.class);
            startActivity(i);
        }

        else if (id == R.id.managetask)
        {
//
            Intent i=new Intent(getApplicationContext(), Admin_manageTask.class);
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
        else if(id==R.id.customerNotifications){

        }
        else if(id==R.id.quotations){
            Intent i=new Intent(getApplicationContext(), Admin_quotationsDashboard.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /*this method is used to partition the page */

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AdminRequestedIdeasFragment(), "REQUESTED");
        adapter.addFragment(new AdminApprovedIdeasFragment(), "APPROVED");


        viewPager.setAdapter(adapter);
        Config.closeLoader();
    }
    /* this method is used to give alert for logout*/

    public void logoutAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Admin_dashboard.this);

        // Setting Dialog Title
        alertDialog.setTitle("Alert!");

        // Setting Dialog Message
        alertDialog
                .setMessage("Do you really want to logout?");

        // On pressing Settings button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Config.saveLoginStatus(Admin_dashboard.this, "No");

                        Intent i = new Intent(Admin_dashboard.this, Login.class);
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
    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent( Intent.ACTION_MAIN );
        intent.addCategory( Intent.CATEGORY_HOME );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity( intent );
    }


}
