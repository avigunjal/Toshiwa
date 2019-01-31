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
import com.toshiwa.Model.Account.AccountResponse;
import com.toshiwa.Model.Account.AccountResult;
import com.toshiwa.Model.Lead.AddLeadResponse;
import com.toshiwa.Model.Material.MaterialResponse;
import com.toshiwa.Model.Meter.MeterResponse;
import com.toshiwa.Model.Meter.MeterResult;
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

public class MeterFormFragment extends Fragment {
    LinearLayout mSave, mCancel;
    int pEmpid, pLid;

    TextView_Regular mToolbarTitle, mResponsiblePerson, mDate;
    String todayDate;
    Edittext_Light mReadingCount, mReaderName, mReadingDate, mExport, mImport, mGeneration;
    String pReadingCount, pReaderName, pReadingDate, pExport, pImport, pGeneration;
    String responseMessage;
    MeterResult meterResult;
    boolean flag_completed = false;
    String status;
    LinearLayout loading;
    ScrollView sv;
    Boolean isAdmin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meter_reading_form,container,false);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.common_toolbar);
        mToolbarTitle=view.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarTitle.setText("Meter Details");
        View decorView = ((AppCompatActivity) getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        pLid = Integer.parseInt(getArguments().getString("lid"));
        status = getArguments().getString("status");
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        isAdmin = SharedPreferencesManager.getIsAdmin(getActivity());

        loading = view.findViewById(R.id.loading);
        sv = view.findViewById(R.id.scroll_view);

        setHasOptionsMenu(true);

        if (!isNetworkAvailable(getActivity())) {
            showDialog(getActivity());

        }else {
            displayMeter(view);
        }
        init(view);
        return view;

    }

    public void init(View view) {

        mReadingCount = view.findViewById(R.id.et_reading_count);
        mReaderName = view.findViewById(R.id.et_reader_name);
        mExport = view.findViewById(R.id.et_export);
        mImport = view.findViewById(R.id.et_import);
        mReadingDate = view.findViewById(R.id.et_reading_date);
        mGeneration = view.findViewById(R.id.et_generation);
        mDate = view.findViewById(R.id.tv_date);
        mResponsiblePerson = view.findViewById(R.id.tv_responsible_person);
        mDate.append(todayDate);

        mResponsiblePerson.append(SharedPreferencesManager.getName(getActivity()));

        mSave = view.findViewById(R.id.ll_add);
        mCancel = view.findViewById(R.id.ll_cancel);


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(getActivity())) {
                    addMeter(view);
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


    public void addMeter(View view)
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        pEmpid = Integer.parseInt(SharedPreferencesManager.getEmpid(getActivity()));
        pReadingCount = mReadingCount.getText().toString();
        pReaderName =   mReaderName.getText().toString();
        pReadingDate = mReadingDate.getText().toString();
        pExport = mExport.getText().toString();
        pImport = mImport.getText().toString();
        pGeneration = mGeneration.getText().toString();

        Call<AddLeadResponse> addBillCall = ApiUtils.getAPIService().add_meter(pEmpid,pLid,
                pReadingCount,
                pReaderName,
                pReadingDate,
                pExport,
                pImport,
                pGeneration
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

    public void displayMeter(final View view) {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<MeterResponse> meterResponseCall = ApiUtils.getAPIService().display_meter_reading(pLid);
        meterResponseCall.enqueue(new Callback<MeterResponse>() {
            @Override
            public void onResponse(Call<MeterResponse> call, Response<MeterResponse> response) {
                if (response.isSuccessful()) {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    if (response.body().getCode().equals("200")) {
                        responseMessage = response.body().getMessage();
                        meterResult = response.body().getMeterResult();
                        if(!isAdmin){
                            formNotEdit(meterResult);
                        }else {
                            formEdit(meterResult);
                        }


                    } else if (response.body().getCode().equals("202")) {
                        sv.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);

                        responseMessage = response.body().getMessage();

                    }
                } else {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MeterResponse> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);

                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void formNotEdit(MeterResult meterResult)
    {
        if(!meterResult.getReadingCount().equals(""))
        {
            mReadingCount.setText(meterResult.getReadingCount());
            mReadingCount.setEnabled(false);
            mReadingCount.setFocusable(false);
            mReadingCount.setTextColor(Color.DKGRAY);
        }
        if(!meterResult.getReaderName().equals(""))
        {
            mReaderName.setText(meterResult.getReaderName());
            mReaderName.setEnabled(false);
            mReaderName.setFocusable(false);
            mReaderName.setTextColor(Color.DKGRAY);
        }
        if(!meterResult.getReadingDate().equals(""))
        {
            mReadingDate.setText(meterResult.getReadingDate());
            mReadingDate.setEnabled(false);
            mReadingDate.setFocusable(false);
            mReadingDate.setTextColor(Color.DKGRAY);
        }
        if(!meterResult.getImport().equals(""))
        {
            mImport.setText(meterResult.getImport());
            mImport.setEnabled(false);
            mImport.setFocusable(false);
            mImport.setTextColor(Color.DKGRAY);
        }
        if(!meterResult.getExport().equals(""))
        {
            mExport.setText(meterResult.getExport());
            mExport.setEnabled(false);
            mExport.setFocusable(false);
            mExport.setTextColor(Color.DKGRAY);

        }
        if(!meterResult.getGeneration().equals(""))
        {
            mGeneration.setText(meterResult.getGeneration());
            mGeneration.setEnabled(false);
            mGeneration.setFocusable(false);
            mGeneration.setTextColor(Color.DKGRAY);

        }

    }

    public void formEdit(MeterResult meterResult)
    {
        if(!meterResult.getReadingCount().equals(""))
        {
            mReadingCount.setText(meterResult.getReadingCount());
            mReadingCount.setTextColor(Color.DKGRAY);
        }
        if(!meterResult.getReaderName().equals(""))
        {
            mReaderName.setText(meterResult.getReaderName());
            mReaderName.setTextColor(Color.DKGRAY);
        }
        if(!meterResult.getReadingDate().equals(""))
        {
            mReadingDate.setText(meterResult.getReadingDate());
            mReadingDate.setTextColor(Color.DKGRAY);
        }
        if(!meterResult.getImport().equals(""))
        {
            mImport.setText(meterResult.getImport());
            mImport.setTextColor(Color.DKGRAY);
        }
        if(!meterResult.getExport().equals(""))
        {
            mExport.setText(meterResult.getExport());
            mExport.setTextColor(Color.DKGRAY);

        }
        if(!meterResult.getGeneration().equals(""))
        {
            mGeneration.setText(meterResult.getGeneration());
            mGeneration.setTextColor(Color.DKGRAY);

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
        Call<MeterResponse> approvalResponseCall = ApiUtils.getAPIService().meter_status(pLid,status);
        approvalResponseCall.enqueue(new Callback<MeterResponse>() {
            @Override
            public void onResponse(Call<MeterResponse> call, Response<MeterResponse> response) {
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
            public void onFailure(Call<MeterResponse> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
