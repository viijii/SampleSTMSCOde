package com.stms.Launch;

import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.stms.Admin.Admin_dashboard;
import com.stms.Customers.CustomerHomePage;
import com.stms.Customers.Customer_dashboard;
import com.stms.Employess.Employee_dashboard;
import com.stms.R;

import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;
import io.fabric.sdk.android.Fabric;



public class Splash extends AppCompatActivity
{

    int SPLASH_TIME_OUT = 2500;
    public static String EXCEPTION_MESS;
    String login_status = "",userType = "";
    ConnectionDetector cd;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);





        try
        {
            cd = new ConnectionDetector(Splash.this);

            login_status = Config.getLoginStatus(Splash.this);
            userType = Config.getLoginUserType(Splash.this);

            String android_id = Settings.Secure.getString(Splash.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            Log.e("android_id", android_id);

            new Handler().postDelayed(new Runnable()
            {
                public void run() {
                    if (login_status.equalsIgnoreCase("Yes")) {


                        if (userType.equalsIgnoreCase("Admin")) {
                            Intent i = new Intent(Splash.this, Admin_dashboard.class);
                            startActivity(i);
                            finish();
                        } else if (userType.equalsIgnoreCase("Emp") || userType.equalsIgnoreCase("Manager") || userType.equalsIgnoreCase("Director")) {
                            Intent i = new Intent(Splash.this, Employee_dashboard.class);
                            startActivity(i);
                            finish();
                        } else if (userType.equalsIgnoreCase("Customer")) {
                            Intent i = new Intent(Splash.this, CustomerHomePage.class);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Intent i = new Intent(Splash.this, Login.class);
                        startActivity(i);
                        finish();
                    }
                    /*Intent i = new Intent(Splash.this, Sramproducts.class);
                    startActivity(i);
                    finish();*/


                }


            }, SPLASH_TIME_OUT);


        }
        catch(Exception e)
        {
            Log.e(EXCEPTION_MESS,"Exception in Splash");
        }
    }



}
