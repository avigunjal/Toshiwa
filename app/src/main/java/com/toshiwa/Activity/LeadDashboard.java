package com.toshiwa.Activity;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.toshiwa.CustomView.TextView_Bold;
import com.toshiwa.CustomView.TextView_Light;
import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.Model.Account.AccountResponse;
import com.toshiwa.Model.Lead.DisplayLeadListResult;
import com.toshiwa.Model.Status.StatusResponse;
import com.toshiwa.Model.Status.StatusResult;
import com.toshiwa.Utils.ApiUtils;
import com.toshiwa.toshiwa.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadDashboard extends AppCompatActivity implements View.OnClickListener{

    TextView_Regular mToolbarTitle;
    CardView mPurchaseCard, mApprovalCard, mMaterialCard, mExecutionCard, mCompletionCard, mBillCard,
    mAccountCard, mMeterReadingCard, mServiceCard;
    String leadName, leadMobile, leadLocation,leadId;
    TextView_Bold mName;
    TextView_Light mMobile,mLocation;
    String responseMessage;
    StatusResult statusResult;
    AppCompatImageView profile;
    DisplayLeadListResult displayLeadListResult;

    ProgressBar progressBar;
    View mPurchaseStatus, mApprovalStatus, mMaterialStatus, mExecutionStatus, mCompletionStatus,
    mBillStatus, mAccStatus, mMeterStatus, mServiceStatus;
    SeekBar mSeekBar;
    String  pPurchaseStatus="", pApprovalStatus="", pMaterialStatus="", pExecutionStatus="",
            pCompletionStatus="",pBillStatus="", pAccStatus="", pMeterStatus="", pServiceStatus="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_dashboard);
        displayLeadListResult = new DisplayLeadListResult();

        leadId = getIntent().getStringExtra("lid");
        leadName = getIntent().getStringExtra("name");
        leadMobile = getIntent().getStringExtra("mobile");
        leadLocation = getIntent().getStringExtra("location");

        init();
        formStatus();

        Toolbar toolbar = (Toolbar) findViewById(R.id.common_toolbar);
        mToolbarTitle = findViewById(R.id.tv_toolbar_title);
        setSupportActionBar(toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            mToolbarTitle.setText("Lead Profile");
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profileIntent = new Intent(LeadDashboard.this, FormActivity.class);
                    profileIntent.putExtra("lid",leadId);
                    profileIntent.putExtra("fragment_value","101");
                    startActivity(profileIntent);
                }
            });
    }

    public void init()
    {
        mName = findViewById(R.id.tv_lead_name);
        mLocation = findViewById(R.id.tv_lead_address);
        mMobile = findViewById(R.id.tv_lead_mobile);
        profile = findViewById(R.id.profile);

        mPurchaseCard = findViewById(R.id.cv_purchase);
        mApprovalCard = findViewById(R.id.cv_approval);
        mMaterialCard = findViewById(R.id.cv_material);
        mExecutionCard = findViewById(R.id.cv_execution);
        mCompletionCard = findViewById(R.id.cv_completion);
        mBillCard = findViewById(R.id.cv_bill);
        mAccountCard = findViewById(R.id.cv_account);
        mMeterReadingCard = findViewById(R.id.cv_meter);
        mServiceCard = findViewById(R.id.cv_service);

        mPurchaseCard.setOnClickListener(this);
        mApprovalCard.setOnClickListener(this);
        mMaterialCard.setOnClickListener(this);
        mExecutionCard.setOnClickListener(this);
        mCompletionCard.setOnClickListener(this);
        mBillCard.setOnClickListener(this);
        mAccountCard.setOnClickListener(this);
        mMeterReadingCard.setOnClickListener(this);
        mServiceCard.setOnClickListener(this);

        mName.setText(leadName);
        mLocation.setText(leadLocation);
        mMobile.setText(leadMobile);
        progressBar = findViewById(R.id.progress);

        mSeekBar          = findViewById(R.id.seekBar);
        mPurchaseStatus   = findViewById(R.id.purchase_status);
        mApprovalStatus   = findViewById(R.id.approval_status);
        mMaterialStatus   = findViewById(R.id.material_status);
        mExecutionStatus  = findViewById(R.id.execution_status);
        mCompletionStatus = findViewById(R.id.completion_status);
        mBillStatus       = findViewById(R.id.bill_status);
        mAccStatus        = findViewById(R.id.account_status);
        mMeterStatus      = findViewById(R.id.meter_status);
        mServiceStatus    = findViewById(R.id.service_status);

    }


    @Override
    public void onClick(View view) {
     switch (view.getId())
     {
         case R.id.cv_purchase:
             Intent inPurchase = new Intent(LeadDashboard.this, FormActivity.class);
             inPurchase.putExtra("fragment_value","1");
             inPurchase.putExtra("lid",leadId);
             inPurchase.putExtra("status",pPurchaseStatus);
             startActivity(inPurchase);break;
         case R.id.cv_approval:
             Intent inApproval = new Intent(this, FormActivity.class);
             inApproval.putExtra("fragment_value","2");
             inApproval.putExtra("lid",leadId);
             inApproval.putExtra("status",pApprovalStatus);
             startActivity(inApproval);break;
         case R.id.cv_material:
             Intent inMaterial = new Intent(this, FormActivity.class);
             inMaterial.putExtra("fragment_value","3");
             inMaterial.putExtra("lid",leadId);
             inMaterial.putExtra("status",pMaterialStatus);
             startActivity(inMaterial);break;
         case R.id.cv_execution:
             Intent inExecution = new Intent(this, FormActivity.class);
             inExecution.putExtra("fragment_value","4");
             inExecution.putExtra("lid",leadId);
             inExecution.putExtra("status",pExecutionStatus);
             startActivity(inExecution);break;
         case R.id.cv_completion:
             Intent inCompletion = new Intent(this, FormActivity.class);
             inCompletion.putExtra("fragment_value","5");
             inCompletion.putExtra("lid",leadId);
             inCompletion.putExtra("status",pCompletionStatus);
             startActivity(inCompletion);break;
         case R.id.cv_bill:
             Intent inBill = new Intent(this, FormActivity.class);
             inBill.putExtra("fragment_value","6");
             inBill.putExtra("lid",leadId);
             inBill.putExtra("status",pBillStatus);
             startActivity(inBill);break;
         case R.id.cv_account:
             Intent inAccount = new Intent(this, FormActivity.class);
             inAccount.putExtra("fragment_value","7");
             inAccount.putExtra("lid",leadId);
             inAccount.putExtra("status",pAccStatus);
             startActivity(inAccount);break;
         case R.id.cv_meter:
             Intent inMeter = new Intent(this, FormActivity.class);
             inMeter.putExtra("fragment_value","8");
             inMeter.putExtra("lid",leadId);
             inMeter.putExtra("status",pMeterStatus);
             startActivity(inMeter);break;
         case R.id.cv_service:
             Intent inService = new Intent(this, FormActivity.class);
             inService.putExtra("fragment_value","9");
             inService.putExtra("lid",leadId);
             inService.putExtra("status",pServiceStatus);
             startActivity(inService);break;
     }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lead_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.pdf);
                menuItem.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.call) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+leadMobile));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    public void formStatus(){
        progressBar.setVisibility(View.VISIBLE);
        Call<StatusResponse> statusResponseCall = ApiUtils.getAPIService().lead_status(Integer.parseInt(leadId));
        statusResponseCall.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("200")) {
                        progressBar.setVisibility(View.GONE);
                        responseMessage = response.body().getMessage();
                        statusResult = response.body().getStatusResult();

                        pPurchaseStatus = statusResult.getPurchase();
                        pApprovalStatus = statusResult.getApproval();
                        pMaterialStatus = statusResult.getMaterial();

                        pExecutionStatus = statusResult.getExecution();
                        pCompletionStatus = statusResult.getCompletion();
                        pBillStatus = statusResult.getBill();

                        pAccStatus = statusResult.getAccount();
                        pMeterStatus = statusResult.getMeterReading();
                        pServiceStatus = statusResult.getService();

                        leadStatus(statusResult);
                    } else if (response.body().getCode().equals("202")) {
                        progressBar.setVisibility(View.GONE);
                        responseMessage = response.body().getMessage();
                        Toast.makeText(LeadDashboard.this, responseMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LeadDashboard.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LeadDashboard.this, getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void leadStatus(StatusResult statusResult){
        if(!statusResult.getPurchase().equals("true")){
            mPurchaseStatus.setBackground(getResources().getDrawable(R.drawable.red_badge_dot));
            }
            else {
            mSeekBar.setProgress(1);
            mSeekBar.refreshDrawableState();
        }
        if(!statusResult.getApproval().equals("true")){
            mApprovalStatus.setBackground(getResources().getDrawable(R.drawable.red_badge_dot));
        }
        else {
            mSeekBar.setProgress(2);
            mSeekBar.refreshDrawableState();
        }
        if(!statusResult.getMaterial().equals("true")){
            mMaterialStatus.setBackground(getResources().getDrawable(R.drawable.red_badge_dot));
        }
        else {
            mSeekBar.setProgress(3);
            mSeekBar.refreshDrawableState();
        }
        if(!statusResult.getExecution().equals("true")){
            mExecutionStatus.setBackground(getResources().getDrawable(R.drawable.red_badge_dot));
        }
        else {
            mSeekBar.setProgress(4);
            mSeekBar.refreshDrawableState();
        }
        if(!statusResult.getCompletion().equals("true")){
            mCompletionStatus.setBackground(getResources().getDrawable(R.drawable.red_badge_dot));
        }
        else {
            mSeekBar.setProgress(5);
            mSeekBar.refreshDrawableState();
        }
        if(!statusResult.getBill().equals("true")){
            mBillStatus.setBackground(getResources().getDrawable(R.drawable.red_badge_dot));
        }
        else {
            mSeekBar.setProgress(6);
            mSeekBar.refreshDrawableState();
        }
        if(!statusResult.getAccount().equals("true")){
            mAccStatus.setBackground(getResources().getDrawable(R.drawable.red_badge_dot));
        }
        else {
            mSeekBar.setProgress(7);
            mSeekBar.refreshDrawableState();
        }
        if(!statusResult.getMeterReading().equals("true")){
            mMeterStatus.setBackground(getResources().getDrawable(R.drawable.red_badge_dot));
        }
        else {
            mSeekBar.setProgress(8);
            mSeekBar.refreshDrawableState();
        }
        if(!statusResult.getService().equals("true")){
            mServiceStatus.setBackground(getResources().getDrawable(R.drawable.red_badge_dot));
        }
        else {
            mSeekBar.setProgress(9);
            mSeekBar.refreshDrawableState();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
