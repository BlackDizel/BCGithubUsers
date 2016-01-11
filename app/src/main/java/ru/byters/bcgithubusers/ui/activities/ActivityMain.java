package ru.byters.bcgithubusers.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ru.byters.bcgithubusers.R;
import ru.byters.bcgithubusers.controllers.ControllerFind;
import ru.byters.bcgithubusers.controllers.ControllerUserInfo;
import ru.byters.bcgithubusers.ui.adapters.UsersListAdapter;
import ru.byters.bcgithubusers.ui.adapters.ViewPagerAdapter;
import ru.byters.bcgithubusers.ui.fragments.FragmentList;

public class ActivityMain extends AppCompatActivity
        implements SearchView.OnQueryTextListener, ViewPager.OnPageChangeListener {

    ViewPagerAdapter adapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    RecyclerView rvFound;
    private boolean isSearchMode;
    private ControllerFind controllerFind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isSearchMode = false;

        UsersListAdapter listAdapter = new UsersListAdapter(0, new ControllerUserInfo(this));
        controllerFind = new ControllerFind(listAdapter);
        listAdapter.setScrolledListener(controllerFind);

        rvFound = (RecyclerView) findViewById(R.id.rvFound);
        rvFound.setAdapter(listAdapter);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    private void resetFragmentData(int pos) {
        Object f = adapter.instantiateItem(viewPager, pos);
        if (f instanceof FragmentList)
            ((FragmentList) f).resetData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        SupportMenuItem searchItem = (SupportMenuItem) menu.findItem(R.id.action_search);
        ((SearchView) searchItem.getActionView()).setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            isSearchMode = true;
            setState();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            if (isSearchMode) isSearchMode = false;
            setState();
        } else if (!isSearchMode) {
            isSearchMode = true;
            setState();
        }

        if (newText.length() >= 2) {
            controllerFind.search(newText);
        }

        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        resetFragmentData(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    void setState() {
        ((UsersListAdapter) rvFound.getAdapter()).resetData();
        if (isSearchMode) {
            viewPager.setVisibility(View.INVISIBLE);
            tabLayout.setVisibility(View.GONE);
            rvFound.setVisibility(View.VISIBLE);
        } else {
            rvFound.setVisibility(View.INVISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
        }
    }
}
