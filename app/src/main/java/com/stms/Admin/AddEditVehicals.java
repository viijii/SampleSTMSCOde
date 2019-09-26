package com.stms.Admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stms.R;
import com.stms.Responses.Add_Employee_response;
import com.stms.Responses.Spinner_response;
import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEditVehicals extends AppCompatActivity implements View.OnClickListener {
    ConnectionDetector cd;
    Button submit;
    Spinner customer, vehicleType,vehicleModel;
    EditText vehicleId, trackerId, trackerPwd, vtype,nextService,purchaseDate;
    Toolbar toolbar;
    TextView veh_model;
    String action;
    String customer1, vtype1,vehModel;
    ArrayList<String>  customer2, vtype2,model;
    Spinner_response spinner_response;
    Add_Employee_response add_employee_response;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add_edit_vehicals );

        toolbar = findViewById( R.id.toolbar );


        action = getIntent().getExtras().getString( "action" );
        Log.d( "TAG","onCreate:tuls " + action );


        submit = findViewById( R.id.submit1 );
        customer = findViewById( R.id.customer1 );
        vehicleType = findViewById( R.id.vehical_type1 );
        vehicleId = findViewById( R.id.vehid1 );
        trackerId = findViewById( R.id.tracker_id1 );
        trackerPwd = findViewById( R.id.password1 );
        vtype = findViewById( R.id.vtype_text );
        nextService= findViewById(R.id.next_service);
        purchaseDate= findViewById(R.id.purchase_date);
        vehicleModel= findViewById( R.id.vehical_model );
        veh_model=findViewById(R.id.veh_model);
        cd = new ConnectionDetector( getApplicationContext() );

        if (action.equals( "add" )) {
            toolbar.setTitle( "Add Vehicles" );
        } else {
            toolbar.setTitle( "Edit User" );
        }


        submit.setOnClickListener( this );
        toolbar.setNavigationOnClickListener( this );

        customers();
        vehicalType();

        customer.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView,View view,int i,long l) {
                customer1 = customer.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        } );

        vehicleType.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent,View view,int position,long id) {
                vtype1 = vehicleType.getSelectedItem().toString();
                getVehicleModel( vtype1);
                vehicleModel.setVisibility( View.VISIBLE );
                veh_model.setVisibility( View.VISIBLE );


                Log.d( "TAG","onItemSelected: " + vtype1 );
                if (vehicleType.getSelectedItem().toString().equalsIgnoreCase( "other" )) {
                    vtype.setVisibility( View.VISIBLE );
                    vtype.addTextChangedListener( new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s,int start,int count,int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s,int start,int before,int count) {
                            vtype1 = vtype.getText().toString();
                            Log.d( "TAG","onItemSelected:teamtext " + vtype1 );
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    } );
                } else {
                    vtype.setVisibility( View.GONE );
                }
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        } );

        vehicleModel.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent,View view,int position,long id) {
                vehModel = vehicleModel.getSelectedItem().toString();
                Log.d( "TAG","onItemSelected: " + vehModel );

            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        } );


    }

    public void customers() {

        if (cd.isConnectingToInternet()) {
            try {
                customer2 = new ArrayList <>();
                Log.d( "TAG","onResponse:spin1 " );
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl ).
                        client( okHttpClient ).
                        addConverterFactory( GsonConverterFactory
                                .create() ).build();
                API api = RestClient.client.create( API.class );
                Log.d( "TAG","getEmpTeam: before" );
                Call <Spinner_response> call = api.getcustomer( "customer", Config.getCorp_code( getApplicationContext() ) );
                call.enqueue( new Callback <Spinner_response>() {
                    @Override
                    public void onResponse(Call <Spinner_response> call,Response <Spinner_response> response) {
                        spinner_response = response.body();
                        Log.d( "TAG","getEmpTeam: after" );
                        Log.d( "TAG","onResponse:data " + spinner_response );

                        customer2 = new ArrayList();

                        if (spinner_response.getStatus().equalsIgnoreCase( "True" )) {
                            for (int i = 0; i < spinner_response.getData().length; i++) {
                                Log.d( "TAG","onResponse:data1234 " + spinner_response.getData()[i].getCus() );
                                customer2.add( spinner_response.getData()[i].getCus());
                            }

                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                getApplicationContext(),
                                android.R.layout.simple_spinner_item,
                                customer2
                        );
                        customer.setAdapter( adapter );

                    }

                    @Override
                    public void onFailure(Call <Spinner_response> call,Throwable t) {
                        t.getMessage();
                        Log.d( "TAG","onFailure:ngdd" + t.getMessage() );
                        Log.d( "TAG","getEmpTeam: failure" );
                    }
                } );
            } catch (Exception e) {
                System.out.println( "Exception e" + e );
            }
        } else {

            Toast.makeText( getApplicationContext(),"no internet",Toast.LENGTH_LONG ).show();

        }


    }

    public void vehicalType() {


        if (cd.isConnectingToInternet()) {
            try {
                vtype2 = new ArrayList ();
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl ).
                        client( okHttpClient ).
                        addConverterFactory( GsonConverterFactory
                                .create() ).build();
                API api = RestClient.client.create( API.class );
                Log.d( "TAG","getEmpTeam: before" );
                Call <Spinner_response> call = api.getVtype( "vehicle", Config.getCorp_code( getApplicationContext() ) );
                call.enqueue( new Callback <Spinner_response>() {
                    @Override
                    public void onResponse(Call <Spinner_response> call,Response <Spinner_response> response) {
                        spinner_response = response.body();
                        Log.d( "TAG","getEmpTeam: after" );
                        Log.d( "TAG","onResponse:data1 " + spinner_response );

                        if (spinner_response.getStatus().equalsIgnoreCase( "True" )) {
                            for (int i = 0; i < spinner_response.getData().length; i++) {
                                Log.d( "TAG","onResponse:data12345567 " + spinner_response.getData()[i].getVeh() );
                                vtype2.add(spinner_response.getData()[i].getVeh() );
                            }
                            Log.d( "TAG","onResponse: "+ vtype2.get( 0 ));
                        }
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                                getApplicationContext(),
                                android.R.layout.simple_spinner_item,
                                vtype2
                        );
                        vehicleType.setAdapter( adapter1 );

                    }

                    @Override
                    public void onFailure(Call <Spinner_response> call,Throwable t) {
                        t.getMessage();
                        Log.d( "TAG","onFailure:ngdd" + t.getMessage() );
                        Log.d( "TAG","getEmpTeam: failure" );
                    }
                } );
            } catch (Exception e) {
                System.out.println( "Exception e" + e );
            }
        } else {

            Toast.makeText( getApplicationContext(),"no internet",Toast.LENGTH_LONG ).show();

        }


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == -1) {
            Intent i=new Intent( AddEditVehicals.this,View_Vehicals.class );
            startActivity( i );
            finish();

        }
        else if (id == R.id.submit1) {

            Log.d( "TAG","onClick:kri33 " );
            if (action.equalsIgnoreCase( "add" )) {
                if (vehicleId.getText().toString().length() == 0) {
                    vehicleId.setError( "Enter VehicleId" );
                }
                else if(trackerId.getText().toString().length()==0){

                    trackerId.setError( "Enter TrackerId" );
                }
                else if(trackerPwd.getText().toString().length()==0){
                    trackerPwd.setError( "Enter Tracker Password" );
                }else if(purchaseDate.getText().toString().matches("[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}")) {

                    saveVehicle();
                }
                else{

                    purchaseDate.setError("wrong formate");




                }

            }
        }

    }


    public void saveVehicle() {

        if(cd.isConnectingToInternet()) {
            try {
                Log.d( "TAG","onResponse:kkadd " );
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().
                        baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory( GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);



                Call <Add_Employee_response> call = api.submit("save",vehicleId.getText().toString(),
                        customer1,vtype1, vehModel,trackerId.getText().toString(),trackerPwd.getText().toString(),
                        Config.getCorp_code(getApplicationContext()),nextService.getText().toString(),
                        purchaseDate.getText().toString(),Config.getUserId(getApplicationContext()));
                call.enqueue(new Callback <Add_Employee_response>() {
                    @Override
                    public void onResponse(Call<Add_Employee_response> call, Response <Add_Employee_response> response) {
                        add_employee_response = response.body();
                        Log.d( "TAG","onResponse:kkadd12 "+add_employee_response );
                        Log.d( "TAG","onResponse:kkaddstatus "+add_employee_response.getStatus() );
                        Log.d( "TAG","onResponse:kkaddmessage "+add_employee_response.getMessage() );
                        Log.d( "TAG","onResponse:kkaddmessage "+vehicleId.getText().toString());
                        Log.d( "TAG","onResponse:kkaddmessage "+customer1 );
                        Log.d( "TAG","onResponse:kkaddmessage "+vtype1+" "+purchaseDate.getText().toString());
                        Log.d( "TAG","onResponse:kkaddmessage "+vehModel);




                        if (add_employee_response.getStatus().equalsIgnoreCase("True"))
                        {

                            Toast.makeText( getApplication(),"save Successfully",Toast.LENGTH_LONG ).show();
                            Intent i=new Intent( AddEditVehicals.this,View_Vehicals.class );

                            startActivity( i );
                        }
                        else{

                            Toast.makeText(AddEditVehicals.this,"Details Incorrect",Toast.LENGTH_LONG ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Add_Employee_response> call,Throwable t) {

                        //Config.closeLoader();
                        t.getMessage();
                        Log.d( "TAG","onFailure:kk "+t.getMessage() );

                    }
                });
            } catch (Exception e) {
                System.out.println("Exception e" + e);
                Log.d( "TAG","submitDeatils:kk "+e );
            }
        }

    }

    public void getVehicleModel(String vtype1){


        Log.d( "TAG", "getVehicleModel: stringveh"+vtype1 );
        if (cd.isConnectingToInternet()) {
            try {
                model = new ArrayList ();
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl ).
                        client( okHttpClient ).
                        addConverterFactory( GsonConverterFactory
                                .create() ).build();
                API api = RestClient.client.create( API.class );
                Log.d( "TAG","getEmpTeam: before" );
                Call <Spinner_response> call = api.getVehModel( "model",vtype1, Config.getCorp_code( getApplicationContext() ) );
                call.enqueue( new Callback <Spinner_response>() {
                    @Override
                    public void onResponse(Call <Spinner_response> call,Response <Spinner_response> response) {
                        spinner_response = response.body();

                        Log.d( "TAG","onResponse:model12 " + spinner_response );

                        if (spinner_response.getStatus().equalsIgnoreCase( "True" )) {
                            for (int i = 0; i < spinner_response.getData().length; i++) {
                                Log.d( "TAG","onResponse:model " + spinner_response.getData()[i].getVehModel());
                                model.add(spinner_response.getData()[i].getVehModel() );
                            }
                            Log.d( "TAG","onResponse:model23 "+ model.get( 0 ));
                        }
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                                getApplicationContext(),
                                android.R.layout.simple_spinner_item,
                                model
                        );
                        vehicleModel.setAdapter( adapter1 );

                    }

                    @Override
                    public void onFailure(Call <Spinner_response> call,Throwable t) {
                        t.getMessage();
                        Log.d( "TAG","onFailure:ngdd" + t.getMessage() );
                        Log.d( "TAG","getEmpTeam: failure" );
                    }
                } );
            } catch (Exception e) {
                System.out.println( "Exception e" + e );
            }
        } else {

            Toast.makeText( getApplicationContext(),"no internet",Toast.LENGTH_LONG ).show();

        }


    }

}