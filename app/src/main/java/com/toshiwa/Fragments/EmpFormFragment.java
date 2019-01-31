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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.Button;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.toshiwa.CustomView.AlertMessage;
import com.toshiwa.CustomView.Edittext_Light;
import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.Model.Account.AccountResponse;
import com.toshiwa.Model.Account.AccountResult;
import com.toshiwa.Model.Lead.AddLeadResponse;
import com.toshiwa.Preferences.SharedPreferencesManager;
import com.toshiwa.Utils.ApiUtils;
import com.toshiwa.toshiwa.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.toshiwa.InternetConnection.DialogNoConnection.showDialog;
import static com.toshiwa.InternetConnection.InternetConnection.isNetworkAvailable;

public class EmpFormFragment extends Fragment {
    String responseMessage;
    TextView_Regular mToolbarTitle;

    TextView_Regular mResponsiblePerson;
    Edittext_Light mName, mMobile, mPassword;
     boolean flag_completed=false;
    Button mAdd;
    String pName, pMobile, pPassword;
    TextView_Regular mError;
    LinearLayout loading;
    ScrollView sv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_emp_form,container,false);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.common_toolbar);
        mToolbarTitle=view.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarTitle.setText("Manage Employee");
        View decorView = ((AppCompatActivity) getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        loading = view.findViewById(R.id.loading);
        sv = view.findViewById(R.id.scroll_view);

        setHasOptionsMenu(true);
        if (!isNetworkAvailable(getActivity())) {
            showDialog(getActivity());

        }

        init(view);
        return view;

    }

    public void init(View view)
    {
    mName = view.findViewById(R.id.et_name);
    mMobile = view.findViewById(R.id.et_mobile);
    mPassword = view.findViewById(R.id.et_password);
    mError = view.findViewById(R.id.error);

    mAdd = view.findViewById(R.id.btn_add);
    mAdd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pName = mName.getText().toString();
            pMobile = mMobile.getText().toString();
            pPassword = mPassword.getText().toString();

            if(pName.equals("") && pMobile.equals("") && pPassword.equals(""))
            {
                mError.setVisibility(View.VISIBLE);
            }else {
                mError.setVisibility(View.GONE);
                addEmployee(view);
            }

        }
    });

    }

    public void addEmployee(final View view)
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<AddLeadResponse> addBillCall = ApiUtils.getAPIService().add_emp(pName, pMobile, pPassword);
        addBillCall.enqueue(new Callback<AddLeadResponse>() {
            @Override
            public void onResponse(Call<AddLeadResponse> call, Response<AddLeadResponse> response) {
                if (response.isSuccessful()) {

                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    AlertMessage.alertMessage(getActivity(),"Great Job!","Employee Added Successfully");
                    if (response.body().getCode().equals("200")) {

                       responseMessage = response.body().getMessage();
                      // Toast.makeText(getActivity(), responseMessage, Toast.LENGTH_SHORT).show();
                       mName.setText(null);
                       mMobile.setText(null);
                       mPassword.setText(null);

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
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if(id == android.R.id.home) {
            getActivity().onBackPressed();
        }

        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
            MenuItem menuItem = menu.findItem(R.id.completed);
            menuItem.setVisible(false);
            MenuItem menuItem1 = menu.findItem(R.id.mark);
            menuItem1.setVisible(false);

        super.onCreateOptionsMenu(menu, menuInflater);
    }


}
