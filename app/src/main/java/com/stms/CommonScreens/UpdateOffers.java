package com.stms.CommonScreens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stms.Admin.AddEditEmployee;
import com.stms.Admin.Admin_dashboard;
import com.stms.Admin.Admin_manageTask;
import com.stms.Admin.ViewEmployees;
import com.stms.Admin.View_Vehicals;
import com.stms.Admin.View_customers;
import com.stms.Customers.CustomerFeedbackPage;
import com.stms.Customers.CustomerHomePage;
import com.stms.Customers.Customer_dashboard;
import com.stms.Customers.ServiceCall;
import com.stms.Employess.Employee_dashboard;
import com.stms.Launch.Login;
import com.stms.R;
import com.stms.Responses.CommonResponse;
import com.stms.Responses.UpdateResponse;
import com.stms.Responses.addEditEmployeeResponse;
import com.stms.utils.Config;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class UpdateOffers extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {


    TextView offersId,add,userName,userType;
    String str;
    ArrayList<UpdateResponse.data> data = new ArrayList<>();
    UpdateResponse updateResponse;
    DrawerLayout drawer;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("admin")) {
            setContentView(R.layout.activity_admin_common_updates);
        }else if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("customer"))
        {
            setContentView(R.layout.activity_common_updates);
        }else{
            setContentView(R.layout.activity_emp_common_updates);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Update Offers");
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
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
        swipeRefreshLayout= findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUpdates();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        View heder=navigationView.getHeaderView( 0 );
try {
    if (Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {

    } else if (Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Emp")) {
        userName = heder.findViewById(R.id.username1);
        userType = heder.findViewById(R.id.user_type1);
        userName.setText(Config.getUser_name(getApplicationContext()));
        userType.setText(Config.getLoginUserType(getApplicationContext()));
    } else {
        userName = heder.findViewById(R.id.username2);
        userType = heder.findViewById(R.id.user_type2);
        userName.setText(Config.getUser_name(getApplicationContext()));
        userType.setText(Config.getLoginUserType(getApplicationContext()));


    }
}catch (Exception e){

}


        getUpdates();
        offersId = findViewById(R.id.offersId);
        add = findViewById(R.id.add);

        add.setVisibility(View.GONE);
        if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")){
            add.setVisibility(View.VISIBLE);
        }else{
            add.setVisibility(View.GONE);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateOffers.this, CommonUpdates.class);
                startActivity(intent);

            }
        });

    }


    public void getUpdates() {

        try {
            Log.d("TAG", "onResponse:add1 ");
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            Call<UpdateResponse> call = api.getAll("getting", Config.getRole(getApplicationContext()), Config.getCorp_code(getApplicationContext()));
            call.enqueue(new Callback<UpdateResponse>() {
                @Override
                public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                    updateResponse = response.body();

                    if (updateResponse.getStatus().equalsIgnoreCase("true")) {

                        if (updateResponse.getData() == null) {

                        } else {
                            for (int i = 0; i < updateResponse.getData().length; i++) {
                                Log.d(TAG, "onResponse: responsessss" + updateResponse.getData()[i]);
                                str = updateResponse.getData()[i].getUpdateDescription();
                                data.add(updateResponse.getData()[i]);
                                offersId.setText(str);
                            }
                        }
                    } else {
                        Toast.makeText(UpdateOffers.this, "Update Again", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<UpdateResponse> call, Throwable t) {

                    //  Config.closeLoader();
                    t.getMessage();

                }
            });
        } catch (Exception e) {
            System.out.println("Exception e" + e);
        }
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id== R.id.home){

            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("customer")){
                Intent i=new Intent(getApplicationContext(), CustomerHomePage.class);
                startActivity(i);
                finish();
            }else{

            }
        }

        else if(id== R.id.mytask){

            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Emp")) {
                Intent i = new Intent(getApplicationContext(), Employee_dashboard.class);
                startActivity(i);
                finish();
            }
            else{

            }
        }
        else if(id== R.id.servicecall){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Customer")) {
                Intent i = new Intent(getApplicationContext(), ServiceCall.class);
                startActivity(i);
                finish();
            }else{}
        }
        else if(id== R.id.complaints){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Customer")) {
                Intent i = new Intent(getApplicationContext(), Customer_dashboard.class);
                startActivity(i);
                finish();
            }else{

            }
        }
        else if(id== R.id.fieldActivity){

            Intent i = new Intent(getApplicationContext(), FieldActivityUpload.class);
            startActivity(i);
            finish();
        }
        else if(id== R.id.feedback){

            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("customer")) {
                Intent i = new Intent(getApplicationContext(), CustomerFeedbackPage.class);
                startActivity(i);
                finish();
            }else{

            }



        }
        else if(id== R.id.managetask){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {
                Intent i = new Intent(getApplicationContext(), Admin_manageTask.class);
                startActivity(i);
                finish();
            }else{

            }
        }
        else if(id== R.id.quotations){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {

            }else{

            }
        }

        else if(id== R.id.customerNotifications){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {
                Intent i = new Intent(getApplicationContext(), Admin_dashboard.class);
                startActivity(i);
                finish();
            }else{}
        }
        else if(id== R.id.profile){
            Intent i=new Intent( getApplicationContext(), Profile.class );
            startActivity( i );
            finish();

        }
        else if(id== R.id.manageEmployees){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {
                Intent i = new Intent(getApplicationContext(), ViewEmployees.class);
                startActivity(i);
                finish();
            }else{}
        }
        else if(id== R.id.manageVehicles){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {
                Intent i = new Intent(getApplicationContext(), View_Vehicals.class);
                startActivity(i);
                finish();
            }else{}
        }
        else if(id== R.id.manageCustomers){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {
                Intent i = new Intent(getApplicationContext(), View_customers.class);
                startActivity(i);
                finish();
            }else{}
        }

        else if(id== R.id.updates){

        }
        else if(id== R.id.logout){
            logoutAlert();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logoutAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UpdateOffers.this);

        // Setting Dialog Title
        alertDialog.setTitle("Alert!");

        // Setting Dialog Message
        alertDialog
                .setMessage("Do you really want to logout?");

        // On pressing Settings button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Config.saveLoginStatus(UpdateOffers.this, "No");

                        Intent i = new Intent(UpdateOffers.this, Login.class);
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