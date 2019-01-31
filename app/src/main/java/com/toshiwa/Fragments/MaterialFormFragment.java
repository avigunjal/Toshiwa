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
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.toshiwa.CustomView.AlertMessage;
import com.toshiwa.CustomView.Checkbox_Regular;
import com.toshiwa.CustomView.Edittext_Light;
import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.Model.Approval.ApprovalResponse;
import com.toshiwa.Model.Approval.ApprovalResult;
import com.toshiwa.Model.Execution.ExecutionResponse;
import com.toshiwa.Model.Lead.AddLeadResponse;
import com.toshiwa.Model.Material.MaterialResponse;
import com.toshiwa.Model.Material.MaterialResult;
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

public class MaterialFormFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    String todayDate;
    TextView_Regular mDate, mResponsiblePerson;
    LinearLayout mSave, mCancel;
    TextView_Regular mToolbarTitle;

    Edittext_Light mOtherThings, mAvailableThings, mMaterialDate;

    RadioButton mOrderedInvertor, mAvailableInvertor, mOrderedModule,
            mAvailableModule, mOrdered, mAvailble;

    Checkbox_Regular mMaterialDispatched;
    RadioGroup rgInvertor, rgModule, rgOther;

    int pEmpid, pLid;

    String pOrderThings, pAvailableThings, pMaterialDate,pInvertor, pModule,
            pOrder, pAvailable,pResponsiblePerson,pMaterialDispatched;
    String responseMessage;
    MaterialResult materialResult;
    boolean flag_completed = false;
    String status;
    LinearLayout loading;
    ScrollView sv;
