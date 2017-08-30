package com.msemyak.carsdb.activities.owner_details;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.msemyak.carsdb.R;
import com.msemyak.carsdb.activities.adapters.RVItemClickListener;
import com.msemyak.carsdb.activities.add_owner.AddEditOwnerActivity;
import com.msemyak.carsdb.activities.owner_details.owner_data_fragment.OwnerDataFragment;
import com.msemyak.carsdb.activities.owner_details.owner_garage_fragment.OwnerGarageFragment;
import com.msemyak.carsdb.model.Car;
import com.msemyak.carsdb.model.Owner;

import com.msemyak.carsdb.activities.adapters.TabViewAdapter;
import com.msemyak.carsdb.activities.main.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OwnerDetailsActivity extends AppCompatActivity implements OwnerDetailsContract.View, RVItemClickListener {

    private OwnerDetailsContract.Presenter myPresenter;
    private Owner owner;
    private int ownerId;
    private int tabToOpen;

    @BindView(R.id.toolbar_profile)
    Toolbar toolbar;
    @BindView(R.id.viewpager_profile)
    ViewPager viewPager;
    @BindView(R.id.tabs_profile)
    TabLayout tabLayout;
    @BindView(R.id.fab_profile)
    FloatingActionButton fabProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_details);

        ButterKnife.bind(this);

        myPresenter = new OwnerDetailsActivityPresenter(this);

        // полцчаем аргументы - id владельца и какую вкладку открывать
        ownerId = getIntent().getIntExtra("owner_id", 0);
        tabToOpen = getIntent().getIntExtra("tab_to_open", 0);

        owner = myPresenter.getOwner(ownerId);

        setupActionBar();
        setupViewPager(viewPager, ownerId);
        setupTabLayout(viewPager);

        viewPager.setCurrentItem(tabToOpen);

    }

    private void setupTabLayout(ViewPager vPager) {
        tabLayout.setupWithViewPager(vPager);
        setFabIcon(tabToOpen);

        // устанавливаем слушатель на переключение вкладок - нужно для смены иконки на поавающей кнопке
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setFabIcon(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_upward);
        toolbar.setTitle(owner.getName() + " " + owner.getSurname());
    }

    // переключение иконок на плавающей кнопке в зависимости от выбранной вкладки
    private void setFabIcon(int selectedTabPosition) {
        fabProfile.setImageDrawable((selectedTabPosition == 0) ?
                ContextCompat.getDrawable(this, R.drawable.ic_edit) :
                ContextCompat.getDrawable(this, R.drawable.ic_add));
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent startIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(startIntent);
        return true;
    }

    // обработчик нажатия на плавающую кнопку
    // если нажата при вкладке 0 (данные владельца), то отправляемся редактироваль эти данные
    // если при вкладке 1 (гараж владельца), то нужно добавить к гаражу автомобиль - показываем диалоговое окно со списком автомобилей
    @OnClick(R.id.fab_profile)
    public void onButtonClick(View view) {
        if (tabLayout.getSelectedTabPosition() == 0) {
            Intent editIntent = new Intent(getApplicationContext(), AddEditOwnerActivity.class);
            editIntent.putExtra("owner_id", ownerId);
            startActivity(editIntent);
        } else {
            // если нет свободных авто (у каждого есть свой владелец), показываем сообщение
            if (myPresenter.getCarsWithoutOwnerCount() == 0) showNoFreeCarsAlert();
            //иначе показываем инициируем данные для списка с доступными автомобилями
            else myPresenter.initCarsWithoutOwnerList();
        }
    }

    @Override
    public void showCarChooserDialog(List<Car> carList) {
        ChooseCarDialog carChoose = new ChooseCarDialog(this, ownerId, carList, this);
        carChoose.show();
    }

    // сообщение, что нет незанятых автомобилей
    private void showNoFreeCarsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.message)
                .setMessage(R.string.no_free_cars_msg)
                .setCancelable(false)
                .setNegativeButton(R.string.ok_text,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void setupViewPager(ViewPager viewPager, int ownerId) {
        TabViewAdapter tabAdapter = new TabViewAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putInt("owner_id", ownerId);

        OwnerDataFragment ownerDataFragment = new OwnerDataFragment();
        ownerDataFragment.setArguments(bundle);

        OwnerGarageFragment ownerGarageFragment = new OwnerGarageFragment();
        ownerGarageFragment.setArguments(bundle);

        tabAdapter.addFragment(ownerDataFragment, getString(R.string.personal_data));
        tabAdapter.addFragment(ownerGarageFragment, getString(R.string.garage));

        viewPager.setAdapter(tabAdapter);
    }

    // обработчик нажатия на списке со свободными автомобилями
    @Override
    public void onRVItemClick(View v, int carId) {

        // связываем автомобиль и владельца
        myPresenter.linkCarToOwner(ownerId, carId);

        // переходим на информацию о владельце
        Intent carIntent = new Intent(this, OwnerDetailsActivity.class);
        carIntent.putExtra("car_id", carId);
        carIntent.putExtra("owner_id", ownerId);
        carIntent.putExtra("tab_to_open", 1);
        startActivity(carIntent);
    }

    @Override
    public void onRVDeleteButtonClick(int id) {

    }
}
