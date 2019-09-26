package com.stms.Admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.stms.Adapters.AdminRequestedIdeasAdapter;
import com.stms.R;
import com.stms.Responses.AdminRequestedResponse;
import com.stms.utils.Config;
import com.stms.utils.ConnectionDetector;
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


public class AdminRequestedIdeasFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    View view;
    RecyclerView listview;
    ConnectionDetector cd;
    AdminRequestedResponse adminRequestedResponse;
    ArrayList<AdminRequestedResponse.data> data = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    AdminRequestedIdeasAdapter adminRequestedIdeasAdapter;

    public AdminRequestedIdeasFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AdminRequestedIdeasFragment newInstance(String param1, String param2) {
        AdminRequestedIdeasFragment fragment = new AdminRequestedIdeasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_requested_ideas, container, false);

        listview = view.findViewById(R.id.listview);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(layoutManager);
        listview.setHasFixedSize(true);
        cd = new ConnectionDetector(getActivity());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("Refresh Data");
                getRequestedIdeas();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getRequestedIdeas();
        return view;
    }

    public void setListView()
    {
        adminRequestedIdeasAdapter = new AdminRequestedIdeasAdapter(getActivity(),data,AdminRequestedIdeasFragment.this);
        listview.setAdapter(adminRequestedIdeasAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void getRequestedIdeas() {
        Log.d(TAG, "getRequestedIdeas: kkkkkkkkkkkkkkkkkkk");
        try {
            Log.d(TAG, "getRequestedIdeas: mmmmmmmmmm");
            if (cd.isConnectingToInternet()) {
                Log.d(TAG, "getRequestedIdeas: sssssssssss");
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Log.d( TAG, "getRequestedIdeas:jagType"+Config.getLoginUserType( getActivity() ) );

                Call<AdminRequestedResponse> call = api.getIdeas("requested",Config.getLoginUserType(getActivity()).toLowerCase(),
                        Config.getCorp_code(getActivity()));

                call.enqueue(new Callback<AdminRequestedResponse>()
                {
                    @Override
                    public void onResponse(Call<AdminRequestedResponse> call,
                                           Response<AdminRequestedResponse> response)
                    {
                        Config.closeLoader();
                        adminRequestedResponse = response.body();
                        Log.d( TAG, "getRequestedIdeas:jagidea"+adminRequestedResponse );

                        data = new ArrayList<AdminRequestedResponse.data>();

                        if(adminRequestedResponse.getStatus().equalsIgnoreCase("true")) {

                            if (adminRequestedResponse.getData() == null) {
                                Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
                            } else {


                                for (int i = 0; i < adminRequestedResponse.getData().length; i++) {
                                    data.add(adminRequestedResponse.getData()[i]);
                                    Log.d(TAG, "onResponse: " + adminRequestedResponse.getData()[i]);
                                    Log.d(TAG, "onResponse: dataRequested" + adminRequestedResponse.getData()[i].getImagePath());
                                    Log.d(TAG, "onResponse: " + adminRequestedResponse.getData()[i].getDescription());
                                    Log.d(TAG, "onResponse: dataRequested" + adminRequestedResponse.getData()[i].getComplaintId());
                                    Log.d(TAG, "onResponse: " + adminRequestedResponse.getData()[i].getMobileNumber());
                                }
                            }

                            setListView();
                        }
                    }

                    @Override
                    public void onFailure(Call<AdminRequestedResponse> call, Throwable t)
                    {
                        Config.closeLoader();
                        t.getMessage();
                        // Toast.makeText(getActivity(), "Try Again!", Toast.LENGTH_LONG).show();
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


    public void approveComplaint(String complaintId) {
        Log.d(TAG, "getRequestedIdeas: kkkkkkkkkkkkkkkkkkk");
        try {
            if (cd.isConnectingToInternet()) {
                Config.showLoader(getActivity());
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<AdminRequestedResponse> call = api.approveIdea("approve",complaintId,
                        Config.getCorp_code(getActivity()));

                call.enqueue(new Callback<AdminRequestedResponse>()
                {
                    @Override
                    public void onResponse(Call<AdminRequestedResponse> call,
                                           Response<AdminRequestedResponse> response)
                    {
                        Config.closeLoader();
                        adminRequestedResponse = response.body();
                        Log.d( TAG, "getRequestedIdeas:jagidea"+adminRequestedResponse );

                        data = new ArrayList<AdminRequestedResponse.data>();

                        if(adminRequestedResponse.getStatus().equalsIgnoreCase("true"))
                        {
                            //System.out.println("User Data---"+userResponse.getData().length);
                            Toast.makeText(getActivity(), "Approved Successfully", Toast.LENGTH_LONG).show();
                            getRequestedIdeas();
                        }
                    }

                    @Override
                    public void onFailure(Call<AdminRequestedResponse> call, Throwable t)
                    {
                        Config.closeLoader();
                        t.getMessage();
                        // Toast.makeText(getActivity(), "Try Again!", Toast.LENGTH_LONG).show();
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
    public void approveIdeaAlert(final String position)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Alert!");

        // Setting Dialog Message
        alertDialog
                .setMessage("Do you really want to approve Service");

        // On pressing Settings button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        approveComplaint(position);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alertDialog1 = alertDialog.create();
        // Showing Alert Message
        alertDialog.show();
    }



}