Boolean isAdmin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_material_form, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.common_toolbar);
        mToolbarTitle = view.findViewById(R.id.tv_toolbar_title);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        mToolbarTitle.setText("Material Details");
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
            displayMaterial(view);
        }
        init(view);

        return view;
    }


       public void init(View view) {

        mDate = view.findViewById(R.id.tv_date);
        mResponsiblePerson = view.findViewById(R.id.tv_responsible_person);

           mResponsiblePerson.append(SharedPreferencesManager.getName(getActivity()));

        rgInvertor = view.findViewById(R.id.rg_invertor);
        mOrderedInvertor = view.findViewById(R.id.rb_ordered_invertor);
        mAvailableInvertor = view.findViewById(R.id.rb_avaialble_invertor);
        rgModule = view.findViewById(R.id.rg_module);
        mOrderedModule = view.findViewById(R.id.rb_ordered_module);
        mAvailableModule = view.findViewById(R.id.rb_avaialble_module);
        rgOther = view.findViewById(R.id.rg_other);
        mOrdered = view.findViewById(R.id.rb_ordered);

        mOtherThings =(Edittext_Light) view.findViewById(R.id.et_other_thing);

        mAvailble = view.findViewById(R.id.rb_available);

        mAvailableThings =(Edittext_Light) view.findViewById(R.id.et_available_things);

        mMaterialDispatched = view.findViewById(R.id.chk_material_dispatched);
        mMaterialDate = view.findViewById(R.id.et_date);

        mDate.append(todayDate);

        mMaterialDispatched.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int id = compoundButton.getId();
                if(id== R.id.chk_material_dispatched)
                {
                    pMaterialDispatched = (mMaterialDispatched.isChecked()) ? "yes" : "no";

                }
            }
        });

        mSave = view.findViewById(R.id.ll_add);
        mCancel = view.findViewById(R.id.ll_cancel);

        rgOther.setOnCheckedChangeListener(this);
        rgInvertor.setOnCheckedChangeListener(this);
        rgModule.setOnCheckedChangeListener(this);


        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable(getActivity())) {
                    addMaterial(view);
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
        switch (i) {
            case R.id.rb_ordered_invertor:
                pInvertor = mOrderedInvertor.getText().toString();
                break;
            case R.id.rb_avaialble_invertor:
                pInvertor = mAvailableInvertor.getText().toString();
                break;
            case R.id.rb_ordered_module:
                pModule = mOrderedModule.getText().toString();
                break;
            case R.id.rb_avaialble_module:
                pModule = mAvailableModule.getText().toString();
                break;
            case R.id.rb_ordered:
                pOrder = mOrdered.getText().toString();
                  break;
            case R.id.rb_available:
                pAvailable = mAvailble.getText().toString();
                 break;


        }
    }


    public void addMaterial(View view)
    {
        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        pEmpid = Integer.parseInt(SharedPreferencesManager.getEmpid(getActivity()));
        pResponsiblePerson= SharedPreferencesManager.getName(getActivity());
        // pResponsiblePerson = mResponsiblePerson.getText().toString();
        pMaterialDate = mMaterialDate.getText().toString();
        pOrderThings = mOtherThings.getText().toString();
        pAvailableThings = mAvailableThings.getText().toString();


        Call<AddLeadResponse> addApprovalCall = ApiUtils.getAPIService().add_material(pEmpid,pLid,
                pResponsiblePerson,
                pInvertor,
                pModule,
                pOrder,
                pOrderThings,
                pAvailable,
                pAvailableThings,
                pMaterialDispatched,
                pMaterialDate);
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

    public void displayMaterial(final View view) {

        sv.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        Call<MaterialResponse> materialResponseCall = ApiUtils.getAPIService().display_material(pLid);
        materialResponseCall.enqueue(new Callback<MaterialResponse>() {
            @Override
            public void onResponse(Call<MaterialResponse> call, Response<MaterialResponse> response) {
                if (response.isSuccessful()) {
                    sv.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);

                    if (response.body().getCode().equals("200")) {
                        responseMessage = response.body().getMessage();
                        materialResult = response.body().getMaterialResult();
                        if(!isAdmin){
                            formNotEdit(materialResult);
                        }else {
                            formEdit(materialResult);
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
            public void onFailure(Call<MaterialResponse> call, Throwable t) {
                sv.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);

                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void formNotEdit(MaterialResult materialResult)
    {
        if(materialResult.getInverter().equals("Available"))
        {
            mAvailableInvertor.setChecked(true);
            for(int i = 0; i < rgInvertor.getChildCount(); i++){
                ((RadioButton)rgInvertor.getChildAt(i)).setEnabled(false);
            }

        }
       if(materialResult.getInverter().equals("Ordered")) {
           mOrderedInvertor.setChecked(true);
           for (int i = 0; i < rgInvertor.getChildCount(); i++) {
               ((RadioButton) rgInvertor.getChildAt(i)).setEnabled(false);
           }
        }
           if (materialResult.getModule().equals("Available")) {
               mAvailableModule.setChecked(true);
               for (int i = 0; i < rgModule.getChildCount(); i++) {
                   ((RadioButton) rgModule.getChildAt(i)).setEnabled(false);
               }

           }
           if (materialResult.getModule().equals("Ordered")) {
               mOrderedModule.setChecked(true);
               for (int i = 0; i < rgModule.getChildCount(); i++) {
                   ((RadioButton) rgModule.getChildAt(i)).setEnabled(false);
               }
           }

        if (materialResult.getOrder().equals("Ordered")) {
               mOrdered.setChecked(true);
               mOtherThings.setText(materialResult.getOrderDetails());
               for (int i = 0; i < rgOther.getChildCount(); i++) {
                   ((RadioButton) rgOther.getChildAt(i)).setEnabled(false);
               }

           }
           if (materialResult.getAvailable().equals("Available")) {
               mAvailble.setChecked(true);
               mAvailableThings.setText(materialResult.getAvailableDetails());

               for (int i = 0; i < rgOther.getChildCount(); i++) {
                   ((RadioButton) rgOther.getChildAt(i)).setEnabled(false);
               }
           }

           if (materialResult.getMaterialDispatchedOnsite().equals("yes") || materialResult.getMaterialDispatchedOnsite().equals("no")) {
               mMaterialDispatched.setChecked(true);
               mMaterialDispatched.setEnabled(false);
               mMaterialDispatched.setEnabled(false);
               mMaterialDispatched.setFocusable(false);
               mMaterialDispatched.setTextColor(Color.DKGRAY);

           }

           if (!materialResult.getDispatchedDate().equals(""))
           {
               mMaterialDate.setText(materialResult.getDispatchedDate());
               mMaterialDate.setEnabled(false);
               mMaterialDate.setFocusable(false);
               mMaterialDate.setTextColor(Color.DKGRAY);

           }
    }

    public void formEdit(MaterialResult materialResult)
    {
        if(materialResult.getInverter().equals("Available"))
        {
            mAvailableInvertor.setChecked(true);


        }
        if(materialResult.getInverter().equals("Ordered")) {
            mOrderedInvertor.setChecked(true);

        }
        if (materialResult.getModule().equals("Available")) {
            mAvailableModule.setChecked(true);

        }
        if (materialResult.getModule().equals("Ordered")) {
            mOrderedModule.setChecked(true);

        }

        if (materialResult.getOrder().equals("Ordered")) {
            mOrdered.setChecked(true);
            mOtherThings.setText(materialResult.getOrderDetails());


        }
        if (materialResult.getAvailable().equals("Available")) {
            mAvailble.setChecked(true);
            mAvailableThings.setText(materialResult.getAvailableDetails());

        }

        if (materialResult.getMaterialDispatchedOnsite().equals("yes") || materialResult.getMaterialDispatchedOnsite().equals("no")) {
            mMaterialDispatched.setChecked(true);
            mMaterialDispatched.setTextColor(Color.DKGRAY);

        }

        if (!materialResult.getDispatchedDate().equals(""))
        {
            mMaterialDate.setText(materialResult.getDispatchedDate());
            mMaterialDate.setTextColor(Color.DKGRAY);

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
        Call<MaterialResponse> approvalResponseCall = ApiUtils.getAPIService().material_status(pLid,status);
        approvalResponseCall.enqueue(new Callback<MaterialResponse>() {
            @Override
            public void onResponse(Call<MaterialResponse> call, Response<MaterialResponse> response) {
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
            public void onFailure(Call<MaterialResponse> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
