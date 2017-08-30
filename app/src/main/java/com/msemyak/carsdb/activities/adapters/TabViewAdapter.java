package com.msemyak.carsdb.activities.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class TabViewAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentsList = new ArrayList<>();
    private final List<String> fragmentsTitleList = new ArrayList<>();

    public TabViewAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentsList.add(fragment);
        fragmentsTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitleList.get(position);
    }
}
