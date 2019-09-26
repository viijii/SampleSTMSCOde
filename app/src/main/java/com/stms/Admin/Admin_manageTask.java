package com.stms.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.stms.Adapters.ViewPagerAdapter;
import com.stms.CommonScreens.FieldActivityUpload;
import com.stms.CommonScreens.Profile;
import com.stms.CommonScreens.UpdateOffers;
import com.stms.Launch.Login;
import com.stms.R;
import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;

public class Admin_manageTask extends AppCompatActivity  implements
        NavigationView.OnNavigationItemSelectedListener {

    ViewPager viewPager;
    TabLayout tabLayout;
    ConnectionDetector cd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MANAGE TASK");
        //   setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Config.showLoader(Admin_manageTask.this);







        tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = findViewById(R.id.pager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        cd = new ConnectionDetector(Admin_manageTask.this);

    }

    @Override
    public void onBackPressed()
    {

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
            finish();
        }
        else if (id == R.id.manageCustomers)

        {
            Intent i=new Intent(getApplicationContext(),View_customers.class);
            startActivity(i);
            finish();



        }
        else if (id == R.id.manageEmployees)
        {
            Intent i=new Intent(getApplicationContext(),ViewEmployees.class);
            startActivity(i);
            finish();
        }

        else if (id == R.id.manageVehicles)
        {
//
            Intent i=new Intent(getApplicationContext(),View_Vehicals.class);
            startActivity(i);
            finish();
        }
        else if(id==R.id.quotations){
            Intent i=new Intent(getApplicationContext(), Admin_quotationsDashboard.class);
            startActivity(i);
            finish();
        }

        else if (id == R.id.logout)
        {
            logoutAlert();
        }
        else if(id==R.id.fieldActivity){

            Intent i = new Intent(getApplicationContext(), FieldActivityUpload.class);
            startActivity(i);
            finish();
        }
        else if(id==R.id.updates){
            Intent i=new Intent(getApplicationContext(), UpdateOffers.class);
            startActivity(i);
            finish();
        }
        else if(id==R.id.customerNotifications){
            Intent i=new Intent(getApplicationContext(),Admin_dashboard.class);
            startActivity(i);
            finish();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /*this method is used to partition the page */

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ManageTaskPendingFragment(), "PENDING");
        adapter.addFragment(new ManageTaskCompletedFragment(), "COMPLETED");
        adapter.addFragment(new ManageTaskVerifiedFragment(),"VERIFIED");

        viewPager.setAdapter(adapter);
        Config.closeLoader();
        /* this method is used to give alert for logout*/
    }

    public void logoutAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Admin_manageTask.this);

        // Setting Dialog Title
        alertDialog.setTitle("Alert!");

        // Setting Dialog Message
        alertDialog
                .setMessage("Do you really want to logout?");

        // On pressing Settings button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Config.saveLoginStatus(Admin_manageTask.this, "No");

                        Intent i = new Intent(Admin_manageTask.this, Login.class);
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





