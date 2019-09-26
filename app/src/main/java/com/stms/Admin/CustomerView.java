package com.stms.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stms.Adapters.CustomerAdapter;
import com.stms.Adapters.ViewCustomerAdapter;
import com.stms.R;
import com.stms.Responses.ViewCustomerResponse;
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

public class CustomerView extends AppCompatActivity {


    RecyclerView recyclerView;
    ViewCustomerAdapter adapter;
    ViewCustomerResponse customerresponse;
    ArrayList<ViewCustomerResponse.data> data;
    TextView name,phone,city,aadhar,email,permanentaddress,currentaddress,username,password;
    Intent intent;

    String cId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Customer View");

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        name= findViewById(R.id.name);
        phone= findViewById(R.id.phone);
        city= findViewById(R.id.city);
        email= findViewById(R.id.email);
        aadhar= findViewById(R.id.aadhar);
        permanentaddress= findViewById(R.id.permanentaddress);
        currentaddress= findViewById(R.id.currentaddress);
        username= findViewById(R.id.username);
        password= findViewById(R.id.password);
        intent=getIntent();
        cId=getIntent().getExtras().getString("cId");


        data=new ArrayList<ViewCustomerResponse.data>();
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setHasFixedSize(true);
        customer();
    }
    public void customer(){

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory( GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            Call<ViewCustomerResponse> call = api.customerview(Config.getCorp_code(getApplicationContext()),cId);
            Log.d("TAG", "metro: hhhhhhhhhhh");
            call.enqueue(new Callback<ViewCustomerResponse>() {
                @Override
                public void onResponse(Call<ViewCustomerResponse> call, Response<ViewCustomerResponse> response) {
                    customerresponse = response.body();
                    Log.d("TAG", "onResponse: "+customerresponse);
                    try {
                        if (customerresponse.getStatus().equalsIgnoreCase("true")) {
                            Log.d("TAG", "onResponse1:"+customerresponse.getStatus()+" "+customerresponse.getData()[0].getName());

                            name.setText(customerresponse.getData()[0].getName());
                            email.setText(customerresponse.getData()[0].getEmail());
                            phone.setText(customerresponse.getData()[0].getMobileNumber());
                            aadhar.setText(customerresponse.getData()[0].getAadhar());
                            permanentaddress.setText(customerresponse.getData()[0].getPermanentAddress());
                            currentaddress.setText(customerresponse.getData()[0].getCurrentAddress());
                            username.setText(customerresponse.getData()[0].getUsername());
                            password.setText(customerresponse.getData()[0].getPassword());
                            city.setText(customerresponse.getData()[0].getCity());
                            for (int i = 0; i < customerresponse.getData().length; i++) {
                                Log.d("TAG", "onResponse111: "+customerresponse.getData()[i].getVehicleType());
                                data.add(customerresponse.getData()[i]);

                                //  Log.d("TAG", "onResponse: 1234"+customerresponse.getData()[i].getJunctions());

                            }
                        }
                        setListView();

                    } //try
                    catch (Exception e) {
                        Log.d("TAG", "onResponse: aaaa"+e);
                        e.printStackTrace();
                    }//catch
                }

                @Override
                public void onFailure(Call<ViewCustomerResponse> call, Throwable t) {

                }


            });
        }//try
        catch (Exception e){
            Toast.makeText(this,"no data found",Toast.LENGTH_SHORT).show();
        }//catch
    }
    public  void setListView(){
        adapter=new ViewCustomerAdapter(data,CustomerView.this, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}