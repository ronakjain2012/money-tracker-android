package com.example.ronak.myexpensetracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView bal;
    ListView l1;
    Context context;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.mipmap.statement,
            R.mipmap.currency_clock,
            R.mipmap.gnome_preferences_system
    };

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Accounts obj = new Accounts(this);
        double balance = obj.getBalance();
        bal = (TextView) findViewById(R.id.currBal);
        bal.setText(balance + "");
        if (balance < 0) {
            bal.setTextColor(Color.rgb(243, 79, 79));
        } else {
            bal.setTextColor(Color.rgb(46, 214, 108));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        obj.close();
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "");
        adapter.addFrag(new TwoFragment(), "");
        adapter.addFrag(new ThreeFragment(), "");

        viewPager.setAdapter(adapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.pinChange) {
            Intent i = new Intent(this, setFirstPassword.class);
            startActivity(i);
        } else if (id == R.id.writeToDev) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"ronakbokaria@web-tools.asia"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Daily Expense Tracker : APP");
            i.putExtra(Intent.EXTRA_TEXT, "Daily Expense Tracker \n");
            i.setType("message/rfc822");
            try {
                startActivity(Intent.createChooser(i, "Send mail with..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.sendFeedback) {

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBodyText = "Your shearing message goes here";
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Daily Expense Tracker");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, "http://web-tools.asia/");
            try {
                startActivity(Intent.createChooser(intent, "Choose sharing method"));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void open_ie(View v) {
        switch (v.getId()) {
            case R.id.i:
                Intent i = new Intent(this, income.class);
                startActivity(i);
                break;
            case R.id.e:
                Intent e = new Intent(this, expense.class);
                startActivity(e);
                break;
        }
    }

    public void openCategory(View v) {
        Intent i = new Intent(this, Category.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Accounts obj = new Accounts(this);
        double balance = obj.getBalance();
        bal = (TextView) findViewById(R.id.currBal);
        bal.setText(balance + "");
        if (balance < 0) {
            bal.setTextColor(Color.rgb(243, 79, 79));
        } else {
            bal.setTextColor(Color.rgb(46, 214, 108));
        }
        obj.close();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Accounts obj = new Accounts(this);
        double balance = obj.getBalance();
        bal = (TextView) findViewById(R.id.currBal);
        bal.setText(balance + "");
        if (balance < 0) {
            bal.setTextColor(Color.rgb(243, 79, 79));
        } else {
            bal.setTextColor(Color.rgb(46, 214, 108));
        }
        obj.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Accounts obj = new Accounts(this);
        double balance = obj.getBalance();
        bal = (TextView) findViewById(R.id.currBal);
        bal.setText(balance + "");
        if (balance < 0) {
            bal.setTextColor(Color.rgb(243, 79, 79));
        } else {
            bal.setTextColor(Color.rgb(46, 214, 108));
        }
        obj.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void openMonth(View v) {
        Intent i = new Intent(context, MonthWiseView.class);
        startActivity(i);
    }

    public void openAccount(View v) {
        Intent i = new Intent(context, AccountView.class);
        startActivity(i);
    }

    public void openAverage(View v) {
        Intent i = new Intent(context, AverageSummary.class);
        startActivity(i);
    }

    public void openFullStatement(View v) {
        Intent i = new Intent(context, FullStatement.class);
        startActivity(i);
    }

    public void logout(View v) {
        Intent i = new Intent(this, Gate_Validation.class);
        startActivity(i);
        finish();
    }

    public void chnagePIN(View v) {
        Intent i = new Intent(this, setFirstPassword.class);
        startActivity(i);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}


