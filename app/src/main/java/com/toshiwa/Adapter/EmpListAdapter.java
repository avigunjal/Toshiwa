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
import com.toshiwa.CustomView.TextView_Light;
import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.Model.Employee.DisplayEmployeeResult;
import com.toshiwa.Model.Lead.AddLeadResponse;
import com.toshiwa.Model.Lead.DisplayLeadListResult;
import com.toshiwa.Utils.ApiUtils;
import com.toshiwa.toshiwa.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpListAdapter extends RecyclerView.Adapter<EmpListAdapter.EmpViewHolder> {

    List<DisplayEmployeeResult> mData;
    Context mContext;

    public EmpListAdapter(Context context, List<DisplayEmployeeResult> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public EmpViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_emp_list, viewGroup, false);
        return new EmpViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull final EmpViewHolder holder, final int position) {
        holder.mName.append(mData.get(position).getName());
        holder.mMobile.append(mData.get(position).getMobile());
        holder.mPassword.append(mData.get(position).getPassword());


    }


    @Override
    public int getItemCount() {

        return mData.size();

    }

    public class EmpViewHolder extends RecyclerView.ViewHolder {

        protected TextView_Regular mName, mMobile, mPassword;
         CardView mLeadCard;

        public EmpViewHolder(View view) {
            super(view);

            this.mLeadCard = (CardView) view.findViewById(R.id.lead_card);
            this.mName = (TextView_Regular) view.findViewById(R.id.tv_customer);
            this.mPassword = (TextView_Regular) view.findViewById(R.id.tv_password);
            this.mMobile = (TextView_Regular) view.findViewById(R.id.tv_mobile);
            }


    }
}
