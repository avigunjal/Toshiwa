package com.toshiwa.Fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.toshiwa.CustomView.AlertMessage;
import com.toshiwa.CustomView.Checkbox_Regular;
import com.toshiwa.CustomView.Edittext_Light;
import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.Model.Lead.AddLeadResponse;
import com.toshiwa.Model.Approval.ApprovalResponse;
import com.toshiwa.Model.Approval.ApprovalResult;
import com.toshiwa.Model.Purchase.PurchaseResponse;
import com.toshiwa.Preferences.SharedPreferencesManager;
import com.toshiwa.Utils.ApiUtils;
import com.toshiwa.toshiwa.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.toshiwa.InternetConnection.DialogNoConnection.showDialog;
import static com.toshiwa.InternetConnection.InternetConnection.isNetworkAvailable;

public class ApprovalFormFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    String todayDate;
    TextView_Regular  mDate, mResponsiblePerson;
    LinearLayout mSave, mCancel;
    TextView_Regular mToolbarTitle;
    Checkbox_Regular mLoadDone,mLoadReflection, mQuotationDone, mSolarOnlineAppDone, mSolarOfflineAppDone,
    mSanctionReceived, mMedaAppDone, mMedaSanctionReceived, mJointInspection, mPcr, mFundReleased;

    Edittext_Light mNetMeterNo, mGenerationMeterNo;

    String pLoadDone,pLoadReflection, pQuotationDone, pSolarOnlineAppDone, pSolarOfflineAppDone,
    pSanctionReceived, pMedaAppDone, pMedaSanctionReceived, pJointInspection, pPcr, pFundReleased,
    pNetMeterNo, pGenerationMeterNo,pResponsiblePerson,pDate;

    String responseMessage;
    ApprovalResult approvalResult;
    int pEmpid, pLid;
    boolean flag_completed=false;
    String status;
    LinearLayout loading;
    ScrollView sv;
