package com.msemyak.carsdb.activities.add_car;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.msemyak.carsdb.R;
import com.msemyak.carsdb.activities.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditCarActivity extends AppCompatActivity implements AddEditCarContract.View {

    @BindView(R.id.btn_save_car_data)
    Button btnAddCar;
    @BindView(R.id.toolbar_add_car)
    Toolbar toolbar;
    @BindView(R.id.et_brand)
    EditText etBrand;
    @BindView(R.id.et_model)
    EditText etModel;
    @BindView(R.id.et_year)
    EditText etYear;
    @BindView(R.id.et_regnumber)
    EditText etRegnumber;

    private AddEditCarContract.Presenter myPresenter;

    private int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // получаем id автомобиля при старте этой активити
        // если id есть - будем редактировать существующий авто
        // если нет - создаем новый (в этом случае идентификатор будет равен NO_SUCH_ID)
        carId = getIntent().getIntExtra("car_id", AddEditCarActivityPresenter.NO_SUCH_ID);

        // создаем презентер, который инициирует данные и сразу попросит их отобразить
        myPresenter = new AddEditCarActivityPresenter(this, carId);

    }

    // просим презентера сохранить данные об автомобиле
    @OnClick(R.id.btn_save_car_data)
    public void onButtonClick(View view) {
        myPresenter.addOrUpdateCar(carId, etBrand.getText().toString(), etModel.getText().toString(), etYear.getText().toString(), etRegnumber.getText().toString());
    }

    // показываем данные автомобиля
    @Override
    public void showCarData(String brand, String model, String year, String regnumber) {
        etBrand.setText(brand);
        etModel.setText(model);
        etYear.setText(year);
        etRegnumber.setText(regnumber);
    }

    // переход к списку
    @Override
    public void navigateToCarList(int ownerId) {
        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
        mainIntent.putExtra("car_id", carId);
        mainIntent.putExtra("tab_to_open", 1);
        startActivity(mainIntent);
    }

    // определяем что делать при нажатии кнопки навигации в тулбаре
    @Override
    public boolean onSupportNavigateUp() {
        setResult(Activity.RESULT_OK);
        finish();
        return true;
    }

}
