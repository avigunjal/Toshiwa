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
import com.toshiwa.Model.Lead.AddLeadResponse;
import com.toshiwa.Model.Notification.NotificationRes;
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

public class LeadFormFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    TextView_Regular mToolbarTitle, mDate, mResponsiblePerson;
    Edittext_Light mCname, mContact, mDname, mCapacity, mContactPerson, mAgging, mLocation;
    Checkbox_Regular mCapex, mOpex;
    LinearLayout mSave, mCancel;
    String todayDate;
    String  responseMessage;
    int pEmpid;
    String pDate = "", pCname = "", pContact = "", pDname = "", pCapacity = "",
            pContactPerson = "", pAgging = "", pLocation = "", pCapex = "", pOpex = "";
    LinearLayout loading;
    ScrollView sv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lead_form, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.common_toolbar);
        loading = view.findViewById(R.id.loading);
        sv = view.findViewById(R.id.scroll_view);

        mToolbarTitle = view.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarTitle.setText("Add New Lead");
        View decorView = ((AppCompatActivity) getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setHasOptionsMenu(true);
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        if (!isNetworkAvailable(getActivity())) {
            showDialog(getActivity());

        }
        init(view);

        mCapex.setOnCheckedChangeListener(this);
        mOpex.setOnCheckedChangeListener(this);

        return view;
    }

    public void init(View view) {
        mDate = view.findViewById(R.id.tv_date);
        mResponsiblePerson = view.findViewById(R.id.tv_responsible_person);

        mCname = view.findViewById(R.id.et_cname);
        mContact = view.findViewById(R.id.et_cnumber);
        mDname = view.findViewById(R.id.et_dname);
        mCapacity = view.findViewById(R.id.et_capacity);
        mContactPerson = view.findViewById(R.id.et_contact_person);
        mAgging = view.findViewById(R.id.et_agging);
        mLocation = view.findViewById(R.id.et_location);

        mCapex = (Checkbox_Regular)view.findViewById(R.id.chk_capex);
        mOpex =(Checkbox_Regular) view.findViewById(R.id.chk_opex);

        mDate.append(todayDate);

        mSave = view.findViewById(R.id.ll_save);
        mCancel = view.findViewById(R.id.ll_cancel);

        mResponsiblePerson.append(SharedPreferencesManager.getName(getActivity()));

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(getActivity())) {
                    addLead(view);
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
            case R.id.chk_capex:
                pCapex = (mCapex.isChecked()) ? "yes" : "no";
                break;
            case R.id.chk_opex:
                pOpex = (mOpex.isChecked()) ? "yes" : "no";
                break;

        }
    }


    public void addLead(final View view) {

        pEmpid = Integer.parseInt(SharedPreferencesManager.getEmpid(getActivity()));
        pDate = todayDate;
        pCname = mCname.getText().toString();
        pContact = mContact.getText().toString();
        pDname = mDname.getText().toString();
        pCapacity = mCapacity.getText().toString();
        pContactPerson = mContactPerson.getText().toString();
        pAgging = mAgging.getText().toString();
        pLocation = mLocation.getText().toString();
        mAgging.setVisibility(View.GONE);



        if (pDate.equals("") || pCname.equals("") || pCname.equals("") || pContact.equals("") ||
                pDname.equals("") || pCapacity.equals("") || pContactPerson.equals("") || pLocation.equals("")) {

            Toast.makeText(getActivity(), "Empty Fields Not Allowed.", Toast.LENGTH_SHORT).show();

        } else {
            sv.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);

            Call<NotificationRes> addLeadResponseCall = ApiUtils.getAPIService().add_lead(pEmpid,
                    pDate,
                    pCname,
                    pContact,
                    pDname,
                    pCapacity,
                    pContactPerson,
                    pLocation, pCapex, pOpex);
            addLeadResponseCall.enqueue(new Callback<NotificationRes>() {
                @Override
                public void onResponse(Call<NotificationRes> call, Response<NotificationRes> response) {
                    if (response.isSuccessful()) {
                        sv.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        if (response.body().getNotificationResult().getCode().equals("200")) {

                            responseMessage = response.body().getNotificationResult().getMessage();
                            AlertMessage.alertMessage(getActivity(),"Great Job!",responseMessage);

                        } else if (response.body().getNotificationResult().getCode().equals("202")) {

                            responseMessage = response.body().getNotificationResult().getMessage();

                            Toast.makeText(getActivity(), responseMessage, Toast.LENGTH_SHORT).show();


                        }
                    } else {
                        sv.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);

                        Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<NotificationRes> call, Throwable t) {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {

            MenuItem menuItem = menu.findItem(R.id.completed);
            menuItem.setVisible(false);
            MenuItem menuItem1 = menu.findItem(R.id.mark);
            menuItem1.setVisible(false);
            super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if(id == android.R.id.home) {
            getActivity().onBackPressed();
        }

        return false;
    }

    }

