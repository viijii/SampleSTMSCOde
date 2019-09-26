package com.stms.CommonScreens;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stms.Admin.Admin_dashboard;
import com.stms.Admin.Admin_manageTask;
import com.stms.Admin.ViewEmployees;
import com.stms.Admin.View_Vehicals;
import com.stms.Admin.View_customers;
import com.stms.Customers.CustomerCompletedIdeasFragment;
import com.stms.Customers.CustomerFeedbackPage;
import com.stms.Customers.CustomerHomePage;
import com.stms.Customers.Customer_dashboard;
import com.stms.Customers.ServiceCall;
import com.stms.Employess.Employee_dashboard;
import com.stms.Launch.Login;
import com.stms.R;
import com.stms.Responses.Get_Profile_response;
import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView fullname,empId,username,email,address,currentAddress,role,userName,userType;
    EditText mobile;
    Button submit;
    String mobilenum;
    ConnectionDetector cd;

    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;


    Get_Profile_response profileResponse;
    List<Get_Profile_response.data> profiledata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("admin")) {
            setContentView(R.layout.activity_profile);
        }else if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("customer"))
        {
            setContentView(R.layout.activity_customer_profile);
        }else{
            setContentView(R.layout.activity_emp_profile);
        }


        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        toolbar.setTitle("Profile");
        navigationView = findViewById(R.id.nav_view);
        fullname=findViewById(R.id.fullname);
        empId=findViewById(R.id.emp_id);
        username=findViewById(R.id.userName);
        email=findViewById(R.id.email);
        address=findViewById(R.id.empAddress);
        currentAddress=findViewById(R.id.currentAddress);
        role=findViewById(R.id.role);
        mobile=findViewById(R.id.mobile);
        submit=findViewById(R.id.submit);

       // setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();





        navigationView.setNavigationItemSelectedListener( this );

        cd=new ConnectionDetector(getApplication());

        View heder=navigationView.getHeaderView( 0 );

        if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {

        }
        else if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Emp")) {
            userName = heder.findViewById(R.id.username1);
            userType = heder.findViewById(R.id.user_type1);
            userName.setText(Config.getUser_name(getApplicationContext()));
            userType.setText(Config.getLoginUserType(getApplicationContext()));
        }
        else{
            userName = heder.findViewById(R.id.username2);
            userType = heder.findViewById(R.id.user_type2);
            try {
                userName.setText(Config.getUser_name(getApplicationContext()));
                userType.setText(Config.getLoginUserType(getApplicationContext()));
            }catch (Exception e){

            }


        }

        ProfileData();




        if (cd.isConnectingToInternet()) {


            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mobile.getText().length() == 10) {

                        mobilenum = mobile.getText().toString();
                        Update(mobilenum);


                    } else {

                        mobile.setError("Enter 10 digits");
                    }

                }
            });

        }
        else {

            submit.setVisibility(View.GONE);
            mobile.setFocusable(false);
        }
    }

    public void ProfileData() {


        if(cd.isConnectingToInternet()){

        try{

            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            profiledata=new ArrayList<>();
            Call<Get_Profile_response> call=api.getProfileData("get", Config.getUserId(getApplicationContext()), Config.getCorp_code(getApplicationContext()),Config.getLoginUserType(getApplicationContext()));
            call.enqueue(new Callback<Get_Profile_response>() {
                @Override
                public void onResponse(Call<Get_Profile_response> call, Response<Get_Profile_response> response) {
                    profileResponse=response.body();
                    if(profileResponse.getData()!=null){
                    for(int i=0;i<profileResponse.getData().length;i++) {
                        profiledata.add(profileResponse.getData()[i]);
                        Log.d("TAG", "onResponse:fffffd" + profileResponse.getData()[i].getCity());
                        Log.d("TAG", "onResponse:cu" + profileResponse.getData()[i].getCurrentAddress());

                        try {
                            username.setText(Config.getUser_name(getApplicationContext()));
                            fullname.setText(profileResponse.getData()[i].getFullname());
                            empId.setText(profileResponse.getData()[i].getEmpid());
                            mobile.setText(profileResponse.getData()[i].getMobile());
                            role.setText(profileResponse.getData()[i].getRole());
                            email.setText(profileResponse.getData()[i].getEmail());
                            address.setText(profileResponse.getData()[i].getCity());
                            currentAddress.setText(profileResponse.getData()[i].getCurrentAddress());
                        } catch (Exception e) {

                        }

                    }


                    }

                }

                @Override
                public void onFailure(Call<Get_Profile_response> call, Throwable t) {
                    Log.d("TAG", "onFailure:failure"+t.getMessage());
                }
            });

        }catch (Exception e){

            e.printStackTrace();
        }

    }else{

        Toast.makeText(getApplicationContext(), "sorry not connected to internet", Toast.LENGTH_SHORT).show();
    }


    }


    public boolean onOptionsItemSelected(MenuItem item) {


        if(toggle.onOptionsItemSelected( item )){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();

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

            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Customer")) {
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
            }else{}
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
            Intent i=new Intent( getApplicationContext(), UpdateOffers.class );
            startActivity( i );
            finish();
        }
        else if(id== R.id.logout){
            logoutAlert();

        }
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }
    public void logoutAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder( this);


        alertDialog.setTitle("Alert!");


        alertDialog
                .setMessage("Do you want to logout?");


        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Config.saveLoginStatus(getApplication(),"0");


                        Config.saveLoginStatus(getApplication(),"0");
                        Intent i=new Intent(getApplicationContext(), Login.class);
                        startActivity(i);
                        finish();
                    }
                });


        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


        alertDialog.show();
    }

    public boolean Update(String mobilenum){



        if(cd.isConnectingToInternet()){
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<Get_Profile_response> call=api.updateProfileData("update", Config.getUserId(getApplicationContext()),mobilenum, Config.getCorp_code(getApplicationContext()),Config.getLoginUserType(getApplicationContext()));
                call.enqueue(new Callback<Get_Profile_response>() {
                    @Override
                    public void onResponse(Call<Get_Profile_response> call, Response<Get_Profile_response> response) {
                        profileResponse=response.body();
                        if(profileResponse.getStatus().equalsIgnoreCase("true")){

                            Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "onResponse:update");
                        }
                    }

                    @Override
                    public void onFailure(Call<Get_Profile_response> call, Throwable t) {
                        Log.d("TAG", "onFailure:failure"+t.getMessage());


                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            Toast.makeText(getApplicationContext(),
                    "Sorry not connected to internet",
                    Toast.LENGTH_LONG).show();
        }


        return true;
    }



    }

