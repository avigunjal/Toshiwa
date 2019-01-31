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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.toshiwa.CustomView.AlertMessage;
import com.toshiwa.CustomView.Edittext_Light;
import com.toshiwa.CustomView.TextView_Regular;
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

public class PurchaseFormFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{

    TextView_Regular mToolbarTitle;
    PurchaseResult purchaseResult;
    String todayDate;
    TextView_Regular  mDate, mResponsiblePerson;
    Edittext_Light  mProjectCost, mFabrication, mInverter, mPanel;
    RadioButton mSubsidyYes, mSubsidyNo, mOnGrid,mOffGrid,mLoadYes, mLoadNo, mRes, mTwo, mThree, mComm, mTrust;
    RadioGroup rgSubsidy,rgGrid, rgLoad, rgPhase, rgType;

    LinearLayout mSave, mCancel;
    int pEmpid, pLid;
    String status;
    String pDate = "", pResponsiblePerson = "", pProjectCost= "", pFabrication= "", pInverter= "", pPanel= "",
            pSubsidy= "", pGrid= "",pLoad="",  pPhase= "", pType= "";
     String responseMessage;
     boolean flag_completed = false;
     LinearLayout loading;
     ScrollView sv;
     Boolean isAdmin = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_purchase_form,container,false);
        loading = view.findViewById(R.id.loading);
        sv = view.findViewById(R.id.scroll_view);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.common_toolbar);
        mToolbarTitle=view.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarTitle.setText("Purchase Order");
        View decorView = ((AppCompatActivity) getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        pLid = Integer.parseInt(getArguments().getString("lid"));
        status = getArguments().getString("status");
        isAdmin = SharedPreferencesManager.getIsAdmin(getContext());
        setHasOptionsMenu(true);

        if (!isNetworkAvailable(getActivity())) {
            showDialog(getActivity());

        }else {
            displayPurchase(view);
        }
        init(view);

        return view;
    }

    public void init(View view) {

        mDate = view.findViewById(R.id.tv_date);
        mResponsiblePerson   = view.findViewById(R.id.tv_responsible_person);

      //  mOrderNo = view.findViewById(R.id.et_order_no);
        mSubsidyYes          = view.findViewById(R.id.rb_subsidy_yes);
        mSubsidyNo           = view.findViewById(R.id.rb_subsidy_no);
        mOnGrid              = view.findViewById(R.id.rb_ongrid);
        mOffGrid              = view.findViewById(R.id.rb_offgrid);
        mLoadYes           = view.findViewById(R.id.rb_load_yes);
        mLoadNo              = view.findViewById(R.id.rb_load_no);
        mRes                = view.findViewById(R.id.rb_res);
        mComm             = view.findViewById(R.id.rb_com);
        mTrust             = view.findViewById(R.id.rb_trust);
        mTwo                = view.findViewById(R.id.rb_two_phase);
        mThree             = view.findViewById(R.id.rb_three_phase);
        mProjectCost       = view.findViewById(R.id.et_project_cost);
        mFabrication       = view.findViewById(R.id.et_fabrication);
        mInverter          = view.findViewById(R.id.et_inverter);
        mPanel             = view.findViewById(R.id.et_pv_panel);

        rgSubsidy         = (RadioGroup)view.findViewById(R.id.rg_subsidy);
        rgGrid            = (RadioGroup)view.findViewById(R.id.rg_grid);
        rgLoad            = (RadioGroup)view.findViewById(R.id.rg_load);
        rgPhase           = (RadioGroup)view.findViewById(R.id.rg_phase);
        rgType            = (RadioGroup)view.findViewById(R.id.rg_type);

        rgSubsidy.setOnCheckedChangeListener(this);
        rgGrid.setOnCheckedChangeListener(this);
        rgType.setOnCheckedChangeListener(this);
        rgPhase.setOnCheckedChangeListener(this);
        rgLoad.setOnCheckedChangeListener(this);


        mDate.append(todayDate);
        mResponsiblePerson.append(SharedPreferencesManager.getName(getActivity()));

        mSave = view.findViewById(R.id.ll_add);
        mCancel = view.findViewById(R.id.ll_cancel);

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
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch(i) {
            case R.id.rb_subsidy_yes:
                    pSubsidy = mSubsidyYes.getText().toString();
                break;
            case R.id.rb_subsidy_no:
                     pSubsidy = mSubsidyNo.getText().toString();
                break;
            case R.id.rb_ongrid:
                    pGrid = mOnGrid.getText().toString();
                break;
            case R.id.rb_offgrid:
                    pGrid = mOffGrid.getText().toString();
                break;
            case R.id.rb_load_yes:
                    pLoad = mLoadYes.getText().toString();
                break;
            case R.id.rb_load_no:
                    pLoad = mLoadNo.getText().toString();
                break;

            case R.id.rb_com:
                     pType = mComm.getText().toString();
                break;
            case R.id.rb_res:
                  pType = mRes.getText().toString();
                break;
            case R.id.rb_trust:
                 pType = mTrust.getText().toString();
                break;
            case R.id.rb_two_phase:
                 pPhase = mTwo.getText().toString();
                break;
            case R.id.rb_three_phase:
                 pPhase = mThree.getText().toString();
                break;

        }

    }

    public void addLead(final View view) {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        pEmpid = Integer.parseInt(SharedPreferencesManager.getEmpid(getActivity()));
        pResponsiblePerson= SharedPreferencesManager.getName(getActivity());
        pDate = todayDate;
       // pResponsiblePerson = mResponsiblePerson.getText().toString();
        pProjectCost = mProjectCost.getText().toString();
        pFabrication = mFabrication.getText().toString();
        pInverter = mInverter.getText().toString();
        pPanel = mPanel.getText().toString();

            Call<AddLeadResponse> addPurchaseCall = ApiUtils.getAPIService().add_purchase(pEmpid,pLid,
                    pResponsiblePerson,
                    pDate,
                    pSubsidy,
                    pGrid,
                    pLoad,
                    pPhase,
                    pType,
                    pProjectCost, pFabrication, pInverter,pPanel);
        addPurchaseCall.enqueue(new Callback<AddLeadResponse>() {
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

    public void displayPurchase(final View view) {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<PurchaseResponse> addPurchaseCall = ApiUtils.getAPIService().display_purchase(pLid);
        addPurchaseCall.enqueue(new Callback<PurchaseResponse>() {
            @Override
            public void onResponse(Call<PurchaseResponse> call, Response<PurchaseResponse> response) {
                if (response.isSuccessful()) {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    if (response.body().getCode().equals("200")) {
                        responseMessage = response.body().getMessage();
                        purchaseResult = response.body().getPurchaseResult();
                        if(purchaseResult.getStatus().equals("true"))
                        {
                          flag_completed = true;
                        }
                        if(!isAdmin){
                            formNotEdit(purchaseResult);
                        }else {
                            formEdit(purchaseResult);
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
            public void onFailure(Call<PurchaseResponse> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);

                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void formNotEdit(PurchaseResult purchaseResult)
    {
        if(purchaseResult.getSubsidy().equals("Yes"))
        {
            mSubsidyYes.setChecked(true);
            for(int i = 0; i < rgSubsidy.getChildCount(); i++){
                ((RadioButton)rgSubsidy.getChildAt(i)).setEnabled(false);
            }

        }
        if(purchaseResult.getSubsidy().equals("No"))
        {
            mSubsidyNo.setChecked(true);
            for(int i = 0; i < rgSubsidy.getChildCount(); i++){
                ((RadioButton)rgSubsidy.getChildAt(i)).setEnabled(false);
            }
        }
        if(purchaseResult.getGrid().equals("Off Grid"))
        {
            mOffGrid.setChecked(true);
            for(int i = 0; i < rgGrid.getChildCount(); i++){
                ((RadioButton)rgGrid.getChildAt(i)).setEnabled(false);
            }
        }
        if(purchaseResult.getGrid().equals("On Grid"))
        {
            mOnGrid.setChecked(true);
            for(int i = 0; i < rgGrid.getChildCount(); i++){
                ((RadioButton)rgGrid.getChildAt(i)).setEnabled(false);
            }
        }
        if(purchaseResult.getLoadExtenstion().equals("No"))
        {
            mLoadNo.setChecked(true);
            for(int i = 0; i < rgLoad.getChildCount(); i++){
                ((RadioButton)rgLoad.getChildAt(i)).setEnabled(false);
            }
        }
        if(purchaseResult.getLoadExtenstion().equals("Yes"))
        {
            mLoadYes.setChecked(true);
            for(int i = 0; i < rgLoad.getChildCount(); i++){
                ((RadioButton)rgLoad.getChildAt(i)).setEnabled(false);
            }
        }
        if(purchaseResult.getType().equals("Residential"))
        {
            mRes.setChecked(true);
            for(int i = 0; i < rgType.getChildCount(); i++){
                ((RadioButton)rgType.getChildAt(i)).setEnabled(false);
            }
        }
        if(purchaseResult.getType().equals("Commercial"))
        {
            mComm.setChecked(true);
            for(int i = 0; i < rgType.getChildCount(); i++){
                ((RadioButton)rgType.getChildAt(i)).setEnabled(false);
            }
        }
        if(purchaseResult.getType().equals("Trust"))
        {
            mTrust.setChecked(true);
            for(int i = 0; i < rgType.getChildCount(); i++){
                ((RadioButton)rgType.getChildAt(i)).setEnabled(false);
            }
        }
        if(purchaseResult.getPhase().equals("Single"))
        {
            mTwo.setChecked(true);
            for(int i = 0; i < rgPhase.getChildCount(); i++){
                ((RadioButton)rgPhase.getChildAt(i)).setEnabled(false);
            }
        }
        if(purchaseResult.getPhase().equals("Three"))
        {
            mThree.setChecked(true);
            for(int i = 0; i < rgPhase.getChildCount(); i++){
                ((RadioButton)rgPhase.getChildAt(i)).setEnabled(false);
            }
        }
        mProjectCost.setText(purchaseResult.getProjectCost());
        mProjectCost.setEnabled(false);mProjectCost.setFocusable(false);
        mProjectCost.setFocusable(false);
        mProjectCost.setTextColor(Color.DKGRAY);

        mFabrication.setText(purchaseResult.getFabrication());
        mFabrication.setEnabled(false);mFabrication.setFocusable(false);
        mFabrication.setFocusable(false);
        mFabrication.setTextColor(Color.DKGRAY);

        mInverter.setText(purchaseResult.getInvertor());
        mInverter.setEnabled(false);mInverter.setFocusable(false);
        mInverter.setFocusable(false);
        mInverter.setTextColor(Color.DKGRAY);

        mPanel.setText(purchaseResult.getPanel());
        mPanel.setEnabled(false);mPanel.setFocusable(false);
        mPanel.setFocusable(false);
        mPanel.setTextColor(Color.DKGRAY);
    }

    public void formEdit(PurchaseResult purchaseResult)
    {
        if(purchaseResult.getSubsidy().equals("Yes"))
        {
            mSubsidyYes.setChecked(true);

        }
        if(purchaseResult.getSubsidy().equals("No"))
        {
            mSubsidyNo.setChecked(true);

        }
        if(purchaseResult.getGrid().equals("Off Grid"))
        {
            mOffGrid.setChecked(true);

        }
        if(purchaseResult.getGrid().equals("On Grid"))
        {
            mOnGrid.setChecked(true);

        }
        if(purchaseResult.getLoadExtenstion().equals("No"))
        {
            mLoadNo.setChecked(true);

        }
        if(purchaseResult.getLoadExtenstion().equals("Yes"))
        {
            mLoadYes.setChecked(true);

        }
        if(purchaseResult.getType().equals("Residential"))
        {
            mRes.setChecked(true);

        }
        if(purchaseResult.getType().equals("Commercial"))
        {
            mComm.setChecked(true);

        }
        if(purchaseResult.getType().equals("Trust"))
        {
            mTrust.setChecked(true);

        }
        if(purchaseResult.getPhase().equals("Single"))
        {
            mTwo.setChecked(true);

        }
        if(purchaseResult.getPhase().equals("Three"))
        {
            mThree.setChecked(true);

        }
        mProjectCost.setText(purchaseResult.getProjectCost());
        mProjectCost.setTextColor(Color.DKGRAY);

        mFabrication.setText(purchaseResult.getFabrication());
        mFabrication.setTextColor(Color.DKGRAY);

        mInverter.setText(purchaseResult.getInvertor());
        mInverter.setTextColor(Color.DKGRAY);

        mPanel.setText(purchaseResult.getPanel());
        mPanel.setTextColor(Color.DKGRAY);
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
        Call<PurchaseResponse> addPurchaseCall = ApiUtils.getAPIService().purchase_status(pLid,status);
        addPurchaseCall.enqueue(new Callback<PurchaseResponse>() {
            @Override
            public void onResponse(Call<PurchaseResponse> call, Response<PurchaseResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode().equals("200")) {
                        responseMessage = response.body().getMessage();
                        Toast.makeText(getContext(), "Status Updated", Toast.LENGTH_SHORT).show();
                        flag_completed = true;
                        getActivity().recreate();
                    } else if (response.body().getCode().equals("202")) {

                        responseMessage = response.body().getMessage();
                        Toast.makeText(getContext(), responseMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PurchaseResponse> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
