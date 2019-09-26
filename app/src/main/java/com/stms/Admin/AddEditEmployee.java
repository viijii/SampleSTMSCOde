package com.stms.Admin;

import android.content.Intent;
import android.drm.DrmStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class AddEditEmployee extends AppCompatActivity implements View.OnClickListener {

EditText fullName,mobileNumber,aadhar,empId,email,currentAddress,joiningDate,permanentAddress,designation,city;
EditText userName,password;
RadioButton employee, manager, active, inActive,director;
Button submit;
String action,userType="",user_status="",EmployeeId;
addEditEmployeeResponse addEditEmployeeResponse;
ConnectionDetector cd;
Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_edit_employee );

        Toolbar toolbar = findViewById(R.id.toolbar);


        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


        fullName=findViewById( R.id.full_name );
        userName=findViewById( R.id.userName );
        password=findViewById( R.id.password );
        mobileNumber=findViewById( R.id.phone );
        aadhar=findViewById( R.id.aadhar );

        city=findViewById( R.id.city );
        empId=findViewById( R.id.empId );
        email=findViewById( R.id.email );
        employee=findViewById( R.id.employee );
        manager=findViewById( R.id.manager );
        director=findViewById( R.id.director );
        currentAddress=findViewById( R.id.currentaddress );
        permanentAddress=findViewById( R.id.permanantAddress );
        joiningDate=findViewById( R.id.joiningdate );
        designation=findViewById( R.id.designation );
        active=findViewById( R.id.active );
        inActive=findViewById( R.id.inactive );
        submit=findViewById( R.id.submit );
      cd=new ConnectionDetector( getApplicationContext() );
         intent=getIntent();


        action=intent.getStringExtra( "action" );

        Log.d( "TAG", "onCreate: action2" +action);
        EmployeeId=getIntent().getExtras().getString( "eId" );
        Log.d( "TAG", "onCreate: actionempId" +EmployeeId);

    submit.setOnClickListener( this );
       active.setOnClickListener( this );
        inActive.setOnClickListener( this );
        employee.setOnClickListener( this );
        manager.setOnClickListener( this );
        director.setOnClickListener( this );

    if(action.equalsIgnoreCase( "add" )){

        toolbar.setTitle("Add Employees");
        Log.d( "TAG", "onCreate:jagadeesh "+action );

    }else if(action.equalsIgnoreCase( "edit")){
        Log.d( "TAG", "onCreate:jagadeesh1 "+action );
        toolbar.setTitle("Edit Employees");
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
                                        saveUsers("update");

                                    }

                                } else mobileNumber.setError("Enter valid mobile number");

                            } else email.setError("enter valid email");
                        } else email.setError("enter valid email");
                    }else userName.setError("allows only alphabets");
                }else fullName.setError("allows only alphabets");
            }
        }

        else if (v.getId() == R.id.employee) {
            userType = "Emp";
        } else if (v.getId() == R.id.manager) {
            userType = "Manager";
        }
        else if (v.getId() == R.id.director) {
            userType = "Director";
        }else if (v.getId() == R.id.active) {
            user_status = "active";
        } else if (v.getId() == R.id.inactive) {
            user_status = "inactive";
            Config.showToast(AddEditEmployee.this, "User will not able to login if account deactivated ");
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
                Log.d( "TAG","onResponse:addtype "+userType );
                Log.d( "TAG","onResponse:addstatus "+fullName );
                Log.d( "TAG","onResponse:addstatus "+mobileNumber );
                Log.d( "TAG","onResponse:addstatus "+empId );
                Log.d( "TAG","onResponse:addstatus "+city );
                Log.d( "TAG","onResponse:addstatus "+aadhar );
                Log.d( "TAG","onResponse:addstatus "+currentAddress );
                Log.d( "TAG","onResponse:addstatus "+permanentAddress );
                Log.d( "TAG","onResponse:addstatus "+designation );
                Log.d( "TAG","onResponse:addstatus "+userName );
                Log.d( "TAG","onResponse:addstatus "+password );
                Log.d( "TAG","onResponse:addstatus "+joiningDate );
                Log.d( "TAG","onResponse:addstatus "+Config.getCorp_code( getApplicationContext() ) );
                Log.d( "TAG","onResponse:addstatus "+Config.getUserId( getApplicationContext() )  );



                Call<addEditEmployeeResponse> call = api.saveusers(
                        action,
                        fullName.getText().toString(),
                        mobileNumber.getText().toString(),
                        empId.getText().toString(),
                        city.getText().toString(),
                        aadhar.getText().toString(),
                        email.getText().toString(),
                        currentAddress.getText().toString(),
                        permanentAddress.getText().toString(),
                        joiningDate.getText().toString(),
                        designation.getText().toString(),
                        userName.getText().toString(),
                        password.getText().toString(),
                        user_status,userType,
                        Config.getUserId( getApplicationContext() ),
                        Config.getCorp_code( getApplicationContext() )




                );
                call.enqueue(new Callback<addEditEmployeeResponse>() {
                    @Override
                    public void onResponse(Call<addEditEmployeeResponse> call, Response<addEditEmployeeResponse> response) {
                        addEditEmployeeResponse = response.body();

                        if(addEditEmployeeResponse.getStatus().equalsIgnoreCase( "true" )){

                            Intent i = new Intent(AddEditEmployee.this, ViewEmployees.class);
                            startActivity( i );


                        }else{
                            Toast.makeText( AddEditEmployee.this, "Update Again", Toast.LENGTH_SHORT ).show();

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


                Call<addEditEmployeeResponse> call = api.getDetails("get",EmployeeId,Config.getCorp_code( getApplicationContext() ));
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
                                    empId.setText( addEditEmployeeResponse.getData()[i].getEmpId() );
                                    currentAddress.setText( addEditEmployeeResponse.getData()[i].getCurrentAddress() );
                                    permanentAddress.setText( addEditEmployeeResponse.getData()[i].getPermanentAddress() );
                                    joiningDate.setText( addEditEmployeeResponse.getData()[i].getJoiningDate() );
                                    designation.setText( addEditEmployeeResponse.getData()[i].getDesignation() );
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

                                    if(addEditEmployeeResponse.getData()[i].getUserType().equalsIgnoreCase( "Emp" )){
                                        employee.setChecked( true );
                                        userType="Emp";
                                    }else{
                                        employee.setChecked( false );
                                    }

                                    if(addEditEmployeeResponse.getData()[i].getUserType().equalsIgnoreCase( "manager" )){
                                        manager.setChecked( true );
                                        userType="Manager";
                                    }else{
                                        manager.setChecked( false );
                                    }
                                    if(addEditEmployeeResponse.getData()[i].getUserType().equalsIgnoreCase( "director" )){
                                        director.setChecked( true );
                                        userType="Director";
                                    }else{
                                        director.setChecked( false );
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
