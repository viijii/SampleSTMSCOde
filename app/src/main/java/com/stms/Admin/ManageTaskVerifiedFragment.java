package com.stms.Admin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.stms.Adapters.ManageTaskCompletedAdapter;
import com.stms.Adapters.ManageTaskVerifiedAdapter;
import com.stms.R;
import com.stms.Responses.ManageTaskPendingResponse;
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


public class ManageTaskVerifiedFragment extends Fragment implements
        View.OnClickListener, NavigationView.OnNavigationItemSelectedListener  {
    Context context;
    View view;
    RecyclerView listview;
    ConnectionDetector cd;
    SwipeRefreshLayout swipeRefreshLayout;
    ManageTaskPendingResponse manageTaskPendingResponse;
    EditText search;
    ManageTaskVerifiedAdapter manageTaskVerifiedAdapter;
    ArrayList<ManageTaskPendingResponse.data> data = new ArrayList<ManageTaskPendingResponse.data>();



    public ManageTaskVerifiedFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ManageTaskPendingFragment newInstance(String param1, String param2) {
        ManageTaskPendingFragment fragment = new ManageTaskPendingFragment();
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
        view = inflater.inflate(R.layout.manage_task_completed_fragment, container, false);


        search= view.findViewById(R.id.search);
        listview = view.findViewById( R.id.listview );
        swipeRefreshLayout = view.findViewById( R.id.swipeRefreshLayout );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        listview.setLayoutManager( layoutManager );
        listview.setHasFixedSize( true );

        cd = new ConnectionDetector( getActivity() );
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println( "Refresh Data" );
                getManageTaskList();
                swipeRefreshLayout.setRefreshing( false );
            }
        } );


        search.setInputType( InputType.TYPE_CLASS_TEXT );
        search.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d( "TAG", "onTextChanged: " + s );
                try{
                    manageTaskVerifiedAdapter.getFilter().filter(s.toString());
                }
                catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println( "Search String---" + s.toString() );


            }


        } );


        getManageTaskList();





        return view;
    }

    public void getManageTaskList() {

        Log.d("TAG", "onResponse:manage1 ");

        try {
            if (cd.isConnectingToInternet()) {

                Log.d("TAG", "onResponse:manage2 ");
                Config.showLoader( getActivity() );
                OkHttpClient okHttpClient = new OkHttpClient();
                Log.d("TAG", "onResponse:manage5 ");
                RestClient.client = new Retrofit.Builder().baseUrl( RestClient.baseUrl ).
                        client( okHttpClient ).
                        addConverterFactory( GsonConverterFactory
                                .create() ).build();
                API api = RestClient.client.create( API.class );
                Call<ManageTaskPendingResponse> call = api.getManageTask("verified",Config.getLoginUserType(getActivity()),Config.getCorp_code(getActivity()) );
                call.enqueue( new Callback<ManageTaskPendingResponse>() {
                    @Override
                    public void onResponse(Call<ManageTaskPendingResponse> call,
                                           Response<ManageTaskPendingResponse> response) {

                        Log.d("TAG", "onResponse:manage4 ");
                        manageTaskPendingResponse = response.body();
                        data = new ArrayList<ManageTaskPendingResponse.data>();

                        Log.d("TAG", "onResponse:manage "+manageTaskPendingResponse.getStatus());
                        if (manageTaskPendingResponse.getStatus().equalsIgnoreCase( "true" )) {

                            for (int i = 0; i < manageTaskPendingResponse.getData().length; i++) {


                                data.add(manageTaskPendingResponse.getData()[i]);

                                Log.d("TAG", "onResponse: dataManage"+manageTaskPendingResponse.getData()[i].getTaskTitle());
                                Log.d("TAG", "onResponse: dataManage"+manageTaskPendingResponse.getData()[i].getComplaintId());
                                Log.d("TAG", "onResponse: dataManage"+manageTaskPendingResponse.getData()[i].getName());




                            }
                        }
                        setListView();
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

    private void setListView() {

        manageTaskVerifiedAdapter  = new ManageTaskVerifiedAdapter( getActivity(), data, ManageTaskVerifiedFragment.this, getContext() );
        listview.setAdapter( manageTaskVerifiedAdapter );
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View view) {

    }
}
