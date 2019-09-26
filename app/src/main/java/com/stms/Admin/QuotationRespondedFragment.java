package com.stms.Admin;

import android.content.Context;
import android.net.Uri;
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

import com.stms.Adapters.QuotationRequestedAdapter;
import com.stms.Adapters.QuotationRespondedAdapter;
import com.stms.R;
import com.stms.Responses.QuotationsResponse;
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


public class QuotationRespondedFragment extends Fragment {


    View view;
    RecyclerView listview;
    ConnectionDetector cd;
    SwipeRefreshLayout swipeRefreshLayout;
    QuotationRespondedAdapter quotationRespondedAdapter;
    QuotationsResponse quotationsResponse;
    ArrayList<QuotationsResponse.data> data = new ArrayList<>();

    public QuotationRespondedFragment() {
        // Required empty public constructor
    }


    public static QuotationRespondedFragment newInstance(String param1, String param2) {
        QuotationRespondedFragment fragment = new QuotationRespondedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_quotation_responded, container, false);


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
                getRequestedQuotes();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getRequestedQuotes();
        return view;
    }

    public void setListView()
    {
        quotationRespondedAdapter = new QuotationRespondedAdapter(getActivity(),data,QuotationRespondedFragment.this);
        listview.setAdapter(quotationRespondedAdapter);
    }


    public void getRequestedQuotes() {
        try {
            Log.d(TAG, "getRequestedQuotes: mmmmmmmmmm");
            if (cd.isConnectingToInternet()) {
                Log.d(TAG, "getRequestedQuotes: sssssssssss");
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Log.d(TAG, "getRequestedQuotes:jagType" + Config.getLoginUserType(getActivity()));

                Call<QuotationsResponse> call = api.getReqQuotations("responded", Config.getLoginUserType(getActivity()).toLowerCase(),
                        Config.getCorp_code(getActivity()));

                call.enqueue(new Callback<QuotationsResponse>() {
                    @Override
                    public void onResponse(Call<QuotationsResponse> call,
                                           Response<QuotationsResponse> response) {
                        Config.closeLoader();
                        quotationsResponse = response.body();
                        Log.d(TAG, "getRequestedQuotes:jagidea" + quotationsResponse);

                        data = new ArrayList<QuotationsResponse.data>();

                        if (quotationsResponse.getStatus().equalsIgnoreCase("true")) {
                            //System.out.println("User Data---"+userResponse.getData().length);


                            if (quotationsResponse.getData() == null) {
                                Toast.makeText(getContext(), "No data", Toast.LENGTH_SHORT).show();
                            } else {


                                for (int i = 0; i < quotationsResponse.getData().length; i++) {
                                    data.add(quotationsResponse.getData()[i]);
                                   /* Log.d(TAG, "onResponse: " + adminRequestedResponse.getData()[i]);
                                    Log.d(TAG, "onResponse: dataRequested" + adminRequestedResponse.getData()[i].getImagePath());
                                    Log.d(TAG, "onResponse: " + adminRequestedResponse.getData()[i].getDescription());
                                    Log.d(TAG, "onResponse: dataRequested" + adminRequestedResponse.getData()[i].getComplaintId());
                                    Log.d(TAG, "onResponse: " + adminRequestedResponse.getData()[i].getMobileNumber());*/
                                }
                            }

                            setListView();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuotationsResponse> call, Throwable t) {
                        Config.closeLoader();
                        t.getMessage();
                         Toast.makeText(getActivity(), "Try Again!", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(getActivity(),
                        "Sorry not connected to internet",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            System.out.println("Exception e" + e);
        }


    }
}
