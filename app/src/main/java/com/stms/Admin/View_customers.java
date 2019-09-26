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

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stms.Adapters.CustomerAdapter;
import com.stms.CommonScreens.FieldActivityUpload;
import com.stms.CommonScreens.Profile;
import com.stms.CommonScreens.UpdateOffers;
import com.stms.Launch.Login;
import com.stms.R;
import com.stms.Responses.CommonResponse;
import com.stms.Responses.customerResponse;

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

public class View_customers extends AppCompatActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener
{
    RecyclerView listview;
    CustomerAdapter customerAdapter;
    ArrayList<customerResponse.data> data;
    customerResponse userResponse;
    ConnectionDetector cd;
    Button add_employee;
CommonResponse commonResponse;
    EditText search;
    LinearLayout linearLayout;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_customer_details_management );


        // Config.showLoader(ViewEmployees.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("View Customers");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        add_employee=findViewById(R.id.addcustomer);
        linearLayout= findViewById( R.id.linearlayout );

        listview = findViewById(R.id.listview);
        search = findViewById(R.id.search);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(View_customers.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(layoutManager);
        listview.setHasFixedSize(true);

        add_employee.setOnClickListener(this);
        linearLayout.setOnClickListener(this);

        cd = new ConnectionDetector(View_customers.this);


        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCustomers();
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
                        customerAdapter.getFilter().filter(s.toString());
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
        getCustomers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is prese`nt.
        getMenuInflater().inflate(R.menu.admin_dashboard, menu);

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
        if (id == android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    /*this method is used to send the data to adapter to display the data*/



    /*this method is used to get employee details */

    public void getCustomers()
    {
        try
        {
            if (cd.isConnectingToInternet())
            {
                Config.showLoader(View_customers.this);
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Call<customerResponse> call = api.getCustomers(Config.getUserId(View_customers.this), Config.getCorp_code(getApplicationContext())
                        );

                call.enqueue(new Callback<customerResponse>()
                {
                    @Override
                    public void onResponse(Call<customerResponse> call,
                                           Response<customerResponse> response)
                    {
                        Config.closeLoader();
                        userResponse = response.body();

                        data = new ArrayList<customerResponse.data>();
                        Log.d( "TAG", "onResponse: status"+userResponse );

                        if(userResponse.getStatus().equalsIgnoreCase("true"))

                        {
                            //System.out.println("User Data---"+userResponse.getData().length);
                            if(userResponse.getData()!= null)
                            {
                                data.clear();
                                for(int i=0; i<userResponse.getData().length; i++ )
                                {
                                    //  System.out.println("User Details--"+userResponse.getData()[i].getFull_name());
                                    data.add(userResponse.getData()[i]);
                                    Log.d( "TAG", "onResponse:jagadeesh " +userResponse.getData()[i].getNoOfVeh());
                                    Log.d( "TAG", "onResponse:jagadeesh " +userResponse.getData()[i].getCustomerName());
                                }
                            }

                        }
                        setListview();
                    }

                    @Override
                    public void onFailure(Call<customerResponse> call, Throwable t)
                    {
                        Config.closeLoader();
                        t.getMessage();
                        Toast.makeText(View_customers.this,
                                "Try Again!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
            {
                Toast.makeText(View_customers.this,
                        "Sorry not connected to internet",
                        Toast.LENGTH_LONG).show();
            }
        }


        catch(Exception e)
        {
            System.out.println("Exception e"+e);
        }

    }

    public void setListview()
    {
        customerAdapter    = new CustomerAdapter(View_customers.this,data,View_customers.this,getApplicationContext());
        listview.setAdapter(customerAdapter);
    }


    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.addcustomer||v.getId()==R.id.linearlayout)
        {
           Intent i = new Intent(View_customers.this, AddCustomers.class);
            i.putExtra("action","add");
            startActivity(i);

        }
    }


    /*this method is used for calling purpose*/

    public void callUser(int position)
    {

        callAlert(position);
    }

    /*this method is used to give alert for calling*/

    public void callAlert(final int position) {


        Toast.makeText(this,"calling",Toast.LENGTH_SHORT).show();

        String call = data.get(position).getMobile();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        //  callIntent.setData(Uri.parse("tel:"+number));
        callIntent.setData(Uri.parse("tel:" + call));

        // startActivity(callIntent);


        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        startActivity(callIntent);
    }

    /*this method is used for mailing purpose*/


    /*this method is used to give alert for mailing*/


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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


        }
        else if (id == R.id.manageEmployees)
        {
            Intent i=new Intent(getApplicationContext(),ViewEmployees.class);
            startActivity(i);
            finish();
        }

        else if (id == R.id.manageVehicles)
        {
            Intent i=new Intent(getApplicationContext(),View_Vehicals.class);
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
        else if(id==R.id.managetask){
            Intent i=new Intent(getApplicationContext(),Admin_manageTask.class);
            startActivity(i);
            finish();

        }
        else if(id==R.id.quotations){
            Intent i = new Intent(getApplicationContext(), Admin_quotationsDashboard.class);
            startActivity(i);
            finish();

        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer( GravityCompat.START);
        return true;
    }

    /*this method is used to give alert for logout*/

    public void logoutAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(View_customers.this);

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

                        Intent i = new Intent(View_customers.this, Login.class);
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


    public void customerDeleteAlert(final String position, final String role)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(View_customers.this);

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
                        deleteUserById(position,role);
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

    public void deleteUserById(String position,String role)
    {
        try
        {
            if (cd.isConnectingToInternet())
            {
                Config.showLoader(View_customers.this);
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Call<CommonResponse> call = api.customerDelete("delete", Config.getCorp_code(getApplicationContext()),position,"customer");

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
                            Config.showToast(View_customers.this,"Deleted");
                            getCustomers();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t)
                    {
                        Config.closeLoader();
                        t.getMessage();
                        Toast.makeText(View_customers.this,
                                "Try Again!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
            {
                Toast.makeText(View_customers.this,
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


