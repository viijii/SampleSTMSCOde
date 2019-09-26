package com.stms.Employess;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.stms.Adapters.MyTaskVerifiedAdapter;
import com.stms.R;
import com.stms.Responses.MyTaskResponse;
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

public class VerifiedTaskListFragment extends Fragment {
    EditText search;
    MyTaskVerifiedAdapter myTaskVerifiedAdapter;
    RecyclerView listview;
    ConnectionDetector cd;
    MyTaskResponse myTaskResponse;
    ArrayList<MyTaskResponse.data> data  = new ArrayList<>();
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    public static final int DIALOG_FRAGMENT_TARGET = 1;
    SwipeRefreshLayout swipeRefreshLayout;
    View view;

    public VerifiedTaskListFragment() {
        // Required empty public constructor
    }


    public static VerifiedTaskListFragment newInstance(String param1, String param2) {
        VerifiedTaskListFragment fragment = new VerifiedTaskListFragment();
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
        view= inflater.inflate(R.layout.fragment_verified_task_list, container, false);

        listview = view.findViewById(R.id.listview);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        search= view.findViewById(R.id.search);

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
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    myTaskVerifiedAdapter.getFilter().filter(s.toString());

                } catch (NullPointerException e) {
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
                getTaskList();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getTaskList();
        return view;
    }

    /* this method is used to list the tasks*/

    public void getTaskList()
    {
        try
        {
            if (cd.isConnectingToInternet())
            {
                Config.showLoader(getActivity());
                OkHttpClient okHttpClient = new OkHttpClient();
                RestClient.client = new Retrofit.Builder().baseUrl(RestClient.baseUrl).
                        client(okHttpClient).
                        addConverterFactory(GsonConverterFactory
                                .create()).build();
                API api = RestClient.client.create(API.class);

                Call<MyTaskResponse> call = api.getUserTask(Config.getLoginUserType(getActivity()),"verified",
                        ""  , Config.getCorp_code(getActivity()), Config.getUserId(getActivity()));

                call.enqueue(new Callback<MyTaskResponse>()
                {
                    @Override
                    public void onResponse(Call<MyTaskResponse> call,
                                           Response<MyTaskResponse> response)
                    {
                        Config.closeLoader();
                        myTaskResponse = response.body();

                        data = new ArrayList<MyTaskResponse.data>();
                        Log.d( "TAG", "onResponse:alltaskresponse "+myTaskResponse.getStatus() );

                        if(myTaskResponse.getStatus().equalsIgnoreCase("true"))
                        {
                            //System.out.println("User Data---"+userResponse.getData().length);
                            for(int i=0; i<myTaskResponse.getData().length; i++ )
                            {
                                if( myTaskResponse.getData()[i].getStatus().equalsIgnoreCase( "verified" ) ) {

                                    data.add( myTaskResponse.getData()[i] );

                                }
                                else{

                                }

                            }
                        }


                        setListView();
                        //   Config.closeLoader();
                    }

                    @Override
                    public void onFailure(Call<MyTaskResponse> call, Throwable t)
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
            //  Config.closeLoader();
        }


        catch(Exception e)
        {
            System.out.println("Exception e"+e);
        }
        Config.closeLoader();

    }

    /* this method is used to send data to adapter to display data*/
    public void setListView()
    {
        myTaskVerifiedAdapter = new MyTaskVerifiedAdapter(getActivity(),data,VerifiedTaskListFragment.this,getContext());
        listview.setAdapter(myTaskVerifiedAdapter);

    }


}
