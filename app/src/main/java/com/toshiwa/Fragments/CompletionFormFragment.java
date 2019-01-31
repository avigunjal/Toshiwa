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
import com.toshiwa.Model.Bill.BillResponse;
import com.toshiwa.Model.Completion.CompletionResponse;
import com.toshiwa.Model.Completion.CompletionResult;
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

public class CompletionFormFragment extends Fragment {

    TextView_Regular mToolbarTitle, mResponsiblePerson, mDate;
    Edittext_Light mCompletionDate;
    String todayDate;
    LinearLayout mSave, mCancel;
    String pCompletionDate, responseMessage;
    int pEmpid, pLid;
    CompletionResult completionResult;
    boolean flag_completed=false;
    String status;
    LinearLayout loading;
    ScrollView sv;
    Boolean isAdmin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_completion_form, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.common_toolbar);
        loading = view.findViewById(R.id.loading);
        sv = view.findViewById(R.id.scroll_view);
        isAdmin = SharedPreferencesManager.getIsAdmin(getActivity());

        mToolbarTitle = view.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarTitle.setText("Completion Details");
        View decorView = ((AppCompatActivity) getActivity()).getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        pLid = Integer.parseInt(getArguments().getString("lid"));
        status = getArguments().getString("status");
        todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        setHasOptionsMenu(true);

        if (!isNetworkAvailable(getActivity())) {
            showDialog(getActivity());

        }else {
            displayCompletion(view);
        }  init(view);

        return view;
    }

    public void init(View view) {

        mCompletionDate = view.findViewById(R.id.et_completion_date);
        mResponsiblePerson = view.findViewById(R.id.tv_responsible_person);
        mSave = view.findViewById(R.id.ll_add);
        mCancel = view.findViewById(R.id.ll_cancel);
        mDate = view.findViewById(R.id.tv_date);

        mDate.append(todayDate);
        mResponsiblePerson.append(SharedPreferencesManager.getName(getActivity()));


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(getActivity())) {
                    addCompletion(view);
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

    public void addCompletion(View view)
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        pEmpid = Integer.parseInt(SharedPreferencesManager.getEmpid(getActivity()));
        pCompletionDate = mCompletionDate.getText().toString();

        Call<AddLeadResponse> addCompletionCall = ApiUtils.getAPIService().add_completion(
                pEmpid,
                pLid,
                pCompletionDate
        );
        addCompletionCall.enqueue(new Callback<AddLeadResponse>() {
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

    public void displayCompletion(final View view) {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<CompletionResponse> completionResponseCall = ApiUtils.getAPIService().display_completion(pLid);
        completionResponseCall.enqueue(new Callback<CompletionResponse>() {
            @Override
            public void onResponse(Call<CompletionResponse> call, Response<CompletionResponse> response) {
                if (response.isSuccessful()) {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if (response.body().getCode().equals("200")) {
                        responseMessage = response.body().getMessage();
                        completionResult = response.body().getCompletionResult();
                        if(!isAdmin){
                            formNotEdit(completionResult);
                        }else {
                            formEdit(completionResult);
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
            public void onFailure(Call<CompletionResponse> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);

                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void formNotEdit(CompletionResult completionResult)
    {
        if(!completionResult.getCompletionDate().equals(""))
        {
           mCompletionDate.setText(completionResult.getCompletionDate());
           mCompletionDate.setFocusable(false);
           mCompletionDate.setEnabled(false);
           mCompletionDate.setTextColor(Color.DKGRAY);

        }
    }

    public void formEdit(CompletionResult completionResult)
    {
        if(!completionResult.getCompletionDate().equals(""))
        {
            mCompletionDate.setText(completionResult.getCompletionDate());
            mCompletionDate.setTextColor(Color.DKGRAY);

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
        Call<CompletionResponse> approvalResponseCall = ApiUtils.getAPIService().completion_status(pLid,status);
        approvalResponseCall.enqueue(new Callback<CompletionResponse>() {
            @Override
            public void onResponse(Call<CompletionResponse> call, Response<CompletionResponse> response) {
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
            public void onFailure(Call<CompletionResponse> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
