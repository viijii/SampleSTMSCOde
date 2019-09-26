package com.stms.Launch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stms.Admin.Admin_dashboard;
import com.stms.Customers.CustomerHomePage;
import com.stms.Employess.Employee_dashboard;
import com.stms.R;
import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;

public class Sramproducts extends AppCompatActivity {

    String login_status = "",userType = "";
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sramproducts);


        try {
            cd = new ConnectionDetector(Sramproducts.this);

            login_status = Config.getLoginStatus(Sramproducts.this);
            userType = Config.getLoginUserType(Sramproducts.this);

            if (login_status.equalsIgnoreCase("Yes")) {


                if (userType.equalsIgnoreCase("Admin")) {
                    Intent i = new Intent(Sramproducts.this, Admin_dashboard.class);
                    startActivity(i);
                    finish();
                } else if (userType.equalsIgnoreCase("Emp") || userType.equalsIgnoreCase("Manager") || userType.equalsIgnoreCase("Director")) {
                    Intent i = new Intent(Sramproducts.this, Employee_dashboard.class);
                    startActivity(i);
                    finish();
                } else if (userType.equalsIgnoreCase("Customer")) {
                    Intent i = new Intent(Sramproducts.this, CustomerHomePage.class);
                    startActivity(i);
                    finish();
                }
            } else {
                Intent i = new Intent(Sramproducts.this, Login.class);
                startActivity(i);
                finish();
            }

        }catch (Exception e){

        }

    }
}
