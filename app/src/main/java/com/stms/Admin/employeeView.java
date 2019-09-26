package com.stms.Admin;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

public class employeeView extends AppCompatActivity {
    TextView name,employeeid,phone,city,aadhar,email,currentaddress,perminantaddress,joiningdate,username,password;
    view_VehicleResponse getInHistoryResponse;
    Intent intent;
    String eid,action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_view);

        name=findViewById(R.id.name);
        employeeid=findViewById(R.id.employeeid);
        phone=findViewById(R.id.phone);
        city=findViewById(R.id.city);
        aadhar=findViewById(R.id.aadhar);
        email=findViewById(R.id.email);
        currentaddress=findViewById(R.id.currentaddress);
        perminantaddress=findViewById(R.id.perminantaddress);
        joiningdate=findViewById(R.id.joiningdate);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Employee View");
        toolbar.setTitleTextColor(Color.WHITE);
        intent=getIntent();
        action=getIntent().getExtras().getString("action");
        eid=getIntent().getExtras().getString("eId");


        employeeView();

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }

    public  void  employeeView()
    {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory.create()).build();
            API api = RestClient.client.create(API.class);

            Call<view_VehicleResponse> call = api.employeeview(eid, Config.getCorp_code(getApplicationContext()));

            call.enqueue(new Callback<view_VehicleResponse>() {
                @Override
                public void onResponse(Call<view_VehicleResponse> call, Response<view_VehicleResponse> response) {
                    getInHistoryResponse = response.body();
                    Log.d("TAG", "onResponse: status" + getInHistoryResponse.getStatus());
                    try {
                        if (getInHistoryResponse.getStatus().equalsIgnoreCase("True")) {
                          name.setText(getInHistoryResponse.getData()[0].getName());
                          email.setText(getInHistoryResponse.getData()[0].getEmail());
                          phone.setText(getInHistoryResponse.getData()[0].getMobilenumber());
                          employeeid.setText(getInHistoryResponse.getData()[0].getEmpid());
                            city.setText(getInHistoryResponse.getData()[0].getCity());
                            aadhar.setText(getInHistoryResponse.getData()[0].getAadhar());
                            joiningdate.setText(getInHistoryResponse.getData()[0].getJoiningdate());
                            currentaddress.setText(getInHistoryResponse.getData()[0].getCurrentaddress());
                            perminantaddress.setText(getInHistoryResponse.getData()[0].getPermanentaddress());
                            username.setText(getInHistoryResponse.getData()[0].getUsername());
                            password.setText(getInHistoryResponse.getData()[0].getPassword());


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
