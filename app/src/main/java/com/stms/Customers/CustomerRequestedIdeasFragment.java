package com.stms.Customers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.stms.Adapters.CustomerRequestAdapter;

import com.stms.Dialog.AddEditIdeaDialog;
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


public class CustomerRequestedIdeasFragment extends Fragment implements View.OnClickListener {

    View view;
    RecyclerView listview;
    ConnectionDetector cd;
    AdminRequestedResponse adminRequestedResponse;
    ArrayList<AdminRequestedResponse.data> data = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    CustomerRequestAdapter customerRequestAdapter;

    LinearLayout add_idea_layout;
    Button add_idea;
    public static final int ADD_DIALOG_FRAGMENT = 1;
    public CustomerRequestedIdeasFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CustomerRequestedIdeasFragment newInstance(String param1, String param2) {
        CustomerRequestedIdeasFragment fragment = new CustomerRequestedIdeasFragment();
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
        view = inflater.inflate(R.layout.fragment_customer_requested_ideas, container, false);

        add_idea_layout = view.findViewById(R.id.add_idea_layout);
        add_idea = view.findViewById(R.id.add_idea);

        listview = view.findViewById(R.id.listview);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(layoutManager);
        listview.setHasFixedSize(true);
        cd = new ConnectionDetector(getActivity());

        add_idea_layout.setOnClickListener(this);
        add_idea.setOnClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("Refresh Data");
                getCustomerIdeas();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getCustomerIdeas();
        return view;
    }

    public void setListView() {
        customerRequestAdapter = new CustomerRequestAdapter(getActivity(), data, CustomerRequestedIdeasFragment.this);
        listview.setAdapter(customerRequestAdapter);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void getCustomerIdeas() {
        try {
            if (cd.isConnectingToInternet()) {
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Call<AdminRequestedResponse> call = api.getCustomerIdeas("requested",
                        Config.getLoginUserType(getActivity()).toLowerCase(),
                        Config.getUserId(getActivity()),
                        Config.getCorp_code(getActivity()));

                call.enqueue(new Callback<AdminRequestedResponse>() {
                    @Override
                    public void onResponse(Call<AdminRequestedResponse> call,
                                           Response<AdminRequestedResponse> response) {
                        Config.closeLoader();
                        adminRequestedResponse = response.body();
                        data = new ArrayList<AdminRequestedResponse.data>();




                        if (adminRequestedResponse.getStatus().equalsIgnoreCase("true")) {

                            if(adminRequestedResponse.getData()==null){
                                Toast.makeText(getActivity(),"No Data Getting",Toast.LENGTH_LONG).show();
                            }else {

                                for (int i = 0; i < adminRequestedResponse.getData().length; i++) {
                                    data.add(adminRequestedResponse.getData()[i]);
                                    Log.d("TAG", "onResponse: " + adminRequestedResponse.getData()[i]);
                                    Log.d("TAG", "onResponse: dataRequested" + adminRequestedResponse.getData()[i].getComplaintId());
                                    Log.d("TAG", "onResponse: " + adminRequestedResponse.getData()[i].getTitle());
                                    Log.d("TAG", "onResponse: " + adminRequestedResponse.getData()[i].getVehicleType());

                                }
                            }
                        }

                        setListView();
                    }

                    @Override
                    public void onFailure(Call<AdminRequestedResponse> call, Throwable t) {
                        Config.closeLoader();
                        t.getMessage();
                        // Toast.makeText(getActivity(), "Try Again!", Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_idea_layout||v.getId() == R.id.add_idea) {
            Bundle b = new Bundle();
            b.putString("action", "add");
            DialogFragment newFragment = new AddEditIdeaDialog();
            newFragment.setArguments(b);
            FragmentManager fm = getFragmentManager();
            newFragment.setTargetFragment(this, ADD_DIALOG_FRAGMENT);
            newFragment.show(fm, "Add Idea Fragment");
        }
    }
}

