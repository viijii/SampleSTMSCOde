package com.stms.Customers;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stms.Adapters.CustomerCompletedAdapter;
import com.stms.Adapters.CustomerRequestAdapter;
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


public class CustomerCompletedIdeasFragment extends Fragment {
    EditText search;
    View view;
    RecyclerView listview;
    ConnectionDetector cd;
    AdminRequestedResponse adminRequestedResponse;
    ArrayList<AdminRequestedResponse.data> data = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    CustomerCompletedAdapter customerCompletedAdapter;


    public static final int ADD_DIALOG_FRAGMENT = 1;
    public CustomerCompletedIdeasFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CustomerCompletedIdeasFragment newInstance(String param1, String param2) {
        CustomerCompletedIdeasFragment fragment = new CustomerCompletedIdeasFragment();
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
        view = inflater.inflate(R.layout.fragment_customer_completed_ideas, container, false);


        search=view.findViewById(R.id.search);
        listview = view.findViewById(R.id.listview);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listview.setLayoutManager(layoutManager);
        listview.setHasFixedSize(true);
        cd = new ConnectionDetector(getActivity());

        search.setInputType(InputType.TYPE_CLASS_TEXT);
        search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                //  adminApprovedIdeasAdapter.getFilter().filter(s.toString());
                try{

                    Log.d("TAG", "onTextChanged: "+s);
                    // filter recycler view when query submitted
                    customerCompletedAdapter.getFilter().filter(s.toString());
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                System.out.println("Search String---"+s.toString());


            }


        });


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
        customerCompletedAdapter = new CustomerCompletedAdapter(getActivity(), data, CustomerCompletedIdeasFragment.this);
        listview.setAdapter(customerCompletedAdapter);
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
                Call<AdminRequestedResponse> call = api.getCustomercompletedIdeas("completed",
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
                            //System.out.println("User Data---"+userResponse.getData().length);

                            if(adminRequestedResponse.getData()==null){
                                Toast.makeText(getActivity(),"No Data Getting",Toast.LENGTH_LONG).show();
                            }else {

                                for (int i = 0; i < adminRequestedResponse.getData().length; i++) {
                                    data.add(adminRequestedResponse.getData()[i]);
                                    Log.d("TAG", "onResponse:completed " + adminRequestedResponse.getData()[i]);
                                    Log.d("TAG", "onResponse:completed1" + adminRequestedResponse.getData()[i].getComplaintId());
                                    Log.d("TAG", "onResponse:completed2" + adminRequestedResponse.getData()[i].getTitle());
                                    Log.d("TAG", "onResponse:completed3 " + adminRequestedResponse.getData()[i].getVehicleType());

                                }
                            }
                        }

                        setListView();
                    }

                    @Override
                    public void onFailure(Call<AdminRequestedResponse> call, Throwable t) {
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
