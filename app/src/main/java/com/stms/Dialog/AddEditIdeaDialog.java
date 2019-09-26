/*
Initial Declaration :-
        file name :-AddEditIdeaDialog.java.
        Purpose :- To propose an idea.
        Methods :- saveIdea(),modifyIdea().
        Developer Name :-
        Release Number:- 1.2.3

        Future Declaration :-
        Ongoing Changes
        Developer Name :-“Name Of the Developer” and Last Updated on “Date”
        Release Number:- “Enter Ur Release Number”
        change description :- “Reason for Ur change”
        change date:- “Enter the date of file change”
        Change Number :- “increment the count whenever you change the file content”
*/

package com.stms.Dialog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.stms.Customers.ImageUpload;
import com.stms.R;
import com.stms.Responses.AdminRequestedResponse;
import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;
import com.stms.webservices.API;
import com.stms.webservices.RestClient;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class AddEditIdeaDialog extends DialogFragment
{
    View view;
    ImageView imagePath;
    Spinner spinner;
    String[] vehicleId;
    ConnectionDetector cd;
    AdminRequestedResponse adminRequestedResponse;
    public static String status="";
    EditText title,description;
    String spinnerId="NA";
    ArrayList<String> data=new ArrayList<String>();
    List<AdminRequestedResponse.data> spinnerlist;
    String action;

    @Override
    public void onStart()
    {
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



    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        getDialog().setTitle("Add Idea");

        view = inflater.inflate(R.layout.add_edit_idea_dialog_layout, container, false);

        title = view.findViewById(R.id.title);
        spinner = view.findViewById(R.id.spinner);
        imagePath = view.findViewById(R.id.image);
        description = view.findViewById(R.id.description);
        spinnerlist=new ArrayList<>();


        action=getArguments().getString("action");
        cd = new ConnectionDetector(getActivity());
        getVehTypeSpinner();

        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> adapterView,View view,int i,long l) {
                spinnerId = spinner.getSelectedItem().toString();
                Log.d(TAG, "onItemSelected:kkkk "+ spinnerId);
                vehicleId=spinnerId.split("_");
                Log.d(TAG, "onItemSelected:kkkk "+ vehicleId[0]);
            }
            @Override
            public void onNothingSelected(AdapterView <?> adapterView) {

            }
        });
             imagePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), ImageUpload.class);
                intent.putExtra("title",title.getText().toString());
                intent.putExtra("description",description.getText().toString());
                intent.putExtra("spinner",vehicleId[0]);

                Log.d(TAG, "onClick: intent"+title.getText().toString()+" "+description.getText().toString()+" "+vehicleId[0]);
                startActivity(intent);
            }
        });



       return view;
    }


    public void getVehTypeSpinner()
    {

        Log.d(TAG, "onResponse:first");

        spinnerlist=new ArrayList<>();
        try
        {

            if (cd.isConnectingToInternet())
            {
                //Config.showLoader(getActivity());
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Log.d(TAG, "onResponse:second");

                Call<AdminRequestedResponse> call = api.getSpinner(
                        "vehicleSpinner", Config.getCorp_code(getActivity()),Config.getUserId(getActivity()));

                call.enqueue(new Callback<AdminRequestedResponse>()
                {
                    @Override
                    public void onResponse(Call<AdminRequestedResponse> call,
                                           Response<AdminRequestedResponse> response) {

                        adminRequestedResponse = response.body();

               //         Log.d(TAG, "onResponse:spinner"+adminRequestedResponse.getData().length);
                        Log.d(TAG, "onResponse:spinner"+adminRequestedResponse.getStatus());

                        if (adminRequestedResponse.getStatus().equalsIgnoreCase("true")) {
                            for (int i = 0; i < adminRequestedResponse.getData().length; i++) {
                                data.add(adminRequestedResponse.getData()[i].getVehicleId() +"_"+adminRequestedResponse.getData()[i].getVehicleType());
                                Log.d(TAG, "onResponse: " + adminRequestedResponse.getData()[i]);
                                Log.d(TAG, "onResponse:total "+adminRequestedResponse.getData()[i].getVehicleId() +" "+adminRequestedResponse.getData()[i].getVehicleType());
                                Log.d(TAG, "onResponse:Id " + adminRequestedResponse.getData()[i].getVehicleId());
                                Log.d(TAG, "onResponse:Type"+adminRequestedResponse.getData()[i].getVehicleType());

                            }

                        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, data);
                            spinner.setAdapter(arrayAdapter);
                           // Config.closeLoader();
                    }else{
                            Config.showToast(getActivity(),adminRequestedResponse.getMessage());
                            status = "Error";
                        }

                       /* getDialog().cancel();
                        Intent i = new Intent()
                                .putExtra("status", status);
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                        Config.closeLoader();*/
                        //sendBackResult();
                    }

                    @Override
                    public void onFailure(Call<AdminRequestedResponse> call, Throwable t)
                    {
                        Config.closeLoader();
                        t.getMessage();
                        Toast.makeText(getActivity(),
                                "Try Again!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
            {
                Toast.makeText(getActivity(),
                        "Sorry not connected to internet",
                        Toast.LENGTH_LONG).show();
            }
        }


        catch(Exception e)
        {
            System.out.println("Exception e"+e);
        }
    }
}