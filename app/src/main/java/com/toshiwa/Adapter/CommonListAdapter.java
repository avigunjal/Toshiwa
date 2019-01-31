package com.toshiwa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toshiwa.Activity.LeadDashboard;
import com.toshiwa.Activity.LeadDetails;
import com.toshiwa.CustomView.TextView_Light;
import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.Model.Lead.DisplayLeadListResult;
import com.toshiwa.toshiwa.R;

import java.util.List;

public class CommonListAdapter extends RecyclerView.Adapter<CommonListAdapter.LeadViewHolder> {

    List<DisplayLeadListResult> mData;
    Context mContext;
    String mId;
    public CommonListAdapter(Context context, List<DisplayLeadListResult> data, String id) {
        mContext = context;
        mData = data;
        mId = id;
    }

    @NonNull
    @Override
    public LeadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_lead_list, viewGroup, false);
        return new LeadViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final LeadViewHolder holder, final int position) {

        if(mData.get(position).getStatus().equals("true")) {

            holder.mStatus.setText("Completed");
            holder.mStatus.setBackgroundColor(mContext.getResources().getColor(R.color.green));
            holder.mName.setText(mData.get(position).getName());
            holder.mDate.setText(mData.get(position).getDate());
            holder.mLocation.setText(mData.get(position).getLocation());
            holder.mMobile.setText(mData.get(position).getMobile());
        } else {
            holder.mStatus.setText("Pending");
            holder.mStatus.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.mName.setText(mData.get(position).getName());
            holder.mDate.setText(mData.get(position).getDate());
            holder.mLocation.setText(mData.get(position).getLocation());
            holder.mMobile.setText(mData.get(position).getMobile());
        }

        holder.mLeadCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LeadDetails.class);
                intent.putExtra("id",mId);
                intent.putExtra("lid",mData.get(position).getLid());
                intent.putExtra("name",mData.get(position).getName());
                intent.putExtra("mobile",mData.get(position).getMobile());
                intent.putExtra("location",mData.get(position).getLocation());
               mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount()
    {

        return mData.size();

    }

    public class LeadViewHolder extends RecyclerView.ViewHolder {

        protected TextView_Regular mName,mMobile,mLocation,mStatus;
        TextView_Light mDate;
        CardView mLeadCard;

        public LeadViewHolder(View view) {
            super(view);

            this.mLeadCard = (CardView) view.findViewById(R.id.lead_card);
            this.mName = (TextView_Regular) view.findViewById(R.id.tv_customer);
            this.mLocation = (TextView_Regular) view.findViewById(R.id.tv_location);
            this.mMobile = (TextView_Regular) view.findViewById(R.id.tv_mobile);
            this.mDate = (TextView_Light)view.findViewById(R.id.tv_date);
            this.mStatus = (TextView_Regular) view.findViewById(R.id.tv_status);
             }

    }

}
