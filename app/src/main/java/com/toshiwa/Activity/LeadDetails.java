package com.toshiwa.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.toshiwa.R;

import static com.toshiwa.Constants.Constants.account_url;
import static com.toshiwa.Constants.Constants.approval_url;
import static com.toshiwa.Constants.Constants.bill_url;
import static com.toshiwa.Constants.Constants.completion_url;
import static com.toshiwa.Constants.Constants.execution_url;
import static com.toshiwa.Constants.Constants.material_url;
import static com.toshiwa.Constants.Constants.meter_url;
import static com.toshiwa.Constants.Constants.purchase_url;
import static com.toshiwa.Constants.Constants.service_url;

public class LeadDetails extends AppCompatActivity {
    TextView_Regular mToolbarTitle;
    WebView mWebView;
    String leadId, id,mobile;
    LinearLayout loading;
    ScrollView sv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_details);
        leadId = getIntent().getStringExtra("lid");
        id = getIntent().getStringExtra("id");
        mobile = getIntent().getStringExtra("mobile");
        loading = findViewById(R.id.loading);
        sv = findViewById(R.id.scroll_view);


        Toolbar toolbar = (Toolbar) findViewById(R.id.common_toolbar);
        mToolbarTitle = findViewById(R.id.tv_toolbar_title);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        mWebView = findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.setInitialScale(1);

        String postData = "lid="+leadId;

        switch (id)
        {
            case "purchase":   mWebView.postUrl(purchase_url, postData.getBytes());
                mToolbarTitle.setText("Purchase Details");
            break;
            case "approval":   mWebView.postUrl(approval_url, postData.getBytes());
                mToolbarTitle.setText("Approval Details");
                break;
            case "material":   mWebView.postUrl(material_url, postData.getBytes());
                mToolbarTitle.setText("Material Details");
                break;
            case "execution":  mWebView.postUrl(execution_url, postData.getBytes());
                mToolbarTitle.setText("Execution Details");
                break;
            case "completion": mWebView.postUrl(completion_url, postData.getBytes());
                mToolbarTitle.setText("Completion Details");
                break;
            case "bill":       mWebView.postUrl(bill_url, postData.getBytes());
                mToolbarTitle.setText("Bill Details");
                break;
            case "account":    mWebView.postUrl(account_url, postData.getBytes());
                mToolbarTitle.setText("Account Details");
                break;
            case "meter":      mWebView.postUrl(meter_url, postData.getBytes());
                mToolbarTitle.setText("Meter Details");
                break;
            case "service":    mWebView.postUrl(service_url, postData.getBytes());
                mToolbarTitle.setText("Service Details");
                break;

        }


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) LeadDetails.this
                .getSystemService(Context.PRINT_SERVICE);

        String jobName = getString(R.string.app_name) + " Document";

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());


    }

public void print(){
    createWebPrintJob(mWebView);
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lead_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.call) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+mobile));
            startActivity(intent);
        }

        if (id == R.id.pdf) {
         print();
        }

        if(id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}