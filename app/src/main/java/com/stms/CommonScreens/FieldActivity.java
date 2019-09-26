package com.stms.CommonScreens;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.stms.Adapters.FieldActivityAdapter;
import com.stms.R;
import com.stms.Responses.FieldActivityResponse;
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

public class FieldActivity extends AppCompatActivity {
RecyclerView listview;
ConnectionDetector cd;
FieldActivityResponse fieldActivityResponse;
ArrayList<FieldActivityResponse.data> data;
FieldActivityAdapter fieldActivityAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_field);
        listview=findViewById(R.id.listview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(FieldActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(layoutManager);
        listview.setHasFixedSize(true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("FieldActivity Recent Posts");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        cd = new ConnectionDetector(getApplicationContext());
        getVideoPath();

    }

    public void getVideoPath() {
        Log.d("TAG", "getVideoPath: llll");
        try {
            if (cd.isConnectingToInternet()) {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<FieldActivityResponse> call = api.getFields(Config.getCorp_code(getApplicationContext()));
                call.enqueue(new Callback<FieldActivityResponse>() {
                    @Override
                    public void onResponse(Call<FieldActivityResponse> call,
                                           Response<FieldActivityResponse> response) {
                      //  Config.closeLoader();
                        fieldActivityResponse = response.body();
                        data = new ArrayList<FieldActivityResponse.data>();

                        if (fieldActivityResponse.getStatus().equalsIgnoreCase("true")) {

                            if(fieldActivityResponse.getData()==null){
                                Toast.makeText(getApplicationContext(),"No Data Getting",Toast.LENGTH_LONG).show();
                            }else {

                                for (int i = 0; i < fieldActivityResponse.getData().length; i++) {
                                  data.add(fieldActivityResponse.getData()[i]);
                                    Log.d("TAG", "onResponse:completed " + fieldActivityResponse.getData()[i]);
                                    Log.d("TAG", "onResponse:completed" + fieldActivityResponse.getData()[i].getTitle());
                                    Log.d("TAG", "onResponse:completed" + fieldActivityResponse.getData()[i].getLocation());
                                    Log.d("TAG", "onResponse:completed" + fieldActivityResponse.getData()[i].getPath());
                                }
                            }
                        }

                        setListview();
                    }

                    @Override
                    public void onFailure(Call<FieldActivityResponse> call, Throwable t) {
                        Config.closeLoader();
                        t.getMessage();
                        Toast.makeText(
                                getApplicationContext(), "Try Again!", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(
                        getApplicationContext(),
                        "Sorry not connected to internet",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            System.out.println("Exception e" + e);
        }
    }
    public void setListview()
    {
        fieldActivityAdapter = new FieldActivityAdapter(getApplicationContext(),data,FieldActivity.this);
        listview.setAdapter(fieldActivityAdapter);
    }

}
