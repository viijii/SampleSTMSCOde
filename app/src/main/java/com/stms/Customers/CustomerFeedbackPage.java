package com.stms.Customers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stms.Adapters.CustomerCompletedAdapter;
import com.stms.Adapters.CustomerCompletedFeedbackAdapter;
import com.stms.Adapters.ViewPagerAdapter;
import com.stms.CommonScreens.FieldActivityUpload;
import com.stms.CommonScreens.Profile;
import com.stms.CommonScreens.UpdateOffers;
import com.stms.Launch.Login;
import com.stms.R;
import com.stms.Responses.AdminRequestedResponse;
import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerFeedbackPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    ConnectionDetector cd;
    TextView userName,userType;
    DrawerLayout drawer;

    RecyclerView listview;
    AdminRequestedResponse adminRequestedResponse;
    ArrayList<AdminRequestedResponse.data> data = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    CustomerCompletedFeedbackAdapter customerCompletedAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_feedback_page);


        listview = findViewById(R.id.listview1);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Feedback");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(layoutManager);
        listview.setHasFixedSize(true);
        cd = new ConnectionDetector(getApplicationContext());


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
        Config.showLoader(CustomerFeedbackPage.this);
        View heder=navigationView.getHeaderView( 0 );

        userName=heder.findViewById(R.id.username2);
        userType=heder.findViewById(R.id.user_type2);
        userName.setText(Config.getUser_name(getApplicationContext()));
        userType.setText(Config.getLoginUserType(getApplicationContext()));
        cd = new ConnectionDetector(CustomerFeedbackPage.this);

        getCustomerIdeas();


    }



    public void setListView() {
        customerCompletedAdapter = new CustomerCompletedFeedbackAdapter(CustomerFeedbackPage.this, data, CustomerFeedbackPage.this);
        listview.setAdapter(customerCompletedAdapter);
    }


    public void getCustomerIdeas() {
        try {
            if (cd.isConnectingToInternet()) {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<AdminRequestedResponse> call = api.getCustomercompletedIdeas("completed",
                        Config.getLoginUserType(getApplicationContext()).toLowerCase(),
                        Config.getUserId(getApplicationContext()),
                        Config.getCorp_code(getApplicationContext()));

                call.enqueue(new Callback<AdminRequestedResponse>() {
                    @Override
                    public void onResponse(Call<AdminRequestedResponse> call,
                                           Response<AdminRequestedResponse> response) {
                        Config.closeLoader();
                        adminRequestedResponse = response.body();
                        data = new ArrayList<AdminRequestedResponse.data>();

                        if (adminRequestedResponse.getStatus().equalsIgnoreCase("true")) {

                            if(adminRequestedResponse.getData()==null){
                                Toast.makeText(getApplicationContext(),"No Data Getting",Toast.LENGTH_LONG).show();
                            }else {

                                for (int i = 0; i < adminRequestedResponse.getData().length; i++) {
                                    data.add(adminRequestedResponse.getData()[i]);
                                    Log.d("TAG", "onResponse:completed " + adminRequestedResponse.getData()[i]);
                                    Log.d("TAG", "onResponse:completed1" + adminRequestedResponse.getData()[i].getComplaintId());
                                    Log.d("TAG", "onResponse:completed2" + adminRequestedResponse.getData()[i].getTitle());
                                    Log.d("TAG", "onResponse:completed3 " + adminRequestedResponse.getData()[i].getVehicleType());

                                }
                            }
                        }

                        setListView();
                    }

                    @Override
                    public void onFailure(Call<AdminRequestedResponse> call, Throwable t) {
                        Config.closeLoader();
                        t.getMessage();
                        Toast.makeText(
                                getApplicationContext(), "Try Again!", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        "Sorry not connected to internet",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            System.out.println("Exception e" + e);
        }
    }


    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        else if (id == R.id.feedback)
        {

        }
        else if (id == R.id.servicecall)
        {

            Intent i=new Intent(getApplicationContext(), ServiceCall.class);
            startActivity(i);
            finish();

        }

        else if (id == R.id.complaints)
        {
            Intent i=new Intent(getApplicationContext(), Customer_dashboard.class);
            startActivity(i);
            finish();
        }

        else if (id == R.id.logout)
        {
            logoutAlert();
        }
        else if(id==R.id.fieldActivity){

            Intent i=new Intent(getApplicationContext(), FieldActivityUpload.class);
            startActivity(i);
            finish();


        }
        else if(id==R.id.updates){
            Intent i=new Intent(getApplicationContext(), UpdateOffers.class);
            startActivity(i);
            finish();

        }
        else if(id ==R.id.home ){
            Intent i=new Intent(getApplicationContext(), CustomerHomePage.class);
            startActivity(i);
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* this method is used to give alert for logout*/

    public void logoutAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustomerFeedbackPage.this);

        // Setting Dialog Title
        alertDialog.setTitle("Alert!");

        // Setting Dialog Message
        alertDialog
                .setMessage("Do you really want to logout?");

        // On pressing Settings button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Config.saveLoginStatus(CustomerFeedbackPage.this, "No");

                        Intent i = new Intent(CustomerFeedbackPage.this, Login.class);
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

