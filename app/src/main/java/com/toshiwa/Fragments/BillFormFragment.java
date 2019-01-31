package com.toshiwa.Fragments;

import android.content.DialogInterface;
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
import com.toshiwa.Model.Approval.ApprovalResponse;
import com.toshiwa.Model.Bill.BillResponse;
import com.toshiwa.Model.Bill.BillResult;
import com.toshiwa.Model.Execution.ExecutionResponse;
import com.toshiwa.Model.Execution.ExecutionResult;
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

public class BillFormFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    LinearLayout mSave, mCancel;
    int pEmpid, pLid;
    String todayDate;
    String responseMessage, pResponsiblePerson, pRemark,
            pInstallationDate, pTaggedDate, pBillRec, pTagged;
    TextView_Regular mToolbarTitle, mResposniblePerson, mDate;
    Edittext_Light mRemark, mInstallationDate, mTaggedDate;
    Checkbox_Regular mBillRec, mTagged;
    BillResult billResult;
    boolean flag_completed=false;
    String status;
    LinearLayout loading;
    ScrollView sv;
Boolean isAdmin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bill_form,container,false);
        loading = view.findViewById(R.id.loading);
        sv = view.findViewById(R.id.scroll_view);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.common_toolbar);
        mToolbarTitle=view.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarTitle.setText("MSEDCL Bill");
        View decorView = ((AppCompatActivity) getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
isAdmin = SharedPreferencesManager.getIsAdmin(getActivity());
        setHasOptionsMenu(true);

        pLid = Integer.parseInt(getArguments().getString("lid"));
        status = getArguments().getString("status");

        if (!isNetworkAvailable(getActivity())) {
            showDialog(getActivity());

        }else {
            displayBill(view);
        }
        init(view);
        return view;

    }

    public void init(View view)
    {

        mInstallationDate = view.findViewById(R.id.et_installation_date);
        mTagged           = view.findViewById(R.id.chk_tagged);
        mTaggedDate       = view.findViewById(R.id.et_tagged_date);
        mBillRec          = view.findViewById(R.id.chk_bill_rec);
        mRemark           = view.findViewById(R.id.et_remark);
        mResposniblePerson = view.findViewById(R.id.tv_responsible_person);
        mDate = view.findViewById(R.id.tv_date);

        mDate.append(todayDate);
        mResposniblePerson.append(SharedPreferencesManager.getName(getActivity()));

        mBillRec.setOnCheckedChangeListener(this);
        mTagged.setOnCheckedChangeListener(this);

        mSave = view.findViewById(R.id.ll_add);
        mCancel = view.findViewById(R.id.ll_cancel);


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(getActivity())) {
                    addBill(view);
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
        switch(id) {
            case R.id.chk_tagged:
                pTagged = (mTagged.isChecked()) ? "yes" : "no";
                break;
            case R.id.chk_bill_rec:
                pBillRec = (mBillRec.isChecked()) ? "yes" : "no";
                break;

        }
    }

    public void addBill(View view)
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        pEmpid = Integer.parseInt(SharedPreferencesManager.getEmpid(getActivity()));
        pResponsiblePerson= SharedPreferencesManager.getName(getActivity());
        // pResponsiblePerson = mResponsiblePerson.getText().toString();
        pInstallationDate = mInstallationDate.getText().toString();
      //  pTagged
       // pBillRec
        pTaggedDate = mTaggedDate.getText().toString();
        pRemark = mRemark.getText().toString();


        Call<AddLeadResponse> addBillCall = ApiUtils.getAPIService().add_bill(pEmpid,pLid,
                pResponsiblePerson,
                pInstallationDate,
                pTagged,
                pTaggedDate,
                pBillRec,
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
                        sv.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);

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

    public void displayBill(final View view) {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<BillResponse> billResponseCall = ApiUtils.getAPIService().display_bill(pLid);
        billResponseCall.enqueue(new Callback<BillResponse>() {
            @Override
            public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                if (response.isSuccessful()) {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if (response.body().getCode().equals("200")) {
                        responseMessage = response.body().getMessage();
                        billResult = response.body().getBillResult();
                        if(!isAdmin){
                            formNotEdit(billResult);
                        }else {
                            formEdit(billResult);
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
            public void onFailure(Call<BillResponse> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);

                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void formNotEdit(BillResult billResult)
    {
        if(!billResult.getMeterInstallationDate().equals(""))
        {
            mInstallationDate.setText(billResult.getMeterInstallationDate());
            mInstallationDate.setEnabled(false);
            mInstallationDate.setFocusable(false);

        }
        if(billResult.getSolarTagged().equals("yes"))
        {
            mTagged.setChecked(true);
            mTagged.setEnabled(false);

        }
        if(billResult.getFirstBillReceived().equals("yes"))
        {
            mBillRec.setChecked(true);
            mBillRec.setEnabled(false);

        }
        if(!billResult.getSolarTaggedDate().equals(""))
        {
            mTaggedDate.setText(billResult.getSolarTaggedDate());
            mTaggedDate.setEnabled(false);
            mTaggedDate.setFocusable(false);

        }
        if(!billResult.getRemark().equals(""))
        {
            mRemark.setText(billResult.getRemark());
            mRemark.setEnabled(false);
            mRemark.setFocusable(false);

        }

    }

    public void formEdit(BillResult billResult)
    {
        if(!billResult.getMeterInstallationDate().equals(""))
        {
            mInstallationDate.setText(billResult.getMeterInstallationDate());


        }
        if(billResult.getSolarTagged().equals("yes"))
        {
            mTagged.setChecked(true);


        }
        if(billResult.getFirstBillReceived().equals("yes"))
        {
            mBillRec.setChecked(true);


        }
        if(!billResult.getSolarTaggedDate().equals(""))
        {
            mTaggedDate.setText(billResult.getSolarTaggedDate());


        }
        if(!billResult.getRemark().equals(""))
        {
            mRemark.setText(billResult.getRemark());


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
        Call<BillResponse> approvalResponseCall = ApiUtils.getAPIService().bill_status(pLid,status);
        approvalResponseCall.enqueue(new Callback<BillResponse>() {
            @Override
            public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
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
            public void onFailure(Call<BillResponse> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
