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
import com.toshiwa.Model.Account.AccountResponse;
import com.toshiwa.Model.Account.AccountResult;
import com.toshiwa.Model.Approval.ApprovalResponse;
import com.toshiwa.Model.Bill.BillResponse;
import com.toshiwa.Model.Bill.BillResult;
import com.toshiwa.Model.Lead.AddLeadResponse;
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

public class AccountFormFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    LinearLayout mSave, mCancel;
    int pEmpid, pLid;
    String responseMessage, pRemark,
            pAmountCount, pPo, pAdvanceReceived;
    TextView_Regular mToolbarTitle;

    TextView_Regular mResponsiblePerson, mDate;
    Edittext_Light mRemark, mAmountCount, mPo;
    Checkbox_Regular mAdvanceReceived;
    AccountResult accountResult;
    boolean flag_completed=false;
    String status;
    String todayDate;
    LinearLayout loading;
    ScrollView sv;
    Boolean isAdmin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account_form,container,false);
        loading = view.findViewById(R.id.loading);
        sv = view.findViewById(R.id.scroll_view);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.common_toolbar);
        mToolbarTitle=view.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarTitle.setText("Account Details");
        View decorView = ((AppCompatActivity) getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        pLid = Integer.parseInt(getArguments().getString("lid"));
        status = getArguments().getString("status");
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        isAdmin = SharedPreferencesManager.getIsAdmin(getActivity());

        setHasOptionsMenu(true);
        if (!isNetworkAvailable(getActivity())) {
            showDialog(getActivity());

        }else {
            displayAccount(view);
        }
        init(view);
        return view;

    }

    public void init(View view)
    {
        mDate = view.findViewById(R.id.tv_date);
        mResponsiblePerson = view.findViewById(R.id.tv_responsible_person);

        mAmountCount = view.findViewById(R.id.et_amount);
        mPo           = view.findViewById(R.id.et_po);
        mRemark       = view.findViewById(R.id.et_remark);
        mAdvanceReceived          = view.findViewById(R.id.chk_advance);
        mRemark           = view.findViewById(R.id.et_remark);
        mResponsiblePerson.append(SharedPreferencesManager.getName(getActivity()));
        mDate.append(todayDate);

        mAdvanceReceived.setOnCheckedChangeListener(this);

        mSave = view.findViewById(R.id.ll_add);
        mCancel = view.findViewById(R.id.ll_cancel);


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(getActivity())) {
                    addAccount(view);
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

    public void addAccount(View view)
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        pEmpid = Integer.parseInt(SharedPreferencesManager.getEmpid(getActivity()));
        pRemark = mRemark.getText().toString();
        pAmountCount = mAmountCount.getText().toString();
        pPo = mPo.getText().toString();

        Call<AddLeadResponse> addBillCall = ApiUtils.getAPIService().add_account(pEmpid,pLid,
                pAmountCount,
                pPo,
                pAdvanceReceived,
                pRemark
          );
        addBillCall.enqueue(new Callback<AddLeadResponse>() {
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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();
        if(id == R.id.chk_advance)
        {
            pAdvanceReceived = (mAdvanceReceived.isChecked()) ? "yes" : "no";

        }
    }

    public void displayAccount(final View view) {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<AccountResponse> accountResponseCall = ApiUtils.getAPIService().display_account(pLid);
        accountResponseCall.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()) {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if (response.body().getCode().equals("200")) {
                        responseMessage = response.body().getMessage();
                        accountResult = response.body().getAccountResult();
                        if(!isAdmin){
                            formNotEdit(accountResult);
                        }else {
                            formEdit(accountResult);
                        }


                    } else if (response.body().getCode().equals("202")) {
                        sv.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);

                        responseMessage = response.body().getMessage();

                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);

                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void formNotEdit(AccountResult accountResult)
    {
        if(!accountResult.getAccCount().equals(""))
        {
            mAmountCount.setText(accountResult.getAccCount());
            mAmountCount.setFocusable(false);
            mAmountCount.setEnabled(false);
            mAmountCount.setTextColor(Color.DKGRAY);
        }
        if(!accountResult.getPoAmount().equals(""))
        {
            mPo.setText(accountResult.getPoAmount());
            mPo.setEnabled(false);
            mPo.setFocusable(false);
            mPo.setTextColor(Color.DKGRAY);
        }
        if(accountResult.getAdvanceReceived().equals("yes"))
        {
            mAdvanceReceived.setChecked(true);
            mAdvanceReceived.setEnabled(false);
            mAdvanceReceived.setTextColor(Color.DKGRAY);

        }
        if(!accountResult.getRemark().equals(""))
        {
            mRemark.setText(accountResult.getRemark());
            mRemark.setEnabled(false);
            mRemark.setFocusable(false);
            mRemark.setTextColor(Color.DKGRAY);

        }

    }

    public void formEdit(AccountResult accountResult)
    {
        if(!accountResult.getAccCount().equals(""))
        {
            mAmountCount.setText(accountResult.getAccCount());
            mAmountCount.setTextColor(Color.DKGRAY);
        }
        if(!accountResult.getPoAmount().equals(""))
        {
            mPo.setText(accountResult.getPoAmount());
            mPo.setTextColor(Color.DKGRAY);
        }
        if(accountResult.getAdvanceReceived().equals("yes"))
        {
            mAdvanceReceived.setChecked(true);
            mAdvanceReceived.setTextColor(Color.DKGRAY);

        }
        if(!accountResult.getRemark().equals(""))
        {
            mRemark.setText(accountResult.getRemark());
            mRemark.setTextColor(Color.DKGRAY);

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
        Call<AccountResponse> approvalResponseCall = ApiUtils.getAPIService().account_status(pLid,status);
        approvalResponseCall.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
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
            public void onFailure(Call<AccountResponse> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
