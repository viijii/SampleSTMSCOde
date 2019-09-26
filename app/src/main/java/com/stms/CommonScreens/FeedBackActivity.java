package com.stms.CommonScreens;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stms.Admin.Admin_manageTask;
import com.stms.Customers.CustomerFeedbackPage;
import com.stms.Customers.CustomerHomePage;
import com.stms.Customers.Customer_dashboard;
import com.stms.R;
import com.stms.Responses.FeedbackResponse;
import com.stms.utils.Config;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class FeedBackActivity extends AppCompatActivity{

    RatingBar rb;

    RadioGroup group,group1,group2;
    RadioButton yes,no,notsure,yes1,no1,notsure1,yes2,no2,notsure2;
    ArrayList<FeedbackResponse.data> data = new ArrayList<>();


    TextView value,q1,q2,q3;
    TextView id1,id2,id3;
    EditText others;
    Button  submit;
    String value1, others1,action="",taskid;
    String option1="",option2="",option3="";
    FeedbackResponse feedbackResponse;
    ProgressDialog progressDialog;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("FeedBack");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(action.equalsIgnoreCase("completed")) {
                    Intent i = new Intent(getApplicationContext(), CustomerHomePage.class);
                    startActivity(i);
                    finish();
                }
               else if(action.equalsIgnoreCase("mangeTask")) {
                    Intent i = new Intent(getApplicationContext(), Admin_manageTask.class);
                    startActivity(i);
                    finish();
                }

                else{

                    Intent i = new Intent(getApplicationContext(), CustomerFeedbackPage.class);
                    startActivity(i);
                    finish();

                }
            }
        });

        rb= findViewById(R.id.rb);
        value= findViewById(R.id.value);
        others= findViewById(R.id.others);
        submit= findViewById(R.id.submit);
        id1=findViewById(R.id.id1);
        id2=findViewById(R.id.id2);
        id3=findViewById(R.id.id3);


        group= findViewById(R.id.group);
        group1= findViewById(R.id.group1);
        group2= findViewById(R.id.group2);


        yes=findViewById(R.id.yes);
        yes1=findViewById(R.id.yes1);
        yes2=findViewById(R.id.yes2);
        no=findViewById(R.id.no);
        no1=findViewById(R.id.no1);
        no2=findViewById(R.id.no2);
        notsure=findViewById(R.id.notsure);
        notsure1=findViewById(R.id.notsure1);
        notsure2=findViewById(R.id.notsure2);

        q1=findViewById(R.id.q1);
        q2=findViewById(R.id.q2);
        q3=findViewById(R.id.q3);

        intent=getIntent();
        action=intent.getStringExtra("action");
        taskid=intent.getStringExtra("taskId");

        Log.d(TAG, "onCreate:taskidfeedback "+taskid);

        feedbackquestions();
  if(action.equalsIgnoreCase("mangeTask")) {
      submit.setVisibility(View.GONE);

      gettingfeedback(taskid);

  }




        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                value.setText("" + rating);
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                value1 = value.getText().toString();
                others1 = others.getText().toString();

                if ((value.getText().toString().length() == 0) && (others.getText().toString().length() == 0)) {
                    value.setError("Give Rating");
                    others.setError("give feedback");
                } else if(group.getCheckedRadioButtonId()<=0){
                    yes.setError("Select Item");
                    no.setError("Select Item");
                    notsure.setError("Select Item");

                }
                else if(group1.getCheckedRadioButtonId()<=0){
                    yes1.setError("Select Item");
                    no1.setError("Select Item");
                    notsure1.setError("Select Item");

                }
                else if(group2.getCheckedRadioButtonId()<=0){
                    yes2.setError("Select Item");
                    no2.setError("Select Item");
                    notsure2.setError("Select Item");

                }

                else {
                    executeSelectedMethod();
                    feedbackForm();
                }


            }





        });


    }

    public void gettingfeedback(String taskid) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory.create()).build();
            API api = RestClient.client.create(API.class);
            Call<FeedbackResponse> call = api.gettingfeedback("gettingfeedback", Config.getCorp_code(getApplicationContext()),taskid);
            call.enqueue(new Callback<FeedbackResponse>() {
                @Override
                public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                    feedbackResponse = response.body();
                    try {
                        if (feedbackResponse.getStatus().equalsIgnoreCase("True")) {
                            for (int i = 0; i < feedbackResponse.getData().length; i++) {
                                data.add(feedbackResponse.getData()[i]);
                                Log.d("TAG", "onResponse: feedback" + feedbackResponse.getData()[i].getOption());
                                Log.d(TAG, "onResponse: "+ feedbackResponse.getData()[0].getOption()+""+ feedbackResponse.getData()[1].getOption()+" "+ feedbackResponse.getData()[2].getOption());
                                Log.d("TAG", "onResponse: feedback" + feedbackResponse.getData()[i].getDescription());
                                Log.d("TAG", "onResponse: feedback" + feedbackResponse.getData()[i].getFeedbackDate());
                                Log.d("TAG", "onResponse: feedback" + feedbackResponse.getData()[i].getRating());
                                Log.d("TAG", "onResponse: feedback" + feedbackResponse.getData()[i].getTaskId());

                                value.setText(feedbackResponse.getData()[i].getRating());
                                others.setText( feedbackResponse.getData()[i].getDescription());

                                others.setFocusable(false);
                                rb.setIsIndicator(true);


                                if(feedbackResponse.getData()[0].getOption().equalsIgnoreCase("Yes")){
                                    yes.setChecked(true);




                                }else{
                                    yes.setChecked(false);
                                    yes.setEnabled(false);

                                }
                                if(feedbackResponse.getData()[1].getOption().equalsIgnoreCase("Yes")){
                                    yes1.setChecked(true);


                                }
                                else{
                                    yes1.setChecked(false);
                                    yes1.setEnabled(false);


                                }
                                if(feedbackResponse.getData()[2].getOption().equalsIgnoreCase("Yes")){
                                    yes2.setChecked(true);



                                }
                                else{
                                    yes2.setChecked(false);
                                    yes2.setEnabled(false);

                                }
                                if(feedbackResponse.getData()[0].getOption().equalsIgnoreCase("No")){
                                    no.setChecked(true);
                                }
                                else{
                                    no.setChecked(false);
                                    no.setEnabled(false);
                                }
                                if(feedbackResponse.getData()[1].getOption().equalsIgnoreCase("No")){
                                    no1.setChecked(true);
                                }
                                else{
                                    no1.setChecked(false);
                                    no1.setEnabled(false);
                                }
                                if(feedbackResponse.getData()[2].getOption().equalsIgnoreCase("No")){
                                    no2.setChecked(true);
                                }
                                else{
                                    no2.setChecked(false);
                                    no2.setEnabled(false);
                                }
                                if(feedbackResponse.getData()[0].getOption().equalsIgnoreCase("Notsure")){
                                    notsure.setChecked(true);
                                }
                                else{
                                    notsure.setChecked(false);
                                    notsure.setEnabled(false);
                                }
                                if(feedbackResponse.getData()[1].getOption().equalsIgnoreCase("Notsure")){
                                    notsure1.setChecked(true);
                                }
                                else{
                                    notsure1.setChecked(false);
                                    notsure1.setEnabled(false);
                                }
                                if(feedbackResponse.getData()[2].getOption().equalsIgnoreCase("Notsure")){
                                    notsure2.setChecked(true);
                                }
                                else{
                                    notsure2.setChecked(false);
                                    notsure2.setEnabled(false);
                                }
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();


                    }

                }

                @Override
                public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                    Log.d("TAG", "onFailure : getting"+t);

                }
            });
        }
        catch (Exception e) {
            Log.d(TAG, "manageemployee: getting" + e);
        }
    }

    public void feedbackquestions() {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                    client(okHttpClient).
                    addConverterFactory(GsonConverterFactory.create()).build();
            API api = RestClient.client.create(API.class);

            Call<FeedbackResponse> call = api.gettingquestions();

            call.enqueue(new Callback<FeedbackResponse>() {
                @Override
                public void onResponse(Call<FeedbackResponse> call, Response<FeedbackResponse> response) {
                    feedbackResponse = response.body();
                    Log.d("TAG", "onResponse: status" + feedbackResponse.getStatus());
                    try {
                        if (feedbackResponse.getStatus().equalsIgnoreCase("True")) {
                            for (int i = 0; i < feedbackResponse.getData().length; i++) {
                                data.add(feedbackResponse.getData()[i]);
                                Log.d("TAG", "onResponse: source" + feedbackResponse.getData()[i].getQuestion());

                               q1.setText(feedbackResponse.getData()[0].getQuestion());
                               q2.setText(feedbackResponse.getData()[1].getQuestion());
                                q3.setText(feedbackResponse.getData()[2].getQuestion());

                               // Log.d(TAG, "onResponse: value0000"+feedbackResponse.getData()[0].getQid()+""+feedbackResponse.getData()[1].getQid()+feedbackResponse.getData()[2].getQid());
                                Log.d(TAG, "onResponse: valiii"+feedbackResponse.getData()[0].getQid());
                                Log.d(TAG, "onResponse: valiii"+feedbackResponse.getData()[1].getQid());
                                Log.d(TAG, "onResponse: valiii"+feedbackResponse.getData()[2].getQid());

                                id1.setText(feedbackResponse.getData()[0].getQid());
                                id2.setText(feedbackResponse.getData()[1].getQid());
                                id3.setText(feedbackResponse.getData()[2].getQid());


                            }

                        }

                    } catch (Exception e) {
                        Log.d("TAG", "onResponse: aaaa");
                        e.printStackTrace();


                    }

                }

                @Override
                public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                    Log.d("TAG", "onFailure: "+t);

                }
            });
        }
        catch (Exception e) {
            Log.d(TAG, "manageemployee: " + e);
        }
    }


     public void executeSelectedMethod() {
        try {
            RadioButton rb = findViewById(group.getCheckedRadioButtonId());
            String selectbutton = rb.getText().toString();

            Log.d(TAG, "executeSelectedMethod: myans1"+selectbutton);

            if (selectbutton.equalsIgnoreCase("Yes")) {
                option1 = yes.getText().toString();
               // option1 = "yes";
                Log.d(TAG, "executeSelectedMethod: option1"+option1);

            } else if (selectbutton.equalsIgnoreCase("No")) {
                option1 = no.getText().toString();
                Log.d(TAG, "executeSelectedMethod: option1"+option1);


            } else if (selectbutton.equalsIgnoreCase("NotSure")){
                option1=notsure.getText().toString();
                Log.d(TAG, "executeSelectedMethod: option1"+option1);

            }
            RadioButton rb1 = findViewById(group1.getCheckedRadioButtonId());
            String selectedRadioButtontext = rb1.getText().toString();

            Log.d(TAG, "executeSelectedMethod:myans2"+selectedRadioButtontext);
            if (selectedRadioButtontext.equalsIgnoreCase("Yes")) {
                option2 = yes1.getText().toString();
                Log.d(TAG, "executeSelectedMethod: option2 "+option2);


            } else if (selectedRadioButtontext.equalsIgnoreCase("No")) {
                option2 = no1.getText().toString();
                Log.d(TAG, "executeSelectedMethod: option2"+option2);

            } else if (selectedRadioButtontext.equalsIgnoreCase("NotSure")){
                option2=notsure1.getText().toString();
                Log.d(TAG, "executeSelectedMethod: option2"+option2);

            }
            RadioButton rb2 = findViewById(group2.getCheckedRadioButtonId());
            String selectedRadioButtontext1 = rb2.getText().toString();

            Log.d(TAG, "executeSelectedMethod: myans3"+selectedRadioButtontext1);

            if (selectedRadioButtontext1.equalsIgnoreCase("Yes")) {
                option3 = yes2.getText().toString();
                Log.d(TAG, "executeSelectedMethod: option3"+option3);


            } else if (selectedRadioButtontext1.equalsIgnoreCase("No")) {
                option3 = no2.getText().toString();
                Log.d(TAG, "executeSelectedMethod: option3"+option3);

            } else if (selectedRadioButtontext1.equalsIgnoreCase("NotSure")){
                option3=notsure2.getText().toString();
                Log.d(TAG, "executeSelectedMethod: option3"+option3);

            }
        }
        catch (Exception e) {
            System.out.print("Exception e" + e);
        }        }

    public void feedbackForm() {
        Log.d(TAG, "feedbackForm: methodclicked");
        progressDialog = ProgressDialog.show(this, "","Please Wait...", true);


        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).client(okHttpClient).
                    addConverterFactory(GsonConverterFactory.create()).build();
            API api = RestClient.client.create(API.class);



            Log.d(TAG, "feedbackFormbutton:idd "+taskid);
            Log.d(TAG, "feedbackForm: id1"+id1.getText().toString());
            Log.d(TAG, "feedbackForm: id2"+id2.getText().toString());
            Log.d(TAG, "feedbackForm: id3"+id3.getText().toString());

            Log.d(TAG, "executeSelectedMethod: option11"+option1);
            Log.d(TAG, "feedbackForm: op2"+option2);
            Log.d(TAG, "feedbackForm: op3"+option3);



            Call<FeedbackResponse> call = api.feedbackForm("feedback",taskid,value1, others1,option1,option2,option3,id1.getText().toString(),id2.getText().toString(),id3.getText().toString(), Config.getCorp_code(getApplicationContext()));


            call.enqueue(new Callback<FeedbackResponse>() {
                @Override
                public void onResponse(Call<FeedbackResponse> call,
                                       Response<FeedbackResponse> response) {
                    feedbackResponse = response.body();
                    try {
                        Log.d("TAG", "onResponse:responsefeedback"+feedbackResponse);
                        if (feedbackResponse.getStatus().equalsIgnoreCase("True")) {
                            /*Log.d("TAG", "onResponse: responsessss"+feedbackResponse.getStatus());
                            for (int i = 0; i < feedbackResponse.getData().length; i++) {
                                data.add(feedbackResponse.getData()[i]);

                            }
*/
                            if(action.equalsIgnoreCase("completed")) {
                                Toast.makeText(FeedBackActivity.this, "Thanks for Your FeedBack", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Customer_dashboard.class);
                                startActivity(i);
                            }
                            else{

                                Toast.makeText(FeedBackActivity.this, "Thanks for Your FeedBack", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), CustomerFeedbackPage.class);
                                startActivity(i);

                            }
                        }

                    } catch (Exception e) {

                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<FeedbackResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("TAG", "feedbackForm: hhhh"+t);
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            System.out.print("Exception e" + e);
        }
    }

}
