package com.example.tulasirao.liveapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URL_DATA = "https://inspirablestuff.blogspot.com/feeds/posts/default?alt=json";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<Listitem> listItems;
    public String x,y;

    private static int SPLASH_TIME_OUT=4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView =(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        listItems = new ArrayList<>();

        loadRecyclerViewData();

        }

        private void loadRecyclerViewData(){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading Data");
            progressDialog.show();


            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    URL_DATA,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject =new JSONObject(response);
                                JSONObject object = jsonObject.getJSONObject("feed");
                                JSONArray array = object.getJSONArray("entry");

                                for(int i=0;i<array.length();i++){
                                    JSONObject o = array.getJSONObject(i);
                                    JSONObject a=o.getJSONObject("title");

                                    JSONObject b=o.getJSONObject("content");

                                    x=a.getString("$t");
                                    y=b.getString("$t");

                                    Listitem item =new Listitem(x,y);

                                    listItems.add(item);

                                }

                                adapter= new MyAdapter(listItems,getApplicationContext());
                                recyclerView.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }




    }

