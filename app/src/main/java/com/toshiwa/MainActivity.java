package com.toshiwa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.toshiwa.Activity.CommonList;
import com.toshiwa.Activity.DeveloperActivity;
import com.toshiwa.Activity.Employee;
import com.toshiwa.Activity.FormActivity;
import com.toshiwa.Activity.LeadDashboard;
import com.toshiwa.Activity.LeadList;
import com.toshiwa.Activity.LoginActivity;
import com.toshiwa.Adapter.SliderAdapter;
import com.toshiwa.CustomView.TextView_Bold;
import com.toshiwa.CustomView.TextView_Regular;
import com.toshiwa.Preferences.SharedPreferencesManager;
import com.toshiwa.toshiwa.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
         TextView_Bold mDisplayLeadTv, mAddLeadTv;
         TextView_Regular mUser;
         CardView mPurchase,mApproval, mMaterial, mExecution, mCompletion,
    mBill, mAccount, mMeter, mService;
         String empid, mobile, name, password;
         LinearLayout mAdmin, mEmp;
    private static ViewPager mPager;
    private int currentPage = 1;
    private int page_no = 1;
    TextView_Regular tv_toolbar_title;
    FloatingActionButton fab;

    int[] imgs = new int[]{R.drawable.imgc,R.drawable.imga, R.drawable.imgb};
    private ArrayList<String> bannerArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.common_toolbar);
         tv_toolbar_title = findViewById(R.id.tv_toolbar_title);
         tv_toolbar_title.setText(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        init();


        empid = SharedPreferencesManager.getEmpid(MainActivity.this);
        mobile = SharedPreferencesManager.getMobile(MainActivity.this);
        name = SharedPreferencesManager.getName(MainActivity.this);
        password = SharedPreferencesManager.getPassword(MainActivity.this);

        mUser.append(name);

        sliderInit();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, FormActivity.class));

            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(!password.equals(getResources().getString(R.string.password))) {
            mAdmin.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_emp).setVisible(false);
            nav_Menu.findItem(R.id.nav_employee).setVisible(false);
            SharedPreferencesManager.setIsAdmin(MainActivity.this, false);

        }else {
            SharedPreferencesManager.setIsAdmin(MainActivity.this, true);
        }
    }

    private void sliderInit() {

            mPager = findViewById(R.id.pager);
            mPager.setAdapter(new SliderAdapter(MainActivity.this,imgs));
            CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
            indicator.setViewPager(mPager);

            // Auto start of viewpager
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == bannerArray.size()) {
                        currentPage = 0;
                    }
                    mPager.setCurrentItem(currentPage++, true);
                }
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 8000, 8000);




    }

    public void init(){
       mDisplayLeadTv =findViewById(R.id.tv_display_leads);
       mAddLeadTv = findViewById(R.id.tv_add_leads);
       mUser = findViewById(R.id.tv_user);
       mPurchase = findViewById(R.id.a);
       mApproval = findViewById(R.id.b);
       mAdmin = findViewById(R.id.ll_admin);
       mEmp = findViewById(R.id.ll_emp);


        mMaterial = findViewById(R.id.c);
        mExecution = findViewById(R.id.d);

        mCompletion = findViewById(R.id.e);
        mBill = findViewById(R.id.f);

        mAccount = findViewById(R.id.g);
        mMeter = findViewById(R.id.h);

        mService = findViewById(R.id.i);

       mDisplayLeadTv.setText(Html.fromHtml("<b><font color=#ffffff>Display </font><font color=#ffffff> Leads</font></b>"));
       mAddLeadTv.setText(Html.fromHtml("<b><font color=#ffffff>Add </font><font color=#ffffff> Leads</font></b>"));

       mDisplayLeadTv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(MainActivity.this, LeadList.class));
               }
       });
        mAddLeadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FormActivity.class));
            }
        });

        mPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommonList.class);
                intent.putExtra("id", "purchase");
                startActivity(intent);
            }
        });
        mApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommonList.class);
                intent.putExtra("id", "approval");
                startActivity(intent);
            }
        });

        mMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommonList.class);
                intent.putExtra("id", "material");
                startActivity(intent);
            }
        });

        mExecution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommonList.class);
                intent.putExtra("id", "execution");
                startActivity(intent);
            }
        });

        mCompletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommonList.class);
                intent.putExtra("id", "completion");
                startActivity(intent);
            }
        });

        mBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommonList.class);
                intent.putExtra("id", "bill");
                startActivity(intent);
            }
        });

        mAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommonList.class);
                intent.putExtra("id", "account");
                startActivity(intent);
            }
        });

        mMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommonList.class);
                intent.putExtra("id", "meter");
                startActivity(intent);
            }
        });
        mService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CommonList.class);
                intent.putExtra("id", "service");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


     @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_emp) {
            Intent inEmp = new Intent(MainActivity.this, FormActivity.class);
            inEmp.putExtra("fragment_value","100");
            startActivity(inEmp);

        }else if (id == R.id.nav_logout) {
            SharedPreferencesManager.setIsLogin(MainActivity.this,false);
            MainActivity.this.finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

          }else if (id == R.id.nav_Developer) {
              startActivity(new Intent(MainActivity.this, DeveloperActivity.class));

        }

        else if (id == R.id.nav_employee) {
            startActivity(new Intent(MainActivity.this, Employee.class));

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