Boolean isAdmin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_approval_form,container,false);
        loading = view.findViewById(R.id.loading);
        sv = view.findViewById(R.id.scroll_view);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.common_toolbar);
        mToolbarTitle=view.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarTitle.setText("Approval Details");
        View decorView = ((AppCompatActivity) getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        pLid = Integer.parseInt(getArguments().getString("lid"));
        status = getArguments().getString("status");
        isAdmin = SharedPreferencesManager.getIsAdmin(getActivity());

        setHasOptionsMenu(true);
        if (!isNetworkAvailable(getActivity())) {
            showDialog(getActivity());

        }else {
            displayApproval(view);
        }
        init(view);

        return view;

    }

    public void init(View view)
    {
        mDate = view.findViewById(R.id.tv_date);
        mResponsiblePerson = view.findViewById(R.id.tv_responsible_person);
        mResponsiblePerson.append(SharedPreferencesManager.getName(getActivity()));


        mLoadDone = view.findViewById(R.id.chk_load_done);
        mLoadReflection = view.findViewById(R.id.chk_load_reflection);
        mQuotationDone = view.findViewById(R.id.chk_quotation_done);
        mSolarOnlineAppDone  = view.findViewById(R.id.chk_solar_online_app_done);
        mSolarOfflineAppDone = view.findViewById(R.id.chk_solar_offline_app_done);
        mSanctionReceived = view.findViewById(R.id.chk_sanction_received);
        mMedaAppDone = view.findViewById(R.id.chk_meda_app_done);
        mMedaSanctionReceived = view.findViewById(R.id.chk_meda_sanction_received);
        mJointInspection = view.findViewById(R.id.chk_joint_inspection);
        mPcr = view.findViewById(R.id.chk_pcr);
        mFundReleased = view.findViewById(R.id.chk_fund_released);

        mNetMeterNo = view.findViewById(R.id.et_net_meter_no);
        mGenerationMeterNo = view.findViewById(R.id.et_generation_meter_no);

        mLoadDone.setOnCheckedChangeListener(this);
        mLoadReflection.setOnCheckedChangeListener(this);
        mQuotationDone.setOnCheckedChangeListener(this);
        mSolarOnlineAppDone.setOnCheckedChangeListener(this);
        mSolarOfflineAppDone.setOnCheckedChangeListener(this);
        mSanctionReceived.setOnCheckedChangeListener(this);
        mMedaAppDone.setOnCheckedChangeListener(this);
        mMedaSanctionReceived.setOnCheckedChangeListener(this);
        mJointInspection.setOnCheckedChangeListener(this);
        mPcr.setOnCheckedChangeListener(this);
        mFundReleased.setOnCheckedChangeListener(this);

        mDate.append(todayDate);

        mSave = view.findViewById(R.id.ll_add);
        mCancel = view.findViewById(R.id.ll_cancel);

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(getActivity())) {
                    addApproval(view);
                } else {

                    showDialog(getActivity());

                }

            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();
        switch(id)
        {
            case R.id.chk_load_done:
            pLoadDone = (mLoadDone.isChecked())?"yes":"no";
            break;
            case R.id.chk_load_reflection:
                pLoadReflection = (mLoadReflection.isChecked())?"yes":"no";
                break;
            case R.id.chk_quotation_done:
                pQuotationDone = (mQuotationDone.isChecked())?"yes":"no";
                break;
            case R.id.chk_solar_online_app_done:
                pSolarOnlineAppDone = (mSolarOnlineAppDone.isChecked())?"yes":"no";
                break;
            case R.id.chk_solar_offline_app_done:
                pSolarOfflineAppDone = (mSolarOfflineAppDone.isChecked())?"yes":"no";
                break;
            case R.id.chk_sanction_received:
                pSanctionReceived = (mSanctionReceived.isChecked())?"yes":"no";
                break;
            case R.id.chk_meda_app_done:
                pMedaAppDone = (mMedaAppDone.isChecked())?"yes":"no";
                break;
            case R.id.chk_meda_sanction_received:
                pMedaSanctionReceived = (mMedaSanctionReceived.isChecked())?"yes":"no";
                break;
            case R.id.chk_joint_inspection:
                pJointInspection = (mJointInspection.isChecked())?"yes":"no";
                break;
            case R.id.chk_pcr:
                pPcr = (mPcr.isChecked())?"yes":"no";
                break;
            case R.id.chk_fund_released:
                pFundReleased = (mFundReleased.isChecked())?"yes":"no";
                break;


        }
    }

    public void addApproval(final View view) {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        pEmpid = Integer.parseInt(SharedPreferencesManager.getEmpid(getActivity()));
        pResponsiblePerson= SharedPreferencesManager.getName(getActivity());
        pDate = todayDate;

        pDate = todayDate;
        mResponsiblePerson.append(SharedPreferencesManager.getName(getActivity()));
        // pResponsiblePerson = mResponsiblePerson.getText().toString();
        pNetMeterNo        = mNetMeterNo.getText().toString();
        pGenerationMeterNo = mGenerationMeterNo.getText().toString();

        Call<AddLeadResponse> addApprovalCall = ApiUtils.getAPIService().add_approval(pEmpid,pLid,
                pResponsiblePerson,
                pDate,
                pLoadDone,
                pLoadReflection,
                pQuotationDone,
                pSolarOnlineAppDone,
                pSolarOfflineAppDone,
                pSanctionReceived, pMedaAppDone, pMedaSanctionReceived,pJointInspection,pPcr,
                pFundReleased, pNetMeterNo,pGenerationMeterNo);
        addApprovalCall.enqueue(new Callback<AddLeadResponse>() {
            @Override
            public void onResponse(Call<AddLeadResponse> call, Response<AddLeadResponse> response) {
                if (response.isSuccessful()) {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                     if (response.body().getCode().equals("200")) {

                        responseMessage = response.body().getMessage();
                        AlertMessage.alertMessage(getActivity(),"Great Job!",responseMessage);

                    } else if (response.body().getCode().equals("202")) {

                        responseMessage = response.body().getMessage();

                        Toast.makeText(getActivity(),responseMessage, Toast.LENGTH_SHORT).show();


                    }
                } else {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddLeadResponse> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);

                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void displayApproval(final View view) {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<ApprovalResponse> approvalResponseCall = ApiUtils.getAPIService().display_approval(pLid);
        approvalResponseCall.enqueue(new Callback<ApprovalResponse>() {
            @Override
            public void onResponse(Call<ApprovalResponse> call, Response<ApprovalResponse> response) {
                if (response.isSuccessful()) {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if (response.body().getCode().equals("200")) {
                        responseMessage = response.body().getMessage();
                        approvalResult = response.body().getApprovalResult();
                        if(!isAdmin){
                            formNotEdit(approvalResult);
                        }else {
                            formEdit(approvalResult);
                        }


                    } else if (response.body().getCode().equals("202")) {

                        responseMessage = response.body().getMessage();

                    }
                } else {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApprovalResponse> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);

                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void formNotEdit(ApprovalResult approvalResult)
    {
        if(approvalResult.getLoadExtensionDone().equals("yes") || approvalResult.getLoadExtensionDone().equals("no"))
        {
            mLoadDone.setChecked(true);
            mLoadDone.setEnabled(false);

        }

        if(approvalResult.getLoadReflectionBill().equals("yes") || approvalResult.getLoadReflectionBill().equals("no"))
        {
            mLoadReflection.setChecked(true);
            mLoadReflection.setEnabled(false);
        }

        if(approvalResult.getQuotationPaid().equals("yes") || approvalResult.getQuotationPaid().equals("no") )
        {
            mQuotationDone.setChecked(true);
            mQuotationDone.setEnabled(false);
        }
         if(approvalResult.getSolarOnlineApplication().equals("yes") || approvalResult.getSolarOnlineApplication().equals("no"))
        {
            mSolarOnlineAppDone.setChecked(true);
            mSolarOnlineAppDone.setEnabled(false);
        }
        if(approvalResult.getSolarOfflineApplication().equals("yes") || approvalResult.getSolarOfflineApplication().equals("no"))
        {
            mSolarOfflineAppDone.setChecked(true);
            mSolarOfflineAppDone.setEnabled(false);
        }

        if(approvalResult.getSolarSanctionReceived().equals("yes") || approvalResult.getSolarSanctionReceived().equals("no"))
        {
            mSanctionReceived.setChecked(true);
            mSanctionReceived.setEnabled(false);
        }
        if(approvalResult.getMedaApplicationDone().equals("yes") || approvalResult.getMedaApplicationDone().equals("no"))
        {
            mMedaAppDone.setChecked(true);
            mMedaAppDone.setEnabled(false);
        }
        if(approvalResult.getMedaSanctionReceived().equals("yes") || approvalResult.getMedaSanctionReceived().equals("no"))
        {
            mMedaSanctionReceived.setChecked(true);
            mMedaSanctionReceived.setEnabled(false);
        }

        if(approvalResult.getJointInspection().equals("yes") || approvalResult.getJointInspection().equals("no"))
        {
            mJointInspection.setChecked(true);
            mJointInspection.setEnabled(false);
        }
        if(approvalResult.getPcrEntered().equals("yes") || approvalResult.getPcrEntered().equals("no"))
        {
            mPcr.setChecked(true);
            mPcr.setEnabled(false);
        }
        if(approvalResult.getFundsReleased().equals("yes") || approvalResult.getFundsReleased().equals("no"))
        {
            mFundReleased.setChecked(true);
            mFundReleased.setEnabled(false);
        }

        if(!approvalResult.getNetMeterNo().equals(""))
        {
            mNetMeterNo.setText(approvalResult.getNetMeterNo());
            mNetMeterNo.setEnabled(false);
            mNetMeterNo.setFocusable(false);
            mNetMeterNo.setTextColor(Color.DKGRAY);
        }

        if(!approvalResult.getGenerationMeterNo().equals(""))
        {
            mGenerationMeterNo.setText(approvalResult.getGenerationMeterNo());
            mGenerationMeterNo.setEnabled(false);
            mGenerationMeterNo.setFocusable(false);
            mGenerationMeterNo.setTextColor(Color.DKGRAY);

        }
    }

    public void formEdit(ApprovalResult approvalResult)
    {
        if(approvalResult.getLoadExtensionDone().equals("yes") || approvalResult.getLoadExtensionDone().equals("no"))
        {
            mLoadDone.setChecked(true);

        }

        if(approvalResult.getLoadReflectionBill().equals("yes") || approvalResult.getLoadReflectionBill().equals("no"))
        {
            mLoadReflection.setChecked(true);
        }

        if(approvalResult.getQuotationPaid().equals("yes") || approvalResult.getQuotationPaid().equals("no") )
        {
            mQuotationDone.setChecked(true);
        }
        if(approvalResult.getSolarOnlineApplication().equals("yes") || approvalResult.getSolarOnlineApplication().equals("no"))
        {
            mSolarOnlineAppDone.setChecked(true);
        }
        if(approvalResult.getSolarOfflineApplication().equals("yes") || approvalResult.getSolarOfflineApplication().equals("no"))
        {
            mSolarOfflineAppDone.setChecked(true);
        }

        if(approvalResult.getSolarSanctionReceived().equals("yes") || approvalResult.getSolarSanctionReceived().equals("no"))
        {
            mSanctionReceived.setChecked(true);
        }
        if(approvalResult.getMedaApplicationDone().equals("yes") || approvalResult.getMedaApplicationDone().equals("no"))
        {
            mMedaAppDone.setChecked(true);
        }
        if(approvalResult.getMedaSanctionReceived().equals("yes") || approvalResult.getMedaSanctionReceived().equals("no"))
        {
            mMedaSanctionReceived.setChecked(true);
        }

        if(approvalResult.getJointInspection().equals("yes") || approvalResult.getJointInspection().equals("no"))
        {
            mJointInspection.setChecked(true);
        }
        if(approvalResult.getPcrEntered().equals("yes") || approvalResult.getPcrEntered().equals("no"))
        {
            mPcr.setChecked(true);
        }
        if(approvalResult.getFundsReleased().equals("yes") || approvalResult.getFundsReleased().equals("no"))
        {
            mFundReleased.setChecked(true);
        }

        if(!approvalResult.getNetMeterNo().equals(""))
        {
            mNetMeterNo.setText(approvalResult.getNetMeterNo());
            mNetMeterNo.setTextColor(Color.DKGRAY);
        }

        if(!approvalResult.getGenerationMeterNo().equals(""))
        {
            mGenerationMeterNo.setText(approvalResult.getGenerationMeterNo());
            mGenerationMeterNo.setTextColor(Color.DKGRAY);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if(status.equals("true")){
            MenuItem menuItem = menu.findItem(R.id.completed);
            menuItem.setVisible(true);
            MenuItem menuItem1 = menu.findItem(R.id.mark);
            menuItem1.setVisible(false);
        }else {
            MenuItem menuItem = menu.findItem(R.id.completed);
            menuItem.setVisible(false);
            MenuItem menuItem1 = menu.findItem(R.id.mark);
            menuItem1.setVisible(true);
        }

        super.onCreateOptionsMenu(menu, menuInflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if(id == android.R.id.home) {
            getActivity().onBackPressed();
        }
        if (id == R.id.mark) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(R.string.completed);
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                }
            });
            builder.setPositiveButton("Completed", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    updateStatus("true");
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        return false;
    }

    public void updateStatus(String status){
        Call<ApprovalResponse> approvalResponseCall = ApiUtils.getAPIService().approval_status(pLid,status);
        approvalResponseCall.enqueue(new Callback<ApprovalResponse>() {
            @Override
            public void onResponse(Call<ApprovalResponse> call, Response<ApprovalResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("200")) {
                        responseMessage = response.body().getMessage();
                        Toast.makeText(getContext(), "Status Updated", Toast.LENGTH_SHORT).show();
                          flag_completed = true;
                    } else if (response.body().getCode().equals("202")) {

                        responseMessage = response.body().getMessage();
                        Toast.makeText(getContext(), responseMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApprovalResponse> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


