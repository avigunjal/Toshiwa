package com.toshiwa.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.toshiwa.Adapter.LeadListAdapter;
import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.Model.Lead.DisplayLeadList;
import com.toshiwa.Model.Lead.DisplayLeadListResult;
import com.toshiwa.Preferences.SharedPreferencesManager;
import com.toshiwa.Utils.ApiUtils;
import com.toshiwa.toshiwa.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadList extends AppCompatActivity {
    RecyclerView mRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    TextView_Regular mTitleToolbar;
    LeadListAdapter leadListAdapter;
    String empid;
    LinearLayout loading, nodata;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_list);
        mTitleToolbar = findViewById(R.id.tv_toolbar_title);
        loading = findViewById(R.id.loading);
        nodata = findViewById(R.id.nodata);

        Intent intent = getIntent();

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_lead_list);
        progressBar = findViewById(R.id.pd);
        empid = SharedPreferencesManager.getEmpid(getApplicationContext());
        //overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        registerForContextMenu(mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.common_toolbar);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Lead List");
        mTitleToolbar.setText("Lead List");

        getLeadData();


    }


    public void getLeadData()
    {
        loading.setVisibility(View.VISIBLE);
        Call<DisplayLeadList> leadListCall = ApiUtils.getAPIService().display_leads(empid);
        leadListCall.enqueue(new Callback<DisplayLeadList>() {
            @Override
            public void onResponse(Call<DisplayLeadList> call, Response<DisplayLeadList> response) {
                if(response.isSuccessful())
                {
                    loading.setVisibility(View.GONE);

                    if(response.body().getCode().equals("200"))
                    {
                        List<DisplayLeadListResult> displayLeadListResult = response.body().getResult();
                        leadListAdapter = new LeadListAdapter(LeadList.this,displayLeadListResult );
                        mRecyclerView.setAdapter(leadListAdapter);

                    }else {
                        nodata.setVisibility(View.VISIBLE);
                        Toast.makeText(LeadList.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DisplayLeadList> call, Throwable t) {
                loading.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                Toast.makeText(LeadList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
