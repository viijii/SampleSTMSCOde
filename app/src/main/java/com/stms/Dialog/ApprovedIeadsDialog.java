package com.stms.Dialog;


import android.app.Dialog;
import android.app.TimePickerDialog;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.util.CrashUtils;
import com.stms.R;
import com.stms.Responses.AdminApprovedSpinnerDialogResponse;
import com.stms.Responses.ManageTaskPendingResponse;
import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class ApprovedIeadsDialog extends DialogFragment implements View.OnClickListener {

    View view;

    String spinnerId;
    EditText takeinstruments,title,Estimateddays;

    Spinner spinner;
    ArrayList<String> data = new ArrayList<>();
    List<AdminApprovedSpinnerDialogResponse.data> spinnerlist;

    ConnectionDetector cd;
    Button submit;
    String []  split;
    AdminApprovedSpinnerDialogResponse adminApprovedSpinnerDialogResponse;
    Bundle bundle;
    String complaintId;

    ManageTaskPendingResponse manageTaskPendingResponse;

    String taskId,action="",empId="";

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(false);
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu( true );
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.admin_approved_ideas_dialog, container, false );

        bundle=getArguments();
        complaintId=bundle.getString("complaintId");
        taskId=bundle.getString("taskId");
        action =bundle.getString("action");




        Log.d( TAG, "onCreateView: modify"+action );
        Log.d( TAG, "onCreateView: complintid"+complaintId );



        Log.d(TAG, "onCreateView: "+taskId);

        title = view.findViewById( R.id.title );


        takeinstruments = view.findViewById( R.id.takeinstruments );
        title=view.findViewById(R.id.title);
        submit= view.findViewById(R.id.submit);

        spinner= view.findViewById(R.id.spinner);
        spinnerlist= new ArrayList<>();

        Estimateddays= view.findViewById(R.id.estimated_time);


        cd = new ConnectionDetector( getContext() );






        if(action.equalsIgnoreCase( "modify" )){
            modifyTask(taskId);

        }

        getName();

        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView,View view,int i,long l) {
                spinnerId = spinner.getSelectedItem().toString();
                split=spinnerId.split("_");
                Log.d(TAG, "onItemSelected: "+split[0]);
            }
            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });

        /*loginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer("1");
            }
        });*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(action.equalsIgnoreCase( "modify" )){

                    updateModifyTask(taskId,complaintId);
                }
                else{
                    if((takeinstruments.length()==0)&& (title.length()==0) ) {
                        takeinstruments.setError("Select Instruments");
                        title.setError("Select Title");
                    }
                    assigninserting();
                }}
        });

        return view;
    }




    public static ApprovedIeadsDialog newInstance(){
        return new ApprovedIeadsDialog();
    }



    public  interface Oncompleted {
        void Oncompleted();

    }
    @Override
    public void onClick(View view) {

    }

    public void getName() {
        Log.d(TAG, "getIdName: first");
        spinnerlist = new ArrayList<>();
        try {
            if (cd.isConnectingToInternet()) {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl ).
                        client( okHttpClient ).
                        addConverterFactory( GsonConverterFactory
                                .create() ).build();
                API api = RestClient.client.create( API.class );
                Log.d(TAG, "getIdName: second");
                Call<AdminApprovedSpinnerDialogResponse> call = api.getname("empSpinner",Config.getCorp_code(getActivity()));
                call.enqueue(new Callback<AdminApprovedSpinnerDialogResponse>() {
                    @Override
                    public void onResponse(Call<AdminApprovedSpinnerDialogResponse> call, Response<AdminApprovedSpinnerDialogResponse> response) {
                        Log.d(TAG, "onResponse: third");
                        adminApprovedSpinnerDialogResponse = response.body();
                        Log.d("TAG", "onResponse: status111" + adminApprovedSpinnerDialogResponse.getStatus());





                        try {
                            if (adminApprovedSpinnerDialogResponse.getStatus().equalsIgnoreCase("True")) {

                                if(action.equalsIgnoreCase( "modify" )){

                                    Log.d( TAG, "onResponse:modify "+empId );
                                    data.add( empId );
                                }


                                for(int i=0; i< adminApprovedSpinnerDialogResponse.getData().length; i++){
                                    data.add(adminApprovedSpinnerDialogResponse.getData()[i].getEmpId()+"_"+adminApprovedSpinnerDialogResponse.getData()[i].getName());
                                    Log.d(TAG, "onResponse: name"+adminApprovedSpinnerDialogResponse.getData()[i].getName());
                                    Log.d(TAG, "onResponse: empId"+adminApprovedSpinnerDialogResponse.getData()[i].getEmpId());


                                }
                                ArrayAdapter arrayAdapter= new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,data);
                                spinner.setAdapter(arrayAdapter);



                            }

                        } catch (Exception e) {
                            Log.d("TAG", "onResponse: aaaa");
                            e.printStackTrace();


                        }

                    }

                    @Override
                    public void onFailure(Call<AdminApprovedSpinnerDialogResponse> call, Throwable t) {
                        Log.d("TAG", "onFailure: "+t);

                    }
                });
            } else {
                Toast.makeText( getActivity(),
                        "Sorry not connected to internet",
                        Toast.LENGTH_LONG ).show();
            }
        } catch (Exception e) {
            System.out.println( "Exception e" + e );
        }

    }



    public void assigninserting() {
        Log.d(TAG, "getIdName: first");
        spinnerlist = new ArrayList<>();
        try {
            if (cd.isConnectingToInternet()) {


                int days= Integer.parseInt(Estimateddays.getText().toString());


                String estdays=Estimateddays.getText().toString();

                Config.showLoader(getActivity());
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl ).
                        client( okHttpClient ).
                        addConverterFactory( GsonConverterFactory
                                .create() ).build();
                API api = RestClient.client.create( API.class );
                Log.d(TAG, "getIdName: second");
                Call<AdminApprovedSpinnerDialogResponse> call = api.datainserting("AssignTask",Config.getCorp_code(getActivity()),
                        Config.getUserId(getActivity()),complaintId,split[0],title.getText().toString(),takeinstruments.getText().toString(),estdays);
                Log.d(TAG, "assigninserting: llllll"+title.getText().toString()+" "+takeinstruments.getText().toString()+" "+estdays+"_ "+split[0]+" "+complaintId);
                call.enqueue(new Callback<AdminApprovedSpinnerDialogResponse>() {
                    @Override
                    public void onResponse(Call<AdminApprovedSpinnerDialogResponse> call, Response<AdminApprovedSpinnerDialogResponse> response) {
                        Log.d(TAG, "onResponse: third");
                        Config.closeLoader();
                        adminApprovedSpinnerDialogResponse = response.body();
                        Log.d("TAG", "onResponse: status111" + adminApprovedSpinnerDialogResponse.getStatus());

                        try {
                            if (adminApprovedSpinnerDialogResponse.getStatus().equalsIgnoreCase("True")) {
                                getDialog().dismiss();

                                Toast.makeText(getActivity(), "Added Successfully", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onResponse: "+adminApprovedSpinnerDialogResponse);
                            }

                        } catch (Exception e) {
                            Log.d("TAG", "onResponse: aaaa");
                            e.printStackTrace();

                            Config.closeLoader();

                        }

                    }

                    @Override
                    public void onFailure(Call<AdminApprovedSpinnerDialogResponse> call, Throwable t) {
                        Log.d("TAG", "onFailure: "+t);
                        Config.closeLoader();

                    }
                });
            } else {
                Toast.makeText( getActivity(),
                        "Sorry not connected to internet",
                        Toast.LENGTH_LONG ).show();
            }
        } catch (Exception e) {
            System.out.println( "Exception e" + e );
            Config.closeLoader();
        }

    }
    public void modifyTask(String taskId ) {

        Log.d( TAG, "modifyTask: taskkkkkId"+taskId );

        try {
            if (cd.isConnectingToInternet()) {


                Config.showLoader( getContext() );
                OkHttpClient okHttpClient = new OkHttpClient();

                RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl ).
                        client( okHttpClient ).
                        addConverterFactory( GsonConverterFactory
                                .create() ).build();
                API api = RestClient.client.create( API.class );
                Call<ManageTaskPendingResponse> call = api.ModifyTask("modify",taskId,Config.getCorp_code(getContext()) );
                call.enqueue( new Callback<ManageTaskPendingResponse>() {
                    @Override
                    public void onResponse(Call<ManageTaskPendingResponse> call,
                                           Response<ManageTaskPendingResponse> response) {


                        manageTaskPendingResponse = response.body();


                        if (manageTaskPendingResponse.getStatus().equalsIgnoreCase( "true" )) {

                            for (int i = 0; i < manageTaskPendingResponse.getData().length; i++) {

                                Log.d( TAG, "onRespons:empId"+manageTaskPendingResponse.getData()[i].getAssignedTo());

                                title.setText( manageTaskPendingResponse.getData()[i].getTaskTitle() );
                                takeinstruments.setText( manageTaskPendingResponse.getData()[i].getInstruments());
                                Estimateddays.setText( manageTaskPendingResponse.getData()[i].getEstimatedDays() );

                                Log.d( TAG, "onResponse: days" +manageTaskPendingResponse.getData()[i].getEstimatedDays());
                                empId=manageTaskPendingResponse.getData()[i].getAssignedTo()+"_"+manageTaskPendingResponse.getData()[i].getAssignedToName();




                            }

                        }else{

                        }

                    }

                    @Override
                    public void onFailure(Call<ManageTaskPendingResponse> call, Throwable t) {
                        Config.closeLoader();
                        t.getMessage();
                        Toast.makeText( getActivity(),
                                "Try Again!",
                                Toast.LENGTH_LONG ).show();
                    }
                } );
            } else {
                Toast.makeText( getActivity(),
                        "Sorry not connected to internet",
                        Toast.LENGTH_LONG ).show();
            }
        } catch (Exception e) {
            Log.d("TAG", "onResponse:manage3 ");
            System.out.println( "Exception e" + e );
        }
        Config.closeLoader();


    }

    public void updateModifyTask(String taskId,String complaintId) {
        spinnerlist = new ArrayList<>();

        try {
            if (cd.isConnectingToInternet()) {

                String estdays=Estimateddays.getText().toString();

                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl ).
                        client( okHttpClient ).
                        addConverterFactory( GsonConverterFactory
                                .create() ).build();
                API api = RestClient.client.create( API.class );
                Log.d(TAG, "getIdName: second");
                Call<AdminApprovedSpinnerDialogResponse> call = api.updatemodifyTask(
                        "updateTask",taskId,
                        Config.getCorp_code(getActivity()),
                        Config.getUserId(getActivity()),
                        complaintId,split[0],
                        title.getText().toString(),
                        takeinstruments.getText().toString(),
                        estdays);
                Log.d(TAG, "update: llllll"+title.getText().toString()+" "+takeinstruments.getText().toString()+" "+estdays+"_ "+split[0]+" "+complaintId);
                Log.d( TAG, "updateModifyTask: gggg"+Config.getCorp_code( getContext() )+" "+complaintId );
                Log.d( TAG, "updateModifyTask:hshs "+split[0] );
                call.enqueue(new Callback<AdminApprovedSpinnerDialogResponse>() {
                    @Override
                    public void onResponse(Call<AdminApprovedSpinnerDialogResponse> call, Response<AdminApprovedSpinnerDialogResponse> response) {
                        Log.d(TAG, "onResponse: third");
                        adminApprovedSpinnerDialogResponse = response.body();
                        Log.d("TAG", "onResponse: status111" + adminApprovedSpinnerDialogResponse.getStatus());

                        try {
                            if (adminApprovedSpinnerDialogResponse.getStatus().equalsIgnoreCase("True")) {
                                getDialog().dismiss();

                                Log.d(TAG, "onResponse: "+adminApprovedSpinnerDialogResponse);
                            }

                        } catch (Exception e) {
                            Log.d("TAG", "onResponse: aaaa");
                            e.printStackTrace();

                        }

                    }

                    @Override
                    public void onFailure(Call<AdminApprovedSpinnerDialogResponse> call, Throwable t) {
                        Log.d("TAG", "onFailure: "+t);

                    }
                });
            } else {
                Toast.makeText( getActivity(),
                        "Sorry not connected to internet",
                        Toast.LENGTH_LONG ).show();
            }
        } catch (Exception e) {
            System.out.println( "Exception e" + e );
        }

    }



}