package com.example.tulasirao.liveapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tulasi rao on 13-02-2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<Listitem> listItems;
    private Context context;

    public MyAdapter(List<Listitem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Listitem listitem = listItems.get(position);

        holder.textViewhead.setText(listitem.getHead());
        holder.textViewdesc.setText(Html.fromHtml(listitem.getDesc()));

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewhead;
        public TextView textViewdesc;


        public ViewHolder(View itemView) {
            super(itemView);


            textViewhead = (TextView)itemView.findViewById(R.id.textviewhead);
            textViewdesc = (TextView)itemView.findViewById(R.id.textviewdesc);

        }
    }





}
