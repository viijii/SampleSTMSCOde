package com.stms.Customers;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.Toast;
import com.stms.Adapters.CustomerApprovedIdeasAdapter;
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


public class CustomerApprovedIdeasFragment extends Fragment  {

    View view;
    EditText search;
    RecyclerView listview;
    ConnectionDetector cd;
    AdminRequestedResponse adminRequestedResponse;
    ArrayList<AdminRequestedResponse.data> data = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    CustomerApprovedIdeasAdapter customerApprovedIdeasAdapter;

    public static final int ADD_DIALOG_FRAGMENT=1;

    public CustomerApprovedIdeasFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CustomerApprovedIdeasFragment newInstance(String param1, String param2) {
        CustomerApprovedIdeasFragment fragment = new CustomerApprovedIdeasFragment();
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
        view = inflater.inflate(R.layout.fragment_customer_approved_ideas, container, false);

        listview = view.findViewById(R.id.listview);

        search= view.findViewById(R.id.search);
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
                    customerApprovedIdeasAdapter.getFilter().filter(s.toString());
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void getCustomerIdeas() {
        Log.d(TAG, "approved: kkkkkkkkkkkkkkkkkkk");
        try {
            Log.d(TAG, "approved: mmmmmmmmmm");
            if (cd.isConnectingToInternet()) {
                Log.d(TAG, "approved: sssssssssss");
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);
                Log.d( TAG, "approved:jagType"+ Config.getLoginUserType( getActivity() ) );
                Log.d( TAG, "approved:jagType"+Config.getCorp_code( getActivity() ) );
                Log.d( TAG, "approved:jagType"+Config.getUserId( getActivity() ) );

                Call<AdminRequestedResponse> call = api.customerapprovedIdeas("requested",Config.getLoginUserType(getActivity()).toLowerCase(),
                        Config.getCorp_code(getActivity()),Config.getUserId(getActivity()));

                call.enqueue(new Callback<AdminRequestedResponse>()
                {
                    @Override
                    public void onResponse(Call<AdminRequestedResponse> call,
                                           Response<AdminRequestedResponse> response) {

                        adminRequestedResponse = response.body();
                        Log.d(TAG, "approved:customerapproved" + adminRequestedResponse);
                        Log.d(TAG, "onResponse: customerapprovedstatus" + adminRequestedResponse.getStatus());


                        if (adminRequestedResponse.getStatus().equalsIgnoreCase("true")) {


                            if (adminRequestedResponse.getData()==null) {
                                Toast.makeText(getActivity(), "No data in approved", Toast.LENGTH_SHORT).show();
                                data.clear();
                            } else {

                                data.clear();
                                for (int i = 0; i < adminRequestedResponse.getData().length; i++) {
                                    Log.d(TAG, "onResponse:approved " + adminRequestedResponse.getData()[i]);
                                    Log.d(TAG, "onResponse:approved" + adminRequestedResponse.getData()[i].getImagePath());
                                    Log.d(TAG, "onResponse:a[pproved " + adminRequestedResponse.getData()[i].getDescription());
                                    Log.d(TAG, "onResponse: approved" + adminRequestedResponse.getData()[i].getComplaintId());

                                    data.add(adminRequestedResponse.getData()[i]);

                                }

                            }


                        }
                        setListView();

                    }
                    @Override
                    public void onFailure(Call<AdminRequestedResponse> call, Throwable t)
                    {
                        Log.d(TAG, "onFailure: "+t);
                        Config.closeLoader();
                        t.getMessage();
                         Toast.makeText(getActivity(), "Try Again!", Toast.LENGTH_LONG).show();
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
            Log.d(TAG, "getRequestedIdeas: "+e);
            System.out.println("Exception e"+e);
        }
    }

    public void setListView()
    {
        Log.d(TAG, "setListView:data"+data);
        customerApprovedIdeasAdapter = new CustomerApprovedIdeasAdapter(getActivity(),data, CustomerApprovedIdeasFragment.this);
        listview.setAdapter(customerApprovedIdeasAdapter);
    }


}