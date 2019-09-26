package com.stms.CommonScreens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.stms.R;
import com.stms.Responses.CommonResponse;
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

public class CommonUpdates extends AppCompatActivity implements View.OnClickListener {

    EditText updates;
    CheckBox all, employees, customers;
    Button submit;
    CommonResponse commonResponse;
    String s="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updates_cardview);

        updates = findViewById(R.id.updates);
        all = findViewById(R.id.all);
        all.setOnClickListener(this);
        employees = findViewById(R.id.employees);
        employees.setOnClickListener(this);
        customers = findViewById(R.id.customers);
        customers.setOnClickListener(this);
        submit = findViewById(R.id.submit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Updates");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(updates.getText().toString().isEmpty()){
                    Log.d(TAG, "onClick:dis1 ");
                    updates.setError("Enter Discription");

                }

                if (s.isEmpty()) {
                    all.setError("plesse check any one of check box");
                    employees.setError("plesse check any one of check box");
                    customers.setError("plesse check any one of check box");
                }

                else {
                     Log.d(TAG, "onClick:dis2 ");
                    updateroles();

                    Intent intent=new Intent(getApplicationContext(),UpdateOffers.class);
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.all:
                if (all.isChecked())
                    s=all.getText().toString();
                    Toast.makeText(getApplicationContext(), "all", Toast.LENGTH_LONG).show();
                break;
            case R.id.employees:
                if (employees.isChecked())
                    s="Emp";
                    Toast.makeText(getApplicationContext(), "emp", Toast.LENGTH_LONG).show();
                break;
            case R.id.customers:
                if (customers.isChecked())
                    s="Customer";
                    Toast.makeText(getApplicationContext(), "customer", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public void updateroles(){
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory.create()).build();
            API api = RestClient.client.create(API.class);

            Call<CommonResponse> call = api.allUpdates("updates", updates.getText().toString(),s.toLowerCase(), Config.getCorp_code(getApplicationContext()));
            Log.d("TAG", "onResponse: rajyam1"+updates.getText().toString());
            Log.d("TAG", "onResponse: rajyam1"+s);
            call.enqueue(new Callback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                    Log.d("TAG", "onResponse: rajyam2");
                    commonResponse = response.body();
                    Log.d("TAG", "onResponse: rajyam status" + commonResponse);
                    Log.d("TAG", "onResponse: rajyam status" + commonResponse.getStatus());
                    try {
                        if (commonResponse.getStatus().equalsIgnoreCase("True")) {
                            Log.d("TAG", "onResponse: tulasi status" + commonResponse.getStatus());


                        }
                        else{


                        }

                    } catch (Exception e) {
                        Log.d("TAG", "onResponse: aaaa");
                        e.printStackTrace();

                    }
                }
                @Override
                public void onFailure(Call<CommonResponse> cal, Throwable t) {
                    Log.d("TAG", "onFailure: "+t);

                }
            });
        }
        catch (Exception e){
            Log.d(TAG, "manageemployee: "+e);
        }

    }// close cadcoins() method

}

