package com.toshiwa.Activity;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.toshiwa.Adapter.CommonListAdapter;
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


public class CommonList extends AppCompatActivity {
    RecyclerView mRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    TextView_Regular mTitleToolbar;
    CommonListAdapter commonListAdapter;
    String empid;
    String id;
    LinearLayout loading, nodata;
    NestedScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list);
        mTitleToolbar = findViewById(R.id.tv_toolbar_title);

        loading = findViewById(R.id.loading);
        nodata = findViewById(R.id.nodata);

        sv = findViewById(R.id.scroll_view);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_lead_list);
        progressBar = findViewById(R.id.pd);
        empid = SharedPreferencesManager.getEmpid(getApplicationContext());
        //overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);


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
        switch (id){
            case "purchase": getPurchase();break;
            case "approval": getApproval();break;
            case "material": getMaterial();break;

            case "execution": getExecution();break;
            case "completion": getCompletion();break;
            case "bill": getBill();break;

            case "account": getAccount();break;
            case "meter": getMeter();break;
            case "service": getService();break;
        }


    }

    public void getPurchase()
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<DisplayLeadList> leadListCall = ApiUtils.getAPIService().admin_purchase();
        leadListCall.enqueue(new Callback<DisplayLeadList>() {
            @Override
            public void onResponse(Call<DisplayLeadList> call, Response<DisplayLeadList> response) {
                if(response.isSuccessful())
                {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if(response.body().getCode().equals("200"))
                    {
                        List<DisplayLeadListResult> displayLeadListResult = response.body().getResult();
                        commonListAdapter = new CommonListAdapter(CommonList.this,displayLeadListResult,id );
                        mRecyclerView.setAdapter(commonListAdapter);

                    }else {
                        nodata.setVisibility(View.VISIBLE);
                        Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DisplayLeadList> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);

                Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getApproval()
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<DisplayLeadList> leadListCall = ApiUtils.getAPIService().admin_approval();
        leadListCall.enqueue(new Callback<DisplayLeadList>() {
            @Override
            public void onResponse(Call<DisplayLeadList> call, Response<DisplayLeadList> response) {
                if(response.isSuccessful())
                {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if(response.body().getCode().equals("200"))
                    {
                        List<DisplayLeadListResult> displayLeadListResult = response.body().getResult();
                        commonListAdapter = new CommonListAdapter(CommonList.this,displayLeadListResult,id );
                        mRecyclerView.setAdapter(commonListAdapter);

                    }else {
                        nodata.setVisibility(View.VISIBLE);
                        Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DisplayLeadList> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getMaterial()
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<DisplayLeadList> leadListCall = ApiUtils.getAPIService().admin_material();
        leadListCall.enqueue(new Callback<DisplayLeadList>() {
            @Override
            public void onResponse(Call<DisplayLeadList> call, Response<DisplayLeadList> response) {
                if(response.isSuccessful())
                {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if(response.body().getCode().equals("200"))
                    {
                        List<DisplayLeadListResult> displayLeadListResult = response.body().getResult();
                        commonListAdapter = new CommonListAdapter(CommonList.this,displayLeadListResult,id );
                        mRecyclerView.setAdapter(commonListAdapter);

                    }else {
                        nodata.setVisibility(View.VISIBLE);
                        Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DisplayLeadList> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);

                Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getExecution()
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<DisplayLeadList> leadListCall = ApiUtils.getAPIService().admin_execution();
        leadListCall.enqueue(new Callback<DisplayLeadList>() {
            @Override
            public void onResponse(Call<DisplayLeadList> call, Response<DisplayLeadList> response) {
                if(response.isSuccessful())
                {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if(response.body().getCode().equals("200"))
                    {
                        List<DisplayLeadListResult> displayLeadListResult = response.body().getResult();
                        commonListAdapter = new CommonListAdapter(CommonList.this,displayLeadListResult,id );
                        mRecyclerView.setAdapter(commonListAdapter);

                    }else {
                        nodata.setVisibility(View.VISIBLE);
                        Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DisplayLeadList> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getCompletion()
    {
        sv.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);

        Call<DisplayLeadList> leadListCall = ApiUtils.getAPIService().admin_completion();
        leadListCall.enqueue(new Callback<DisplayLeadList>() {
            @Override
            public void onResponse(Call<DisplayLeadList> call, Response<DisplayLeadList> response) {
                if(response.isSuccessful())
                {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if(response.body().getCode().equals("200"))
                    {
                        List<DisplayLeadListResult> displayLeadListResult = response.body().getResult();
                        commonListAdapter = new CommonListAdapter(CommonList.this,displayLeadListResult,id );
                        mRecyclerView.setAdapter(commonListAdapter);

                    }else {nodata.setVisibility(View.VISIBLE);
                        Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DisplayLeadList> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getBill()
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<DisplayLeadList> leadListCall = ApiUtils.getAPIService().admin_bill();
        leadListCall.enqueue(new Callback<DisplayLeadList>() {
            @Override
            public void onResponse(Call<DisplayLeadList> call, Response<DisplayLeadList> response) {
                if(response.isSuccessful())
                {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if(response.body().getCode().equals("200"))
                    {
                        List<DisplayLeadListResult> displayLeadListResult = response.body().getResult();
                        commonListAdapter = new CommonListAdapter(CommonList.this,displayLeadListResult,id );
                        mRecyclerView.setAdapter(commonListAdapter);

                    }else {nodata.setVisibility(View.VISIBLE);
                        Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DisplayLeadList> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getMeter()
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<DisplayLeadList> leadListCall = ApiUtils.getAPIService().admin_meter();
        leadListCall.enqueue(new Callback<DisplayLeadList>() {
            @Override
            public void onResponse(Call<DisplayLeadList> call, Response<DisplayLeadList> response) {
                if(response.isSuccessful())
                {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if(response.body().getCode().equals("200"))
                    {
                        List<DisplayLeadListResult> displayLeadListResult = response.body().getResult();
                        commonListAdapter = new CommonListAdapter(CommonList.this,displayLeadListResult,id );
                        mRecyclerView.setAdapter(commonListAdapter);

                    }else {
                        nodata.setVisibility(View.VISIBLE);
                        Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DisplayLeadList> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getAccount()
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<DisplayLeadList> leadListCall = ApiUtils.getAPIService().admin_account();
        leadListCall.enqueue(new Callback<DisplayLeadList>() {
            @Override
            public void onResponse(Call<DisplayLeadList> call, Response<DisplayLeadList> response) {
                if(response.isSuccessful())
                {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if(response.body().getCode().equals("200"))
                    {
                        List<DisplayLeadListResult> displayLeadListResult = response.body().getResult();
                        commonListAdapter = new CommonListAdapter(CommonList.this,displayLeadListResult,id );
                        mRecyclerView.setAdapter(commonListAdapter);

                    }else {nodata.setVisibility(View.VISIBLE);
                        Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DisplayLeadList> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getService()
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<DisplayLeadList> leadListCall = ApiUtils.getAPIService().admin_service();
        leadListCall.enqueue(new Callback<DisplayLeadList>() {
            @Override
            public void onResponse(Call<DisplayLeadList> call, Response<DisplayLeadList> response) {
                if(response.isSuccessful())
                {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if(response.body().getCode().equals("200"))
                    {
                        List<DisplayLeadListResult> displayLeadListResult = response.body().getResult();
                        commonListAdapter = new CommonListAdapter(CommonList.this,displayLeadListResult,id );
                        mRecyclerView.setAdapter(commonListAdapter);

                    }else {nodata.setVisibility(View.VISIBLE);
                        Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DisplayLeadList> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                nodata.setVisibility(View.VISIBLE);
                Toast.makeText(CommonList.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
           onBackPressed();
           return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
