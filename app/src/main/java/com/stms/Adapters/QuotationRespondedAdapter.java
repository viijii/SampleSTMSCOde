package com.stms.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import com.stms.Admin.QuotationRespondedFragment;
import com.stms.R;
import com.stms.Responses.QuotationsResponse;

import java.util.ArrayList;

public class QuotationRespondedAdapter extends RecyclerView.Adapter<QuotationRespondedAdapter.EMSHolder> {
    ArrayList<QuotationsResponse.data> data;
    Activity con;
    int selectedIndex = -1;
    int FADE_DURATION = 100;
    int i=0;
    QuotationRespondedFragment quotationRespondedFragment;

    public QuotationRespondedAdapter(Activity con, ArrayList<QuotationsResponse.data> data,
                                     QuotationRespondedFragment quotationRespondedFragment)
    {
        this.con = con;
        this.data = data;
        this.quotationRespondedFragment = quotationRespondedFragment;
    }

    @NonNull
    @Override
    public QuotationRespondedAdapter.EMSHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_responded_cardview,
                parent, false);

        return new QuotationRespondedAdapter.EMSHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuotationRespondedAdapter.EMSHolder holder, int position) {
        setScaleAnimation(holder.itemView);

        holder.quotedId.setText(data.get(position).getQuotationId());
        holder.status.setTextColor(Color.RED);
        holder.status.setText(data.get(position).getQuotationStatus());
        holder.customerName.setText(data.get(position).getCustomerName());
        holder.email.setText(data.get(position).getEmail());
        holder.model.setText(data.get(position).getVehModel());
        holder.vehType.setText(data.get(position).getVehType());
        holder.mobile.setText(data.get(position).getMobileNumber());


    }
    private void setScaleAnimation(View view)
    {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class EMSHolder extends RecyclerView.ViewHolder {
        TextView customerName,email,mobile,model,vehType,status,quotedId;

        public EMSHolder(View itemView) {
            super(itemView);
            customerName=itemView.findViewById(R.id.name);
            email=itemView.findViewById(R.id.email);
            mobile=itemView.findViewById(R.id.phoneNumber);
            model=itemView.findViewById(R.id.vehicleModel);
            vehType=itemView.findViewById(R.id.vehicleType);
            status=itemView.findViewById(R.id.status);
            quotedId=itemView.findViewById(R.id.quoteId);



        }
    }
}
