package com.msemyak.carsdb.activities.car_details;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.msemyak.carsdb.R;
import com.msemyak.carsdb.model.Car;
import com.msemyak.carsdb.model.Owner;
import com.msemyak.carsdb.activities.add_car.AddEditCarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CarDetailsActivity extends AppCompatActivity implements CarDetailsContract.View {

    @BindView(R.id.toolbar_car_profile)
    Toolbar toolbar;
    @BindView(R.id.fab_car_profile)
    FloatingActionButton fab;
    @BindView(R.id.tv_car_brand)
    TextView tvCarBrand;
    @BindView(R.id.tv_car_model)
    TextView tvCarModel;
    @BindView(R.id.tv_car_year)
    TextView tvCarYear;
    @BindView(R.id.tv_car_regnumber)
    TextView tvCarRegnumber;
    @BindView(R.id.tv_car_owner)
    TextView tvCarOwner;

    private CarDetailsContract.Presenter myPresenter;
    private int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // получаем переданный id автомобиля
        carId = getIntent().getIntExtra("car_id", 0);

        myPresenter = new CarDetailsActivityPresenter(this);

        // получаем объект автомобиля
        // вообще, для View это слишком много логики
        // TODO - рефакторить, переносить логику в презентер
        Car car = myPresenter.getCar(carId);

        // данные о машине
        tvCarBrand.setText(car.getBrand());
        tvCarModel.setText(car.getModel());
        tvCarYear.setText(car.getYear());
        tvCarRegnumber.setText(car.getRegnumber());

        // смотрим кто владелец
        if (car.getOwnerId() != 0) {
            Owner carOwner = myPresenter.getOwner(car.getOwnerId());
            tvCarOwner.setText(carOwner.getName() + " " + carOwner.getSurname());
        } else
            tvCarOwner.setText("нет");
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(Activity.RESULT_OK);
        finish();
        return true;
    }

    @OnClick(R.id.fab_car_profile)
    public void onButtonClick(View view) {

        Intent editIntent = new Intent(getApplicationContext(), AddEditCarActivity.class);
        editIntent.putExtra("car_id", carId);
        startActivity(editIntent);

    }

}
