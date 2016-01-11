package ru.byters.bcgithubusers.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import ru.byters.bcgithubusers.R;
import ru.byters.bcgithubusers.ui.adapters.ViewPagerAdapter;
import ru.byters.bcgithubusers.ui.fragments.FragmentList;

public class ActivityMain extends AppCompatActivity
        implements SearchView.OnQueryTextListener, ViewPager.OnPageChangeListener {

    ViewPagerAdapter adapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
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
    public boolean onQueryTextSubmit(String query) {
        //todo implement
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //todo implement
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
}
