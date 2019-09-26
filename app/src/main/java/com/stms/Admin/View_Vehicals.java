/*
Initial Declaration :-
        file name :- ViewEmployees.java.
        Purpose :- To get the employee details,to update the employee details and for calling and mailing .
        Methods :-setListview(), getEmployees(),updateDetails(),callUser(),callAlert()
                   emailUser(),emailAlert(),deleteUser(),deleteAlert(),deleteUserById(),logoutAlert().
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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stms.Adapters.VehicalAdapter;
import com.stms.CommonScreens.FieldActivityUpload;
import com.stms.CommonScreens.Profile;
import com.stms.CommonScreens.UpdateOffers;
import com.stms.Launch.Login;
import com.stms.R;
import com.stms.Responses.CommonResponse;
import com.stms.Responses.VehicalResponse;

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

public class View_Vehicals extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    Toolbar toolbar;
    RecyclerView employeeslist_rv;
    NavigationView navigationView;
    EditText search;
    LinearLayout addEmployee;
    Button add;
    VehicalAdapter vehicalAdapter;
    ConnectionDetector cd;
    VehicalResponse vehicalResponse;
    ArrayList<VehicalResponse.data> data;
    CommonResponse commonResponse;
    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_view_vehicles );
        search=findViewById(R.id.search);
        toolbar = findViewById(R.id.toolbar);
        employeeslist_rv = findViewById(R.id.employees_list_view);
        drawer = findViewById(R.id.drawer_layout);
        toolbar.setTitle("Manage Vehicles");
        navigationView = findViewById(R.id.nav_view);
        employeeslist_rv.setLayoutManager(new LinearLayoutManager(this));

        addEmployee = findViewById( R.id.linearlayout );
        add = findViewById( R.id.add_employee );
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        addEmployee.setOnClickListener( this );
        add.setOnClickListener( this );
        cd=new ConnectionDetector( getApplicationContext() );

        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getVehicles();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        try{
            search.setInputType(InputType.TYPE_CLASS_TEXT);
            search.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    try {
                        // filter recycler view when query submitted
                        vehicalAdapter.getFilter().filter(s.toString());
                    }catch (Exception e){

                    }

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                    System.out.println("Search String---"+s.toString());


                }
            });}
        catch (Exception e)
        {

        }

        getVehicles();

    }

    public void getVehicles() {

        try
        {
            if (cd.isConnectingToInternet())
            {
                Config.showLoader(View_Vehicals.this);
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Call<VehicalResponse> call = api.getVehicle("get", Config.getCorp_code( getApplicationContext()) );

                call.enqueue(new Callback<VehicalResponse>()
                {
                    @Override
                    public void onResponse(Call<VehicalResponse> call,
                                           Response<VehicalResponse> response)
                    {
                        Config.closeLoader();

                        vehicalResponse = response.body();

                        data = new ArrayList<VehicalResponse.data>();


                        if(vehicalResponse.getStatus().equalsIgnoreCase("true"))
                        {
                            //System.out.println("User Data---"+userResponse.getData().length);
                            if(vehicalResponse.getData()!= null)
                            {
                                data.clear();
                                for(int i=0; i<vehicalResponse.getData().length; i++ )
                                {
                                    System.out.println("User Details--"+vehicalResponse.getData()[i].getCustomerName());
                                    data.add(vehicalResponse.getData()[i]);
                                    //Config.closeLoader();
                                }
                            }

                        }
                        setListview();
                    }

                    @Override
                    public void onFailure(Call<VehicalResponse> call, Throwable t)
                    {
                        Config.closeLoader();
                        t.getMessage();
                        Toast.makeText(View_Vehicals.this,
                                "Try Again!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
            {
                Toast.makeText(View_Vehicals.this,
                        "Sorry not connected to internet",
                        Toast.LENGTH_LONG).show();
            }
        }


        catch(Exception e)
        {
            System.out.println("Exception e"+e);
        }




    }

    public void setListview() {
       // Log.d( "TAG","setListview: "+data.get( 0 ).getCustomerName() );
        vehicalAdapter=new VehicalAdapter( getApplicationContext(),data,View_Vehicals.this );
        employeeslist_rv.setAdapter( vehicalAdapter );

    }


    public boolean onOptionsItemSelected(MenuItem item) {


        if(toggle.onOptionsItemSelected( item )){

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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

        }

     else if (id == R.id.managetask)
        {
            Intent i=new Intent(getApplicationContext(), Admin_manageTask.class);
            startActivity(i);
            finish();

        }
        else if (id == R.id.customerNotifications)
        {
            Intent i=new Intent(getApplicationContext(), Admin_dashboard.class);
            startActivity(i);
            finish();

        }
        else if (id == R.id.quotations)
        {

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


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer( GravityCompat.START);
        return true;
    }


    public void logoutAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(View_Vehicals.this);

        // Setting Dialog Title
        alertDialog.setTitle("Alert!");

        // Setting Dialog Message
        alertDialog
                .setMessage("Do you really want to logout?");

        // On pressing Settings button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Config.saveLoginStatus(getApplicationContext(), "No");

                        Intent i = new Intent(View_Vehicals.this, Login.class);
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
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.add_employee || id == R.id.linearlayout) {

            Intent i = new Intent( View_Vehicals.this,AddEditVehicals.class );
            i.putExtra("action","add");
             startActivity( i );


        }

    }

    public void modify(String id) {
        Intent i = new Intent( View_Vehicals.this,EditVehicals.class );
        i.putExtra("action","edit");
         i.putExtra("id",id);
         i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity( i );

    }

    public void VehicledeleteAlert(final String position, final String vehicleType)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(View_Vehicals.this);

        // Setting Dialog Title
        alertDialog.setTitle("Alert!");

        // Setting Dialog Message
        alertDialog
                .setMessage("Do you really want to delete?");

        // On pressing Settings button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                        deleteUserById(position,vehicleType);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

    /*this method is used to delete the employee*/

    public void deleteUserById(String position,String vehicleType)
    {
        try
        {
            if (cd.isConnectingToInternet())
            {
                Config.showLoader(View_Vehicals.this);
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Call<CommonResponse> call = api.vehicleDelete("delete", Config.getCorp_code(getApplicationContext()),position,"vehicle");

                call.enqueue(new Callback<CommonResponse>()
                {
                    @Override
                    public void onResponse(Call<CommonResponse> call,
                                           Response<CommonResponse> response)
                    {
                        Config.closeLoader();
                        commonResponse = response.body();

                        if(commonResponse.getStatus().equalsIgnoreCase("true"))
                        {
                            Config.showToast(View_Vehicals.this,"Deleted");
                            getVehicles();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t)
                    {
                        Config.closeLoader();
                        t.getMessage();
                        Toast.makeText(View_Vehicals.this,
                                "Try Again!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
            {
                Toast.makeText(View_Vehicals.this,
                        "Sorry not connected to internet",
                        Toast.LENGTH_LONG).show();
            }
        }


        catch(Exception e)
        {
            System.out.println("Exception e"+e);
        }
    }

}





