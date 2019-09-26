package com.stms.Launch;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.stms.Admin.Admin_dashboard;
import com.stms.Customers.CustomerHomePage;
import com.stms.Customers.Customer_dashboard;
import com.stms.Employess.Employee_dashboard;
import com.stms.R;
import com.stms.Responses.LoginResponse;
import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;


import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.Manifest.permission.ANSWER_PHONE_CALLS;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission_group.CAMERA;

public class Login extends AppCompatActivity implements View.OnClickListener {

       String version_Name;
  String playStoreVersion;
    RadioGroup user_type;

    Button login;

    EditText username, password,corp_code;
    RadioButton user, admin,customer;
    String utype = "";
    ConnectionDetector cd;
    LoginResponse loginResponse;


    static final int RequestPermissionCode = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        PackageManager manager = this.getPackageManager();//pplay store
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
          version_Name = info.versionName;//ppplaystore

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 10);
        }

        RequestMultiplePermission();

        login = findViewById(R.id.login);
        corp_code=findViewById(R.id.cc);

        user = findViewById(R.id.user);
        admin = findViewById(R.id.admin);
        customer= findViewById( R.id.customer );

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        cd = new ConnectionDetector(Login.this);


        login.setOnClickListener(this);
        user.setOnClickListener(this);
        admin.setOnClickListener(this);
        customer.setOnClickListener(this);
    }

    /*

    this method is used to request to the user , to give the user perimissioin to access the call,camera,read_contacts etc....

    */
    public void onBackPressed() {

       Intent intent=new Intent( Intent.ACTION_MAIN );
       intent.addCategory( Intent.CATEGORY_HOME );
       intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
       startActivity( intent );

        }

    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(Login.this, new String[]
                {
                        CAMERA,
                        READ_CONTACTS,
                        ANSWER_PHONE_CALLS
                }, RequestPermissionCode);

    }

    // Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean Contacts = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean Calls = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    //         boolean GetAccountsPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && Contacts && Calls) {

                        Toast.makeText(Login.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        // Toast.makeText(Login.this, "Permission Denied", Toast.LENGTH_LONG).show();

                    }
                }

                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            if (username.getText().length() == 0) {
                username.setError("Enter Username");
            } else if (password.getText().length() == 0) {
                password.setError("Enter Password");
            } else {
                if (utype.equalsIgnoreCase("Admin")) {
                    login();

                } else if (utype.equalsIgnoreCase("Emp")) {
                    login();

                }else if (utype.equalsIgnoreCase("Customer")) {
                    login();
                }
                else {
                    Config.showToast(Login.this, "Select User Type");
                }

            }

        } else if (v.getId() == R.id.admin) {

            utype = "Admin";

        } else if (v.getId() == R.id.user) {

            utype = "Emp";

        }
        else if (v.getId() == R.id.customer) {

            utype = "Customer";

        }
    }

    public void login() {
        Log.d( "TAG", "adminLogin:role"+utype );
        try {
            if (cd.isConnectingToInternet()) {

                Log.d( "TAG", "adminLogin:try " );
                Config.showLoader(Login.this);
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Log.d( "TAG", "adminLogin: first" );

                Call<LoginResponse> call = api.login(username.getText().toString(),
                        password.getText().toString(),corp_code.getText().toString().toLowerCase(),utype.toLowerCase()
                );

                call.enqueue(new Callback<LoginResponse>() {


                    @Override
                    public void onResponse(Call<LoginResponse> call,
                                           Response<LoginResponse> response) {
                        Config.closeLoader();
                        Log.d( "TAG", "onResponse:response " );

                        loginResponse = response.body();
                        Log.d( "TAG", "onRespon:staus"+loginResponse.getStatus() );
                        Log.d( "TAG", "onRespon:role"+loginResponse.getRole() );

                        if (loginResponse.getStatus().equalsIgnoreCase("True")) {

                            Config.saveLoginStatus(Login.this, "Yes");
                            Config.saveLoginUserType(Login.this, loginResponse.getRole().toLowerCase());
                            Config.saveUserId(Login.this, loginResponse.getId());
                            Config.saveCorp_code(Login.this, corp_code.getText().toString().toLowerCase());
                            Config.saveUser_name(Login.this, loginResponse.getUsername());
                            Config.saveRole(Login.this,utype.toLowerCase());


                            Log.d("TAG", "onCreate:second");

                            if (utype.equalsIgnoreCase("Admin")) {
                                Intent i = new Intent(Login.this, Admin_dashboard.class);
                                startActivity(i);


                            } else if (utype.equalsIgnoreCase("Emp")) {
                                Intent i = new Intent(Login.this, Employee_dashboard.class);
                                startActivity(i);
                            } else if (utype.equalsIgnoreCase("Customer")) {
                                Log.d("TAG", "onCreate: CCCCCCCCCCCCCC");
                                Intent i = new Intent(Login.this, CustomerHomePage.class);
                                startActivity(i);
                            } else {

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Config.closeLoader();

                        t.getMessage();
                        Toast.makeText(Login.this,"Try Again php not !",
                                Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(Login.this,"Sorry not connected to internet",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            System.out.println("Exception e" + e);
        }
    }

}

