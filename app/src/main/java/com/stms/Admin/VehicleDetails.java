package com.stms.Admin;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.stms.R;
import com.stms.Responses.view_VehicleResponse;
import com.stms.utils.Config;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class VehicleDetails extends AppCompatActivity {

     TextView vehicleno,customerinfo,vehtype,trackingid,trackingpassword,mobilenumber,purchasedate,nextservice,noofservices;
    view_VehicleResponse getInHistoryResponse;
    String customerId,action;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicledetails);

        vehicleno=findViewById(R.id.vehicleno);
        customerinfo=findViewById(R.id.customerinfo);
        vehtype=findViewById(R.id.vehtype);
        trackingid=findViewById(R.id.trackingid);
        trackingpassword=findViewById(R.id.trackingpassword);
        mobilenumber=findViewById(R.id.mobilenumber);
        purchasedate=findViewById(R.id.purchasedate);
        nextservice=findViewById(R.id.nextservice);
        noofservices=findViewById(R.id.noofservices);



        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Vehicle View");
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        intent=getIntent();
        action=getIntent().getExtras().getString("action");
        customerId=getIntent().getExtras().getString("eId");

        manageemployee();


    }


    public  void  manageemployee()
    {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory.create()).build();
            API api = RestClient.client.create(API.class);
            Log.d("TAG", "manageemployee:customerId " + customerId);

            Call<view_VehicleResponse> call = api.manageemployee(customerId, Config.getCorp_code(getApplicationContext()));

            call.enqueue(new Callback<view_VehicleResponse>() {
                @Override
                public void onResponse(Call<view_VehicleResponse> call, Response<view_VehicleResponse> response) {
                    getInHistoryResponse = response.body();
                    Log.d("TAG", "onResponse: status" + getInHistoryResponse.getStatus());

                    Log.d("TAG", "onResponse: data" + getInHistoryResponse.getData()[0]);

                    Log.d("TAG", "onResponse: id" + getInHistoryResponse.getData()[0].getTrackerid());
                    Log.d("TAG", "onResponse: no" + getInHistoryResponse.getData()[0].getMobilenumber());
                    Log.d("TAG", "onResponse: city" + getInHistoryResponse.getData()[0].getCity());

                    try {
                        if (getInHistoryResponse.getStatus().equalsIgnoreCase("True")) {
                            trackingid.setText(getInHistoryResponse.getData()[0].getTrackerid());
                            trackingpassword.setText(getInHistoryResponse.getData()[0].getTrackerpassword());
                            vehicleno.setText(getInHistoryResponse.getData()[0].getVehicleid());
                            customerinfo.setText(getInHistoryResponse.getData()[0].getCustomername());
                            purchasedate.setText(getInHistoryResponse.getData()[0].getPurchasedate());
                            vehtype.setText(getInHistoryResponse.getData()[0].getVehicletype());
                            mobilenumber.setText(getInHistoryResponse.getData()[0].getMobilenumber());
                            noofservices.setText(getInHistoryResponse.getData()[0].getNoofservices());
                        }

                    } catch (Exception e) {
                        Log.d("TAG", "onResponse: aaaa");
                        e.printStackTrace();


                    }

                }

                @Override
                public void onFailure(Call<view_VehicleResponse> call, Throwable t) {
                    Log.d("TAG", "onFailure: "+t);

                }
            });
        }
        catch (Exception e){
            Log.d(TAG, "manageemployee: "+e);
        }

    }// close cadcoins() method

}
