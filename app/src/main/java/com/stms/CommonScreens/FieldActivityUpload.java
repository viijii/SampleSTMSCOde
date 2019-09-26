package com.stms.CommonScreens;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.stms.Admin.AddCustomers;
import com.stms.Admin.Admin_dashboard;
import com.stms.Admin.Admin_manageTask;
import com.stms.Admin.Admin_quotationsDashboard;
import com.stms.Admin.ViewEmployees;
import com.stms.Admin.View_Vehicals;
import com.stms.Admin.View_customers;
import com.stms.Customers.CustomerFeedbackPage;
import com.stms.Customers.CustomerHomePage;
import com.stms.Customers.Customer_dashboard;
import com.stms.Customers.ServiceCall;
import com.stms.Employess.Employee_dashboard;
import com.stms.Launch.Login;
import com.stms.R;
import com.stms.utils.Config;

public class FieldActivityUpload extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
EditText title,location;
Button image,recentposts,video;
TextView userName,userType;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("admin")) {
            setContentView(R.layout.admin_field_activity);
        }else if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("customer"))
        {
            setContentView(R.layout.customer_field_activity);
        }else{
            setContentView(R.layout.employee_field_activity);
        }




        title=findViewById(R.id.title);
        location=findViewById(R.id.location);
        image=findViewById(R.id.image);
        video=findViewById(R.id.video);
        recentposts=findViewById(R.id.recentposts);

        if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("admin")) {

        }else {
            recentposts.setVisibility(View.GONE);
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("FieldActivity");
        toolbar.setTitleTextColor(Color.WHITE);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            //closing keyboard if it is open
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        };


        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View heder=navigationView.getHeaderView( 0 );

        if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {

        }
        else if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Emp")) {
            userName = heder.findViewById(R.id.username1);
            userType = heder.findViewById(R.id.user_type1);
            userName.setText(Config.getUser_name(getApplicationContext()));
            userType.setText(Config.getLoginUserType(getApplicationContext()));
        }
        else{
            userName = heder.findViewById(R.id.username2);
            userType = heder.findViewById(R.id.user_type2);
            try {
                userName.setText(Config.getUser_name(getApplicationContext()));
                userType.setText(Config.getLoginUserType(getApplicationContext()));
            }catch (Exception e){

            }


        }


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),FieldImageUpload.class);
                intent.putExtra("title",title.getText().toString());
                intent.putExtra("location",location.getText().toString());
                startActivity(intent);
            }
        });
        recentposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),FieldActivity.class);
                startActivity(intent);
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),VideoUploading.class);
                intent.putExtra("title",title.getText().toString());
                intent.putExtra("location",location.getText().toString());
                startActivity(intent);
            }
        });
    }

   public boolean onOptionsItemSelected(MenuItem item) {


        if(toggle.onOptionsItemSelected( item )){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();

        if(id== R.id.home){

            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("customer")){
                Intent i=new Intent(getApplicationContext(), CustomerHomePage.class);
                startActivity(i);
                finish();
        }else{

            }
        }

        else if(id== R.id.mytask){

            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Emp")) {
                Intent i = new Intent(getApplicationContext(), Employee_dashboard.class);
                startActivity(i);
                finish();
            }
            else{

            }
        }
        else if(id== R.id.servicecall){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Customer")) {
                Intent i = new Intent(getApplicationContext(), ServiceCall.class);
                startActivity(i);
                finish();
            }else{}
        }
        else if(id== R.id.complaints){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Customer")) {
                Intent i = new Intent(getApplicationContext(), Customer_dashboard.class);
                startActivity(i);
                finish();
            }else{

            }
        }
        else if(id== R.id.fieldActivity){

        }
        else if(id== R.id.feedback){

            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Customer")) {
                Intent i = new Intent(getApplicationContext(), CustomerFeedbackPage.class);
                startActivity(i);
                finish();
            }else{

            }
        }
        else if(id== R.id.managetask){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {
                Intent i = new Intent(getApplicationContext(), Admin_manageTask.class);
                startActivity(i);
                finish();
            }else{}
        }
        else if(id== R.id.quotations){
            Intent i=new Intent(getApplicationContext(), Admin_quotationsDashboard.class);
            startActivity(i);
        }

        else if(id== R.id.customerNotifications){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {
                Intent i = new Intent(getApplicationContext(), Admin_dashboard.class);
                startActivity(i);
                finish();
            }else{}
        }
        else if(id== R.id.profile){

            Intent i = new Intent(getApplicationContext(), Profile.class);
            startActivity(i);
            finish();

        }
        else if(id== R.id.manageEmployees){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {
                Intent i = new Intent(getApplicationContext(), ViewEmployees.class);
                startActivity(i);
                finish();
            }else{}
        }
        else if(id== R.id.manageVehicles){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {
                Intent i = new Intent(getApplicationContext(), View_Vehicals.class);
                startActivity(i);
                finish();
            }else{}
        }
        else if(id== R.id.manageCustomers){
            if(Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {
                Intent i = new Intent(getApplicationContext(), View_customers.class);
                startActivity(i);
                finish();
            }else{}
        }

        else if(id== R.id.updates){
            Intent i=new Intent( getApplicationContext(), UpdateOffers.class );
            startActivity( i );
            finish();
        }
        else if(id== R.id.logout){
            logoutAlert();

        }
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }
    public void logoutAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder( this);


        alertDialog.setTitle("Alert!");


        alertDialog
                .setMessage("Do you want to logout?");


        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Config.saveLoginStatus(getApplication(),"0");

                        Intent i=new Intent(getApplicationContext(), Login.class);
                        startActivity(i);
                        finish();
                    }
                });


        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


        alertDialog.show();
    }
}
