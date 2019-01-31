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
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.toshiwa.Adapter.EmpListAdapter;
import com.toshiwa.Adapter.LeadListAdapter;
import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.Model.Employee.DisplayEmployee;
import com.toshiwa.Model.Employee.DisplayEmployeeResult;
import com.toshiwa.Model.Lead.DisplayLeadList;
import com.toshiwa.Model.Lead.DisplayLeadListResult;
import com.toshiwa.Preferences.SharedPreferencesManager;
import com.toshiwa.Utils.ApiUtils;
import com.toshiwa.toshiwa.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Employee extends AppCompatActivity {
    RecyclerView mRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    TextView_Regular mTitleToolbar;
    EmpListAdapter empListAdapter;
    String empid;
    LinearLayout loading;
    RelativeLayout sv;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_list);
        mTitleToolbar = findViewById(R.id.tv_toolbar_title);
        loading = findViewById(R.id.loading);
        sv = findViewById(R.id.scroll_view);

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
        ab.setTitle("Employee List");
        mTitleToolbar.setText("Employee List");

        getEmpData();

    }


    public void getEmpData()
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<DisplayEmployee> leadListCall = ApiUtils.getAPIService().display_emp();
        leadListCall.enqueue(new Callback<DisplayEmployee>() {
            @Override
            public void onResponse(Call<DisplayEmployee> call, Response<DisplayEmployee> response) {
                if(response.isSuccessful())
                {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if(response.body().getCode().equals("200"))
                    {
                        List<DisplayEmployeeResult> displayEmployeeResults = response.body().getResult();
                        empListAdapter = new EmpListAdapter(Employee.this,displayEmployeeResults );
                        mRecyclerView.setAdapter(empListAdapter);

                    }else {
                        Toast.makeText(Employee.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DisplayEmployee> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);

                Toast.makeText(Employee.this, "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               onBackPressed();
               return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
