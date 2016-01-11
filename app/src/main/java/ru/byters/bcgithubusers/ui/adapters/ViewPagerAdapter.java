package ru.byters.bcgithubusers.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Locale;

import ru.byters.bcgithubusers.R;
import ru.byters.bcgithubusers.ui.fragments.FragmentList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    Context context;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentList.newInstance(FragmentList.FILTER_AH);
            case 1:
                return FragmentList.newInstance(FragmentList.FILTER_IP);
            case 2:
                return FragmentList.newInstance(FragmentList.FILTER_QZ);
            default:
                break;
        }
        return null;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return context.getString(R.string.pageAH).toUpperCase(l);
            case 1:
                return context.getString(R.string.pageIP).toUpperCase(l);
            case 2:
                return context.getString(R.string.pageQZ).toUpperCase(l);
        }
        return null;
    }
}
