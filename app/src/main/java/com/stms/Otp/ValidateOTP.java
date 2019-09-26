package com.stms.Otp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stms.Employess.Employee_dashboard;
import com.stms.R;

import com.stms.Responses.otpRes;
import com.stms.Responses.otpValRes;
import com.stms.utils.Config;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ValidateOTP extends AppCompatActivity {

    private static final String TAG ="243" ;
    EditText ed1,ed2,ed3,ed4, ed5;
    TextView resend;
    Button b1;
    otpValRes valres;
    otpRes res;
    String ideaId,taskId,completed;
    int position;
    public static String status=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validateotp);


        resend = findViewById( R.id.resend);
        b1= findViewById(R.id.submit);


        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        ed3 = findViewById(R.id.ed3);
        ed4 = findViewById(R.id.ed4);
        ed5 = findViewById(R.id.ed5);
        ed5.setVisibility( View.GONE);
        Intent i=getIntent();
       ideaId= i.getStringExtra("idea" );
        taskId= i.getStringExtra("task" );
        position=i.getIntExtra( "position",0 );
        completed=i.getStringExtra( "completed" );

        Log.d( TAG, "onCreate:jagggg"+position );
        ed1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(ed1.getText().toString().length()==1)     //size as per your requirement
                {
                    ed2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        ed2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(ed2.getText().toString().length()==1)     //size as per your requirement
                {
                    ed3.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        ed3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(ed3.getText().toString().length()==1)     //size as per your requirement
                {
                    ed4.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        ed4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(ed4.getText().toString().length()==1)     //size as per your requirement
                {
                    ed5.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                valOtp();

            }
        });


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOtp();
            }
        });

    }


    public void getOtp() {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory( GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            Call<otpRes> call = api.generateOTP(ideaId,taskId, Config.getUserId( getApplicationContext() ), Config.getCorp_code( getApplicationContext() ) );
            call.enqueue(new Callback<otpRes>() {
                @Override
                public void onResponse(Call<otpRes> call,
                                       Response<otpRes> response) {

                   // res = new otpRes();
                    res = response.body();
                    Log.d( TAG, "onResponse: response1"+res.getStatus() );
                    Log.d( TAG, "onResponse: response"+res.getResponse() );
                    if(res.getStatus().equalsIgnoreCase("true")){
                        Toast.makeText(ValidateOTP.this, "OTP has been sent", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(ValidateOTP.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<otpRes> call, Throwable t) {


                    Log.d(" TAG", "onFailure:fail" + t);

                }


            });


        } catch (Exception e) {
            System.out.print("Exception e" + e);

        }


    }


    public void valOtp(){
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

            String otp;
            otp = ed1.getText().toString()+ed2.getText().toString()+ed3.getText().toString()+ed4.getText().toString();
            Log.d(TAG, "onClick:1 "+otp);

            Call<otpValRes> call = api.validateOTP(completed,ideaId,taskId, otp, Config.getCorp_code( getApplicationContext() ));
            call.enqueue(new Callback<otpValRes>() {
                @Override
                public void onResponse(Call<otpValRes> call,
                                       Response<otpValRes> response) {

                    valres = new otpValRes();
                    valres = response.body();
                    if(valres.getStatus().equalsIgnoreCase("true")){
                        Toast.makeText(ValidateOTP.this, "success", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("otp","otp");
                        editor.putInt("position233",position);
                        Log.d( TAG, "onResponse: kk"+position );
                        editor.commit();

                        if(completed.equalsIgnoreCase( "completed" )){
                          update(completed,taskId);

                        }
                   else {
                            Intent i = new Intent( getApplicationContext(), Employee_dashboard.class );

                            startActivity( i );
                        }

                    }else{
                        Toast.makeText(ValidateOTP.this, "failed", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<otpValRes> call, Throwable t) {


                    Log.d(" TAG", "onFailure:fail" + t);

                }


            });


        } catch (Exception e) {
            System.out.print("Exception e" + e);


        }

    }

    public void update(String completed, String taskId) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);
            Call<otpValRes> call = api.updateOTP(taskId, Config.getCorp_code( getApplicationContext() ));
            call.enqueue(new Callback<otpValRes>() {
                @Override
                public void onResponse(Call<otpValRes> call,
                                       Response<otpValRes> response) {

                    valres = new otpValRes();
                    valres = response.body();
                    if (valres.getStatus().equalsIgnoreCase( "true" )) {
                        Toast.makeText( ValidateOTP.this, "success", Toast.LENGTH_SHORT ).show();

                        Intent i = new Intent( getApplicationContext(), Employee_dashboard.class );

                        startActivity( i );
                    } else {
                        Toast.makeText( ValidateOTP.this, "failed", Toast.LENGTH_SHORT ).show();
                    }

                }

                @Override
                public void onFailure(Call<otpValRes> call, Throwable t) {


                    Log.d(" TAG", "onFailure:fail" + t);
                    //   Log.d(" TAG", "onFailure:fail1" + res.getStatus());
                }


            });


        } catch (Exception e) {
            System.out.print("Exception e" + e);


        }



    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }
}
