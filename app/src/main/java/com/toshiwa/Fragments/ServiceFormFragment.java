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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.toshiwa.CustomView.AlertMessage;
import com.toshiwa.CustomView.Edittext_Light;
import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.Model.Lead.AddLeadResponse;
import com.toshiwa.Model.Meter.MeterResponse;
import com.toshiwa.Model.Meter.MeterResult;
import com.toshiwa.Model.Purchase.PurchaseResponse;
import com.toshiwa.Model.Service.ServiceResponse;
import com.toshiwa.Model.Service.ServiceResult;
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

public class ServiceFormFragment extends Fragment {

    LinearLayout mSave, mCancel;
    int pEmpid, pLid;
    TextView_Regular mToolbarTitle, mResPerson, mDate;
    String todayDate;
    Edittext_Light  mResponsiblePerson,mScheduledDate,mCallCount, mCallDate,
            mCallRemark,mAttendRemark;
    String pResponsiblePerson,pScheduledDate,pCallCount, pCallDate,
            pCallRemark,pAttendRemark;
    String responseMessage;
    ServiceResult serviceResult;
    boolean flag_completed = false;
    String status;
    LinearLayout loading;
    ScrollView sv;
Boolean isAdmin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_service_form,container,false);
        loading = view.findViewById(R.id.loading);
        sv = view.findViewById(R.id.scroll_view);
        isAdmin = SharedPreferencesManager.getIsAdmin(getActivity());

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.common_toolbar);
        mToolbarTitle=view.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarTitle.setText("Service Details");
        View decorView = ((AppCompatActivity) getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        pLid = Integer.parseInt(getArguments().getString("lid"));
        status = getArguments().getString("status");
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        setHasOptionsMenu(true);

        if (!isNetworkAvailable(getActivity())) {
            showDialog(getActivity());

        }else {
            displayService(view);
        }
        init(view);
        return view;

    }

    public void init(View view) {


        mResponsiblePerson  = view.findViewById(R.id.et_responsible_person);
        mScheduledDate      = view.findViewById(R.id.et_scheduled_date);
        mCallCount          = view.findViewById(R.id.et_call_count);
        mCallDate     = view.findViewById(R.id.et_call_date);
        mCallRemark          = view.findViewById(R.id.et_call_remark);
        mAttendRemark       = view.findViewById(R.id.et_attend_remark);
        mResPerson = view.findViewById(R.id.tv_responsible_person);
        mDate = view.findViewById(R.id.tv_date);

        mResPerson.append(SharedPreferencesManager.getName(getActivity()));
        mDate.append(todayDate);

        mSave = view.findViewById(R.id.ll_add);
        mCancel = view.findViewById(R.id.ll_cancel);


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(getActivity())) {
                    addService(view);
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


    public void addService(View view)
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        pEmpid = Integer.parseInt(SharedPreferencesManager.getEmpid(getActivity()));
        pResponsiblePerson   = mResponsiblePerson.getText().toString();
        pScheduledDate       = mScheduledDate.getText().toString();
        pCallCount           = mCallCount.getText().toString();
        pCallDate            = mCallDate.getText().toString();
        pCallRemark          = mCallRemark.getText().toString();
        pAttendRemark        = mAttendRemark.getText().toString();

        Call<AddLeadResponse> addServiceCall = ApiUtils.getAPIService().add_service(pEmpid,pLid,
                pResponsiblePerson,
                pScheduledDate,
                pCallCount,
                pCallDate,
                pCallRemark,
                pAttendRemark
        );
        addServiceCall.enqueue(new Callback<AddLeadResponse>() {
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

    public void displayService(final View view) {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<ServiceResponse> serviceResponseCall = ApiUtils.getAPIService().display_service(pLid);
        serviceResponseCall.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if (response.isSuccessful()) {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if (response.body().getCode().equals("200")) {
                        responseMessage = response.body().getMessage();
                        serviceResult = response.body().getServiceResult();
                        if(!isAdmin){
                            formNotEdit(serviceResult);
                        }else {
                            formEdit(serviceResult);
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
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);

                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void formNotEdit(ServiceResult serviceResult)
    {
        if(!serviceResult.getResponsiblePerson().equals(""))
        {
            mResponsiblePerson.setText(serviceResult.getResponsiblePerson());
            mResponsiblePerson.setEnabled(false);
            mResponsiblePerson.setFocusable(false);
            mResponsiblePerson.setTextColor(Color.DKGRAY);

        }
        if(!serviceResult.getScheduledDate().equals(""))
        {
            mScheduledDate.setText(serviceResult.getScheduledDate());
            mScheduledDate.setEnabled(false);
            mScheduledDate.setFocusable(false);
            mScheduledDate.setTextColor(Color.DKGRAY);

        }
        if(!serviceResult.getCallCount().equals(""))
        {
            mCallCount.setText(serviceResult.getCallCount());
            mCallCount.setEnabled(false);
            mCallCount.setFocusable(false);
            mCallCount.setTextColor(Color.DKGRAY);

        }
        if(!serviceResult.getCallDate().equals(""))
        {
            mCallDate.setText(serviceResult.getCallDate());
            mCallDate.setEnabled(false);
            mCallDate.setFocusable(false);
            mCallDate.setTextColor(Color.DKGRAY);

        }
        if(!serviceResult.getCallRemark().equals(""))
        {
            mCallRemark.setText(serviceResult.getCallRemark());
            mCallRemark.setEnabled(false);
            mCallRemark.setFocusable(false);
            mCallRemark.setTextColor(Color.DKGRAY);


        }
        if(!serviceResult.getCallAttendRemark().equals(""))
        {
            mAttendRemark.setText(serviceResult.getCallAttendRemark());
            mAttendRemark.setEnabled(false);
            mAttendRemark.setFocusable(false);
            mAttendRemark.setTextColor(Color.DKGRAY);


        }

    }

    public void formEdit(ServiceResult serviceResult)
    {
        if(!serviceResult.getResponsiblePerson().equals(""))
        {
            mResponsiblePerson.setText(serviceResult.getResponsiblePerson());
            mResponsiblePerson.setTextColor(Color.DKGRAY);

        }
        if(!serviceResult.getScheduledDate().equals(""))
        {
            mScheduledDate.setText(serviceResult.getScheduledDate());
            mScheduledDate.setTextColor(Color.DKGRAY);

        }
        if(!serviceResult.getCallCount().equals(""))
        {
            mCallCount.setText(serviceResult.getCallCount());
            mCallCount.setTextColor(Color.DKGRAY);

        }
        if(!serviceResult.getCallDate().equals(""))
        {
            mCallDate.setText(serviceResult.getCallDate());
            mCallDate.setTextColor(Color.DKGRAY);

        }
        if(!serviceResult.getCallRemark().equals(""))
        {
            mCallRemark.setText(serviceResult.getCallRemark());
            mCallRemark.setTextColor(Color.DKGRAY);


        }
        if(!serviceResult.getCallAttendRemark().equals(""))
        {
            mAttendRemark.setText(serviceResult.getCallAttendRemark());
            mAttendRemark.setTextColor(Color.DKGRAY);


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
        Call<ServiceResponse> addPurchaseCall = ApiUtils.getAPIService().service_status(pLid,status);
        addPurchaseCall.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
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
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
