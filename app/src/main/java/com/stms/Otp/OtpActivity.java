package com.stms.Otp;/*
package com.stms.Otp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stms.R;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OtpActivity extends AppCompatActivity {

    private static final String TAG ="1827" ;
    EditText mobileNum;
    Button b1;
    String num;
    otpRes res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_otp);

        mobileNum = (EditText) findViewById(R.id.editText);
        b1=findViewById( R.id.b1 );

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mobileNum.length() != 10) {
                    mobileNum.setError("please enter valid phone number");
                }
                else{
             getOtp();
                    Intent i = new Intent(getApplicationContext(), ValidateOTP.class);
                    startActivity(i);
                }


            }

        });



    }

    public void getOtp() {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory( GsonConverterFactory
                            .create()).build();
            API api = RestClient.client.create(API.class);

           */
/* Log.d("TAG", "onClick: " + mobileNum.getText().toString());

            num = mobileNum.getText().toString();
*//*

         //   Config.saveNumber(getApplication(),mobileNum.getText().toString());


            Log.d("TAG", "onClick: " + num);
            Call<otpRes> call = api.generateOTP(num);
            call.enqueue(new Callback<otpRes>() {
                @Override
                public void onResponse(Call<otpRes> call,
                                       Response<otpRes> response) {

                    res = new otpRes();
                    res = response.body();
                    if(res.getStatus().equalsIgnoreCase("true")){
                        Toast.makeText(OtpActivity.this, "OTP has been sent", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(OtpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<otpRes> call, Throwable t) {


                    Log.d(" TAG", "onFailure:fail" + t);
                    //   Log.d(" TAG", "onFailure:fail1" + res.getStatus());
                }


            });


        } catch (Exception e) {
            System.out.print("Exception e" + e);

        }


    }



}
*/
