package com.stms.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.stms.R;
import com.stms.Responses.addEditEmployeeResponse;
import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCustomers extends AppCompatActivity implements View.OnClickListener {


    EditText fullName,mobileNumber,aadhar,email,currentAddress,permanentAddress,city;
    EditText userName,password;
    RadioButton  active, inActive;
    Button submit;
    String action,user_status="",EmployeeId;
    com.stms.Responses.addEditEmployeeResponse addEditEmployeeResponse;
    ConnectionDetector cd;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_customers);

        Toolbar toolbar = findViewById(R.id.toolbar);


        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i=new Intent( AddCustomers.this,View_customers.class );
               i.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
               startActivity( i );


            }
        });


        fullName=findViewById( R.id.full_name );
        userName=findViewById( R.id.userName );
        password=findViewById( R.id.password );
        mobileNumber=findViewById( R.id.phone );
        aadhar=findViewById( R.id.aadhar );

        city=findViewById( R.id.city );

        email=findViewById( R.id.email );

        currentAddress=findViewById( R.id.currentaddress );
        permanentAddress=findViewById( R.id.permanantAddress );

        active=findViewById( R.id.active );
        inActive=findViewById( R.id.inactive );
        submit=findViewById( R.id.submit );
        cd=new ConnectionDetector( getApplicationContext() );
        intent=getIntent();


        action=intent.getStringExtra( "action" );

        Log.d( "TAG", "onCreate: action2" +action);
        EmployeeId=getIntent().getExtras().getString( "cId" );


        Log.d("TAG", "onCreate:eid "+EmployeeId);
        submit.setOnClickListener( this );
        active.setOnClickListener( this );
        inActive.setOnClickListener( this );


        if(action.equalsIgnoreCase( "add" )){
            toolbar.setTitle(" Add Customers");

            Log.d( "TAG", "onCreate:jagadeesh "+action );

        }else if(action.equalsIgnoreCase( "edit")){
            Log.d( "TAG", "onCreate:jagadeesh1 "+action );
            toolbar.setTitle(" Edit Customers");
            getData(EmployeeId);

        }


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit) {

            if (fullName.getText().toString().length() == 0) {
                fullName.setError("Enter Name");
            } else if (email.getText().toString().length() == 0) {
                email.setError("Enter Email");
            } else if (userName.getText().toString().length() == 0) {
                userName.setError("Enter Username");
            } else if (password.getText().toString().length() == 0) {
                password.setError("Enter Password");
            }
            else if (aadhar.getText().toString().length() == 0) {
                aadhar.setError("Enter Aadhar");
            }
            else if (mobileNumber.getText().toString().length() == 0) {
                mobileNumber.setError("Enter MobileNumber");
            }
            else
            {
                if(fullName.getText().toString().matches("[a-zA-Z.? ]*")) {
                    if (userName.getText().toString().matches("[a-zA-Z.? ]*")) {
                        if (email.getText().toString().contains("@")) {
                            if (email.getText().toString().contains(".com")) {
                                if ((mobileNumber.getText().toString().length() == 10) && ((mobileNumber.getText().toString().startsWith("6") ||
                                        (mobileNumber.getText().toString().startsWith("8") || (mobileNumber.getText().toString().startsWith("9") ||
                                                (mobileNumber.getText().toString().startsWith("7"))))))) {

                                    if (action.equalsIgnoreCase( "add")) {

                                        Log.d( "TAG", "onClick:saveactions "+action );

                                        saveUsers("save");
                                    } else if(action.equalsIgnoreCase( "edit")){
                                        Log.d( "TAG", "onClick:saveactions1 "+action );
                                        UpdateUsers("update");

                                    }

                                } else mobileNumber.setError("Enter valid mobile number");

                            } else email.setError("enter valid email");
                        } else email.setError("enter valid email");
                    }else userName.setError("allows only alphabets");
                }else fullName.setError("allows only alphabets");
            }
        }
        else if (v.getId() == R.id.active) {
            user_status = "active";
        } else if (v.getId() == R.id.inactive) {
            user_status = "inactive";
            Config.showToast(AddCustomers.this, "User will not able to login if account deactivated ");
        }

    }

    public void saveUsers(String action) {
        if(cd.isConnectingToInternet()) {
            try {
                Log.d( "TAG","onResponse:add1 " );
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory( GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Log.d( "TAG", "saveUsers:action "+action );

                Log.d( "TAG","onResponse:addstatus "+fullName.getText().toString() );
                Log.d( "TAG","onResponse:addstatus "+mobileNumber.getText().toString()+"  "+email.getText().toString() );

                Log.d( "TAG","onResponse:addstatus "+city.getText().toString() );
                Log.d( "TAG","onResponse:addstatus "+aadhar.getText().toString() );
                Log.d( "TAG","onResponse:addstatus "+currentAddress.getText().toString() );
                Log.d( "TAG","onResponse:addstatus "+permanentAddress.getText().toString() );

                Log.d( "TAG","onResponse:addstatus "+userName.getText().toString()+"  "+user_status );
                Log.d( "TAG","onResponse:addstatus "+password.getText().toString());

                Log.d( "TAG","onResponse:addstatus "+Config.getCorp_code( getApplicationContext() ) );
                Log.d( "TAG","onResponse:addstatus "+Config.getUserId( getApplicationContext() )  );

                Call<addEditEmployeeResponse> call = api.savecustomers(
                        action,
                        fullName.getText().toString(),
                        mobileNumber.getText().toString(),
                        city.getText().toString(),
                        aadhar.getText().toString(),
                        email.getText().toString(),
                        currentAddress.getText().toString(),
                        permanentAddress.getText().toString(),
                        userName.getText().toString(),
                        password.getText().toString(),
                        user_status,
                        Config.getUserId( getApplicationContext() ),
                        Config.getCorp_code( getApplicationContext() )

                );
                call.enqueue(new Callback<addEditEmployeeResponse>() {

                    @Override
                    public void onResponse(Call<addEditEmployeeResponse> call, Response<addEditEmployeeResponse> response) {
                        addEditEmployeeResponse = response.body();

                        Log.d( "TAG", "onResponse:status" +addEditEmployeeResponse.getStatus());

                        if(addEditEmployeeResponse.getStatus().equalsIgnoreCase( "true" )){

                            Intent i = new Intent(AddCustomers.this, View_customers.class);
                            startActivity( i );
                            finish();

                        }else {

                            Toast.makeText(AddCustomers.this, "Update Again", Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onFailure(Call<addEditEmployeeResponse> call, Throwable t) {

                        Config.closeLoader();
                        t.getMessage();
                        Log.d( "TAG", "onFailure:kk "+t.getMessage() );

                    }
                });
            } catch (Exception e) {
                System.out.println("Exception e" + e);
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"no internet", Toast.LENGTH_LONG).show();
        }
    }


    public void UpdateUsers(String action) {
        if(cd.isConnectingToInternet()) {
            try {
                Log.d( "TAG","onResponse:add1 " );
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory( GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Log.d( "TAG", "saveUsers:action "+action );

                Log.d( "TAG","onResponse:addstatus "+fullName.getText().toString() );
                Log.d( "TAG","onResponse:addstatus "+mobileNumber.getText().toString()+"  "+email.getText().toString() );

                Log.d( "TAG","onResponse:addstatus "+city.getText().toString() );
                Log.d( "TAG","onResponse:addstatus "+aadhar.getText().toString() );
                Log.d( "TAG","onResponse:addstatus "+currentAddress.getText().toString() );
                Log.d( "TAG","onResponse:addstatus "+permanentAddress.getText().toString() );

                Log.d( "TAG","onResponse:addstatus "+userName.getText().toString()+"  "+user_status );
                Log.d( "TAG","onResponse:addstatus "+password.getText().toString());

                Log.d( "TAG","onResponse:addstatus "+Config.getCorp_code( getApplicationContext() ) );
                Log.d( "TAG","onResponse:addstatus "+Config.getUserId( getApplicationContext() )  );

                Call<addEditEmployeeResponse> call = api.savecustomers(
                        action,
                        fullName.getText().toString(),
                        mobileNumber.getText().toString(),
                        city.getText().toString(),
                        aadhar.getText().toString(),
                        email.getText().toString(),
                        currentAddress.getText().toString(),
                        permanentAddress.getText().toString(),
                        userName.getText().toString(),
                        password.getText().toString(),
                        user_status,
                        EmployeeId,
                        Config.getCorp_code( getApplicationContext() )

                );
                call.enqueue(new Callback<addEditEmployeeResponse>() {

                    @Override
                    public void onResponse(Call<addEditEmployeeResponse> call, Response<addEditEmployeeResponse> response) {
                        addEditEmployeeResponse = response.body();

                        Log.d( "TAG", "onResponse:status" +addEditEmployeeResponse.getStatus());

                        if(addEditEmployeeResponse.getStatus().equalsIgnoreCase( "true" )){

                            Intent i = new Intent(AddCustomers.this, View_customers.class);
                            startActivity( i );
                            finish();

                        }else {

                            Toast.makeText(AddCustomers.this, "Update Again", Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onFailure(Call<addEditEmployeeResponse> call, Throwable t) {

                        Config.closeLoader();
                        t.getMessage();
                        Log.d( "TAG", "onFailure:kk "+t.getMessage() );

                    }
                });
            } catch (Exception e) {
                System.out.println("Exception e" + e);
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"no internet", Toast.LENGTH_LONG).show();
        }
    }


    public void getData(String EmployeeId) {

        if(cd.isConnectingToInternet()) {
            try {
                Log.d( "TAG","onResponse:add1 " );
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory( GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);


                Call<addEditEmployeeResponse> call = api.getDetails("getCustomer",EmployeeId,Config.getCorp_code( getApplicationContext() ));
                call.enqueue(new Callback<addEditEmployeeResponse>() {
                    @Override
                    public void onResponse(Call<addEditEmployeeResponse> call, Response<addEditEmployeeResponse> response) {
                        addEditEmployeeResponse = response.body();

                        Log.d( "TAG", "onCreate: fullName33"+addEditEmployeeResponse.getMessage());
                        if(addEditEmployeeResponse.getStatus().equalsIgnoreCase( "true" )){

                            if(addEditEmployeeResponse.getData()!= null)
                            {
                                for(int i=0; i<addEditEmployeeResponse.getData().length; i++ )
                                {
                                    Log.d( "TAG", "onResponse: fullname"+addEditEmployeeResponse.getData()[i].getFullName());
                                    fullName.setText( addEditEmployeeResponse.getData()[i].getFullName() );
                                    mobileNumber.setText( addEditEmployeeResponse.getData()[i].getMobileNumber());
                                    aadhar.setText( addEditEmployeeResponse.getData()[i].getAadhar() );
                                    email.setText( addEditEmployeeResponse.getData()[i].getEmail() );

                                    currentAddress.setText( addEditEmployeeResponse.getData()[i].getCurrentAddress() );
                                    permanentAddress.setText( addEditEmployeeResponse.getData()[i].getPermanentAddress() );

                                    city.setText( addEditEmployeeResponse.getData()[i].getCity() );
                                    userName.setText( addEditEmployeeResponse.getData()[i].getUserName() );
                                    password.setText(addEditEmployeeResponse.getData()[i].getPassword());

                                    if(addEditEmployeeResponse.getData()[i].getUserStatus().equalsIgnoreCase( "active" )){
                                        active.setChecked( true );
                                        user_status="active";
                                    }else{
                                        active.setChecked( false );
                                    }

                                    if(addEditEmployeeResponse.getData()[i].getUserStatus().equalsIgnoreCase( "inactive" )){
                                        inActive.setChecked( true );
                                        user_status="inactive";
                                    }else{
                                        inActive.setChecked( false );
                                    }
                                    Log.d( "TAG", "onResponse:cityrore "+ addEditEmployeeResponse.getData()[i].getCity());


                                }
                            }


                        }else{

                        }



                    }

                    @Override
                    public void onFailure(Call<addEditEmployeeResponse> call, Throwable t) {

                        Config.closeLoader();
                        t.getMessage();

                    }
                });
            } catch (Exception e) {
                System.out.println("Exception e" + e);
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"no internet", Toast.LENGTH_LONG).show();
        }
    }


}
