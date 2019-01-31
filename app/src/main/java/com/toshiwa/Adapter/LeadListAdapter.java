package com.toshiwa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.toshiwa.Activity.LeadDashboard;
import com.toshiwa.Activity.LeadList;
import com.toshiwa.CustomView.TextView_Light;
import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.Model.Lead.AddLeadResponse;
import com.toshiwa.Model.Lead.DisplayLeadList;
import com.toshiwa.Model.Lead.DisplayLeadListResult;
import com.toshiwa.Utils.ApiUtils;
import com.toshiwa.toshiwa.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadListAdapter extends RecyclerView.Adapter<LeadListAdapter.LeadViewHolder> {

    List<DisplayLeadListResult> mData;
    Context mContext;
    public LeadListAdapter(Context context, List<DisplayLeadListResult> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public LeadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_lead_list, viewGroup, false);
        return new LeadViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final LeadViewHolder holder, final int position) {
        if(mData.get(position).getDelete().equals("true")) {
        holder.mLeadCard.setVisibility(View.GONE);
        } else {
            holder.mLeadCard.setLongClickable(true);

            if (mData.get(position).getStatus().equals("true")) {

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
                    Intent intent = new Intent(mContext, LeadDashboard.class);
                    intent.putExtra("lid", mData.get(position).getLid());
                    intent.putExtra("name", mData.get(position).getName());
                    intent.putExtra("mobile", mData.get(position).getMobile());
                    intent.putExtra("location", mData.get(position).getLocation());
                    mContext.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount()
    {

        return mData.size();

    }

    public class LeadViewHolder extends RecyclerView.ViewHolder implements com.toshiwa.Adapter.LeadViewHolder, View.OnCreateContextMenuListener, View.OnClickListener,
    MenuItem.OnMenuItemClickListener {

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

            mLeadCard.setOnClickListener(this);
            mLeadCard.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Move to archive");
            MenuItem edit = menu.add(Menu.NONE,1,1,"Archive");

            edit.setOnMenuItemClickListener(onChange);
           }
        private final MenuItem.OnMenuItemClickListener onChange = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case 1:
                        int pos = getAdapterPosition();
                        deleteLead(pos);
                       // Toast.makeText(mContext,"Archive"+ mData.get(pos).getName(),Toast.LENGTH_LONG).show();
                        return true;

                }
                return false;
            }
        };

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            return false;
        }

        @Override
        public void onClick(View view) {

        }

        public void deleteLead(int i) {
            String lid = mData.get(i).getLid();

                Call<AddLeadResponse> leadListCall = ApiUtils.getAPIService().delete_lead(lid);
                leadListCall.enqueue(new Callback<AddLeadResponse>() {
                    @Override
                    public void onResponse(Call<AddLeadResponse> call, Response<AddLeadResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getCode().equals("200")) {
                                String resMess = response.body().getMessage();
                                Toast.makeText(mContext, resMess, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Oops..Try Again Later", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddLeadResponse> call, Throwable t) {
                        Toast.makeText(mContext, "Oops..Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                });

                mData.remove(i);
                notifyDataSetChanged();
            }

    }

}
