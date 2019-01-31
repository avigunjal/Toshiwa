package com.toshiwa.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.toshiwa.Activity.LeadList;
import com.toshiwa.Adapter.LeadListAdapter;
import com.toshiwa.CustomView.AlertMessage;
import com.toshiwa.CustomView.Checkbox_Regular;
import com.toshiwa.CustomView.Edittext_Light;
import com.toshiwa.CustomView.TextView_Bold;
import com.toshiwa.CustomView.TextView_Light;
import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.Model.Lead.DisplayLeadList;
import com.toshiwa.Model.Lead.DisplayLeadListResult;
import com.toshiwa.Model.Notification.NotificationRes;
import com.toshiwa.Preferences.SharedPreferencesManager;
import com.toshiwa.Utils.ApiUtils;
import com.toshiwa.toshiwa.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.toshiwa.InternetConnection.DialogNoConnection.showDialog;
import static com.toshiwa.InternetConnection.InternetConnection.isNetworkAvailable;

public class LeadProfileFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    TextView_Regular mToolbarTitle, mDate, mResponsiblePerson;
    Edittext_Light mCname, mContact, mDname, mCapacity, mContactPerson, mAgging, mLocation;
    Checkbox_Regular mCapex, mOpex;
    LinearLayout mSave, mCancel;
    String todayDate;
    String  responseMessage;
    int pEmpid;
    int pLid;
    String pDate = "", pCname = "", pContact = "", pDname = "", pCapacity = "",
            pContactPerson = "", pAgging = "", pLocation = "", pCapex = "", pOpex = "";
    LinearLayout loading;
    ScrollView sv;
    TextView_Bold mNameTV;
    TextView_Regular mMobileTV, mLocationTV;
    ImageView edit;
    LinearLayout mEditForm, mProfile;
    TextView_Regular mCapacityTV, mDealerTV, mContactPersonTV, mOpexTV, mCapexTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lead_profile, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.common_toolbar);
        loading = view.findViewById(R.id.loading);
        sv = view.findViewById(R.id.scroll_view);
        edit = view.findViewById(R.id.edit);
        mEditForm = view.findViewById(R.id.ll_lead_form);
        mProfile = view.findViewById(R.id.ll_profile);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mProfile.setVisibility(View.GONE);
              mEditForm.setVisibility(View.VISIBLE);
            }
        });

        mToolbarTitle = view.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarTitle.setText("Lead Profile");
        View decorView = ((AppCompatActivity) getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setHasOptionsMenu(true);
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        pLid = Integer.parseInt(getArguments().getString("lid"));

        if (!isNetworkAvailable(getActivity())) {
            showDialog(getActivity());

        }
        init(view);

        mCapex.setOnCheckedChangeListener(this);
        mOpex.setOnCheckedChangeListener(this);

        getLeadData();
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


        //Lead Profile
        mNameTV = view.findViewById(R.id.tv_lead_name);
        mMobileTV = view.findViewById(R.id.tv_lead_mobile);
        mLocationTV = view.findViewById(R.id.tv_lead_address);
        mCapacityTV = view.findViewById(R.id.tv_lead_capacity);
        mDealerTV = view.findViewById(R.id.tv_lead_dealer);
        mContactPersonTV = view.findViewById(R.id.tv_lead_contact_person);
        mOpexTV = view.findViewById(R.id.tv_lead_opex);
        mCapexTV = view.findViewById(R.id.tv_lead_Capex);

        //Data lead profile


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

    public void getLeadData()
    {
        String empid = SharedPreferencesManager.getEmpid(getContext());
        loading.setVisibility(View.VISIBLE);
        Call<DisplayLeadList> leadListCall = ApiUtils.getAPIService().display_profile(empid,pLid);
        leadListCall.enqueue(new Callback<DisplayLeadList>() {
            @Override
            public void onResponse(Call<DisplayLeadList> call, Response<DisplayLeadList> response) {
                if(response.isSuccessful())
                {
                    loading.setVisibility(View.GONE);
                    sv.setVisibility(View.VISIBLE);
                    if(response.body().getCode().equals("200"))
                    {
                        List<DisplayLeadListResult> displayLeadListResult = response.body().getResult();
                        mNameTV.append(displayLeadListResult.get(0).getName());
                        mMobileTV.append(displayLeadListResult.get(0).getMobile());
                        mLocationTV.append(displayLeadListResult.get(0).getLocation());
                        mDealerTV.append(displayLeadListResult.get(0).getDealer());
                        mCapacityTV.append(displayLeadListResult.get(0).getCapacity());
                        mContactPersonTV.append(displayLeadListResult.get(0).getContact_person());
                      if(displayLeadListResult.get(0).getCapex().equals(""))
                      {
                          mCapexTV.append("--");
                          mOpexTV.append(displayLeadListResult.get(0).getOpex());

                      }
                      else if(displayLeadListResult.get(0).getOpex().equals(""))
                      {
                          mCapexTV.append(displayLeadListResult.get(0).getCapex());
                          mOpexTV.append("--");

                      }

                        }else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DisplayLeadList> call, Throwable t) {
                loading.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Oops. Try Again Later", Toast.LENGTH_SHORT).show();
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

