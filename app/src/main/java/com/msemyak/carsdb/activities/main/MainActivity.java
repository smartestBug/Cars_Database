package com.msemyak.carsdb.activities.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.msemyak.carsdb.R;
import com.msemyak.carsdb.activities.add_car.AddEditCarActivity;
import com.msemyak.carsdb.activities.add_owner.AddEditOwnerActivity;
import com.msemyak.carsdb.activities.adapters.TabViewAdapter;
import com.msemyak.carsdb.activities.main.cars_list_fragment.CarsListFragment;
import com.msemyak.carsdb.activities.main.owners_list_fragment.OwnersListFragment;
import com.msemyak.carsdb.eventbus.eventFabVisible;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private TabViewAdapter tabAdapter;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_man);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_car);

        int tabToOpen = getIntent().getIntExtra("tab_to_open", 0);
        viewPager.setCurrentItem(tabToOpen);

    }

    @OnClick(R.id.fab)
    public void onButtonClick(View view) {
        startActivity((tabLayout.getSelectedTabPosition() == 0) ?
                (new Intent(this, AddEditOwnerActivity.class)) :
                (new Intent(this, AddEditCarActivity.class)));
    }

    private void setupViewPager(ViewPager viewPager) {
        tabAdapter = new TabViewAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new OwnersListFragment(), getString(R.string.tab_title_owner));
        tabAdapter.addFragment(new CarsListFragment(), getString(R.string.tab_title_car));
        viewPager.setAdapter(tabAdapter);
    }

    // Event bus
    @Subscribe
    public void onMessageEvent(eventFabVisible fabVisibility) {

        if (fabVisibility.visible) fab.show();
        else fab.hide();

    }

    ;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
