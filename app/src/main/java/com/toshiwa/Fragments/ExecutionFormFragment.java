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
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.toshiwa.CustomView.AlertMessage;
import com.toshiwa.CustomView.Checkbox_Regular;
import com.toshiwa.CustomView.Edittext_Light;
import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.Model.Completion.CompletionResponse;
import com.toshiwa.Model.Execution.ExecutionResponse;
import com.toshiwa.Model.Execution.ExecutionResult;
import com.toshiwa.Model.Lead.AddLeadResponse;
import com.toshiwa.Model.Purchase.PurchaseResponse;
import com.toshiwa.Model.Purchase.PurchaseResult;
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

public class ExecutionFormFragment extends Fragment {

    String todayDate;
    TextView_Regular mDate, mResponsiblePerson;
    LinearLayout mSave, mCancel;
    TextView_Regular mToolbarTitle;
    Checkbox_Regular mMaterialReady;
    Edittext_Light mStartDate, mCompletionDate, mRemark;
    int pEmpid, pLid;
    ExecutionResult executionResult;
    String pMaterialReady,pStartDate, pCompletionDate, pRemark, responseMessage,pResponsiblePerson;
    boolean flag_completed = false;
    String status;
    LinearLayout loading;
    ScrollView sv;
    Boolean isAdmin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_execution_form,container,false);
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.common_toolbar);
        mToolbarTitle=view.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarTitle.setText("Execution Details");
        View decorView = ((AppCompatActivity) getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        pLid = Integer.parseInt(getArguments().getString("lid"));
        status = getArguments().getString("status");
isAdmin = SharedPreferencesManager.getIsAdmin(getActivity());
        loading = view.findViewById(R.id.loading);
        sv = view.findViewById(R.id.scroll_view);

        setHasOptionsMenu(true);

        if (!isNetworkAvailable(getActivity())) {
            showDialog(getActivity());
        }else {
            displayExecution(view);
        }
        init(view);

        return view;
    }

    public void init(View view) {

        mDate = view.findViewById(R.id.tv_date);
        mResponsiblePerson = view.findViewById(R.id.tv_responsible_person);
        mResponsiblePerson.append(SharedPreferencesManager.getName(getActivity()));

        mMaterialReady  =    view.findViewById(R.id.chk_material);
        mStartDate      =        view.findViewById(R.id.et_start);
        mCompletionDate =   view.findViewById(R.id.et_completion);
        mRemark         =           view.findViewById(R.id.et_remark);
        mDate.append(todayDate);

        mMaterialReady.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int id = compoundButton.getId();
                if(id== R.id.chk_material)
                {
                    pMaterialReady = (mMaterialReady.isChecked()) ? "yes" : "no";

                }
            }
        });

        mSave = view.findViewById(R.id.ll_add);
        mCancel = view.findViewById(R.id.ll_cancel);


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(getActivity())) {
                    addExecution(view);
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


    public void addExecution(View view)
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        pEmpid = Integer.parseInt(SharedPreferencesManager.getEmpid(getActivity()));
        pResponsiblePerson= SharedPreferencesManager.getName(getActivity());
        // pResponsiblePerson = mResponsiblePerson.getText().toString();
        pStartDate = mStartDate.getText().toString();
        pCompletionDate = mCompletionDate.getText().toString();
        pRemark = mRemark.getText().toString();


        Call<AddLeadResponse> addApprovalCall = ApiUtils.getAPIService().add_execution(pEmpid,pLid,
                pResponsiblePerson,
                pMaterialReady,
                pStartDate,
                pCompletionDate,
                pRemark
               );
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

    public void displayExecution(final View view) {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<ExecutionResponse> executionResponseCall = ApiUtils.getAPIService().display_execution(pLid);
        executionResponseCall.enqueue(new Callback<ExecutionResponse>() {
            @Override
            public void onResponse(Call<ExecutionResponse> call, Response<ExecutionResponse> response) {
                if (response.isSuccessful()) {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if (response.body().getCode().equals("200")) {
                        responseMessage = response.body().getMessage();
                        executionResult = response.body().getExecutionResult();
                        if(!isAdmin){
                            formNotEdit(executionResult);
                        }else {
                            formEdit(executionResult);
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
            public void onFailure(Call<ExecutionResponse> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);

                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void formNotEdit(ExecutionResult executionResult)
    {
        if(executionResult.getMaterialReady().equals("yes"))
        {
            mMaterialReady.setChecked(true);
            mMaterialReady.setEnabled(false);

        }
        if(!executionResult.getStartDate().equals(""))
        {
            mStartDate.setText(executionResult.getStartDate());
            mStartDate.setEnabled(false);
            mStartDate.setFocusable(false);
            mStartDate.setTextColor(Color.DKGRAY);


        }
        if(!executionResult.getWorkCompletionDate().equals(""))
        {
            mCompletionDate.setText(executionResult.getWorkCompletionDate());
            mCompletionDate.setEnabled(false);
            mCompletionDate.setFocusable(false);
            mCompletionDate.setTextColor(Color.DKGRAY);


        }
        if(!executionResult.getRemark().equals(""))
        {
            mRemark.setText(executionResult.getRemark());
            mRemark.setEnabled(false);
            mRemark.setFocusable(false);
            mRemark.setTextColor(Color.DKGRAY);


        }

    }

    public void formEdit(ExecutionResult executionResult)
    {
        if(executionResult.getMaterialReady().equals("yes"))
        {
            mMaterialReady.setChecked(true);

        }
        if(!executionResult.getStartDate().equals(""))
        {
            mStartDate.setText(executionResult.getStartDate());
           mStartDate.setTextColor(Color.DKGRAY);


        }
        if(!executionResult.getWorkCompletionDate().equals(""))
        {
            mCompletionDate.setText(executionResult.getWorkCompletionDate());
            mCompletionDate.setTextColor(Color.DKGRAY);


        }
        if(!executionResult.getRemark().equals(""))
        {
            mRemark.setText(executionResult.getRemark());
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
        if(id == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return false;
    }

    public void updateStatus(String status){
        Call<ExecutionResponse> approvalResponseCall = ApiUtils.getAPIService().execution_status(pLid,status);
        approvalResponseCall.enqueue(new Callback<ExecutionResponse>() {
            @Override
            public void onResponse(Call<ExecutionResponse> call, Response<ExecutionResponse> response) {
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
            public void onFailure(Call<ExecutionResponse> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
