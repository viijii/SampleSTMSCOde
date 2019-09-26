package com.stms.CommonScreens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.Firebase;
import com.stms.Admin.Admin_manageTask;
import com.stms.Employess.Employee_dashboard;
import com.stms.R;
import com.stms.Responses.CommonResponse;
import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TaskChat extends AppCompatActivity {

    private static final String TAG = "MyFirebaseMsgService";
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1;

 ConnectionDetector cd;


    String task_id,assigned_by,assigned_to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_task_chat );


        layout = findViewById( R.id.layout1 );
        layout_2 = findViewById( R.id.layout2 );
        sendButton = findViewById( R.id.sendButton );
        messageArea = findViewById( R.id.messageArea );
        scrollView = findViewById( R.id.scrollView );
        Toolbar toolbar = findViewById(R.id.toolbar);

        final String username2= Config.getUser_name(getApplicationContext());
        final String crop= Config.getCorp_code(getApplicationContext());
          cd=new ConnectionDetector(getApplicationContext());


        Intent intent = getIntent();
        task_id=intent.getStringExtra("task_id");
        assigned_by=intent.getStringExtra("assign_by");
        assigned_to=intent.getStringExtra("assign_to");


        Log.d(TAG, "onCreate:by"+assigned_by);
        Log.d(TAG, "onCreate:to"+assigned_to);
        Log.d(TAG, "onCreate:id"+task_id);



        toolbar.setTitle(assigned_to);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Config.getLoginUserType(getApplicationContext()).equalsIgnoreCase("Admin")) {

                    Intent intent = new Intent(getApplicationContext(), Admin_manageTask.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else{

                        Intent intent = new Intent(getApplicationContext(), Employee_dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }

            }
        });


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( getApplicationContext() );
        final String time2=sharedPreferences.getString( "time","" );



        Firebase.setAndroidContext( this );
        reference1 = new Firebase( "https://qptms-7be31.firebaseio.com/" +task_id+"_"+crop);

        sendButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String messageText = messageArea.getText().toString();

                if (!messageText.equals( "" )) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put( "message",messageText );
                    map.put( "user",assigned_by );
                    map.put("time",time2);
                    map.put( "senduser",username2 );
                    reference1.push().setValue( map );
                    //reference2.push().setValue( map );
                    messageArea.setText( "" );
                    taskChatCount(assigned_by,assigned_to,crop,task_id);

                }

            }
        });
        reference1.addChildEventListener( new com.firebase.client.ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {



                Map<String, String> map  = (Map) dataSnapshot.getValue(Map.class);
                if (map != null) {
                    String message = map.get( "message" );
                    String userName = map.get( "user" );
                    String username2=map.get( "senduser" );
                    Log.d( TAG,"onChildAdded:"+username2 );
                    String time = map.get( "time" );


                    if (userName.equals( assigned_by

                    )) {
                        addMessageBox( username2+"\n" + message+"\n"+time,1 );
                    } else {
                        addMessageBox( username2+"\n" + message+"\n"+time,2 );
                    }

                }

            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(com.firebase.client.FirebaseError firebaseError) {

            }
        } );
    }

    public  void taskChatCount(String assigned_by, String assigned_to, String crop, String task_id) {

        if(cd.isConnectingToInternet()) {
            try {

                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl ).
                        client( okHttpClient ).
                        addConverterFactory( GsonConverterFactory
                                .create() ).build();
                API api = RestClient.client.create( API.class );
                Call<CommonResponse> call = api.TaskchatCount( assigned_by, assigned_to, crop,task_id );
                call.enqueue( new Callback<CommonResponse>() {
                    @Override
                    public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<CommonResponse> call, Throwable t) {

                    }
                } );
            }catch (Exception e){

                Log.d( "TAG", "chatCount: "+e.getMessage() );
            }
        }
        else{

            Toast.makeText(getApplicationContext(),"no internet", Toast.LENGTH_LONG).show();
        }

    }


    private String getDate(Long time) {
        Long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date netDate = (new Date(timeStamp));
        return sdf.format(netDate);
    }




    @SuppressLint({"NewApi","SetTextI18n"})
    public void addMessageBox(String message, int type){

        Long timeStamp = System.currentTimeMillis();

        TextView textView = new TextView(TaskChat.this);
        Log.d( TAG,"addMessageBox: time"+getDate( timeStamp ) );

        final String time=getDate( timeStamp );
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("time",time);
        editor.commit();

        textView.setText("\n"+message+"\n");


        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;

            textView.setTextColor( Color.BLACK);
            textView.setCompoundDrawablePadding( 10 );

        }
        else{
            lp2.gravity = Gravity.RIGHT;

            textView.setTextColor( Color.BLUE );
            textView.setCompoundDrawablePadding( 10 );
            lp2.setMarginEnd(20);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
