package com.toshiwa.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.toshiwa.Fragments.AccountFormFragment;
import com.toshiwa.Fragments.ApprovalFormFragment;
import com.toshiwa.Fragments.BillFormFragment;
import com.toshiwa.Fragments.CompletionFormFragment;
import com.toshiwa.Fragments.EmpFormFragment;
import com.toshiwa.Fragments.ExecutionFormFragment;
import com.toshiwa.Fragments.LeadFormFragment;
import com.toshiwa.Fragments.LeadProfileFragment;
import com.toshiwa.Fragments.MaterialFormFragment;
import com.toshiwa.Fragments.MeterFormFragment;
import com.toshiwa.Fragments.PurchaseFormFragment;
import com.toshiwa.Fragments.ServiceFormFragment;
import com.toshiwa.toshiwa.R;


public class FormActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    String leadId, status;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        if (findViewById(R.id.container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            if(getIntent().hasExtra("fragment_value") || getIntent().hasExtra("lid")){

                String fragmentName = getIntent().getStringExtra("fragment_value");
                leadId = getIntent().getStringExtra("lid");
                status = getIntent().getStringExtra("status");
                Log.i("FRAGMENT_NAME: ","********"+fragmentName+"********");
                startFragment(fragmentName);

            }
            else {
                Log.i("FRAGMENT_NAME: ","********Lead fragment********");
                // Create a new Fragment to be placed in the activity layout
                LeadFormFragment leadFormFragment = new LeadFormFragment();
                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, leadFormFragment).commit();
            }
        }



    }

    public void startFragment(String fn){
        Bundle bundle = new Bundle();
        bundle.putString("lid",leadId);
        bundle.putString("status", status);
        switch (fn)
        {
            case "1": fragmentManager.beginTransaction();
                      PurchaseFormFragment purchaseFormFragment = new PurchaseFormFragment();
                      purchaseFormFragment.setArguments(bundle);
                      fragmentTransaction.replace(R.id.container,purchaseFormFragment);
                      fragmentTransaction.commit();break;

            case "2": fragmentManager.beginTransaction();
                ApprovalFormFragment approvalFormFragment = new ApprovalFormFragment();
                approvalFormFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container,approvalFormFragment);
                fragmentTransaction.commit();break;
            case "3": fragmentManager.beginTransaction();
                MaterialFormFragment materialFormFragment = new MaterialFormFragment();
                materialFormFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container,materialFormFragment);
                fragmentTransaction.commit();break;
            case "4": fragmentManager.beginTransaction();
                ExecutionFormFragment executionFormFragment = new ExecutionFormFragment();
                executionFormFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container, executionFormFragment);
                fragmentTransaction.commit();break;
            case "5": fragmentManager.beginTransaction();
                CompletionFormFragment completionFormFragment = new CompletionFormFragment();
                completionFormFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container, completionFormFragment);
                fragmentTransaction.commit();break;
            case "6": fragmentManager.beginTransaction();
                BillFormFragment billFormFragment = new BillFormFragment();
                billFormFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container, billFormFragment);
                fragmentTransaction.commit();break;
            case "7": fragmentManager.beginTransaction();
                AccountFormFragment accountFormFragment = new AccountFormFragment();
                accountFormFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container, accountFormFragment);
                fragmentTransaction.commit();break;
            case "8": fragmentManager.beginTransaction();
                 MeterFormFragment meterFormFragment = new MeterFormFragment();
                 meterFormFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container,meterFormFragment);
                fragmentTransaction.commit();break;
            case "9": fragmentManager.beginTransaction();
            ServiceFormFragment serviceFormFragment = new ServiceFormFragment();
            serviceFormFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container,serviceFormFragment);
                fragmentTransaction.commit();break;
            case "100": fragmentManager.beginTransaction();
                EmpFormFragment empFormFragment = new EmpFormFragment();
                empFormFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container,empFormFragment);
                fragmentTransaction.commit();
                break;

            case "101": fragmentManager.beginTransaction();
                LeadProfileFragment leadProfileFragment = new LeadProfileFragment();
                leadProfileFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container,leadProfileFragment);
                fragmentTransaction.commit();
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_menu, menu);
        return true;
    }


}
