package com.msemyak.carsdb.activities.add_owner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.msemyak.carsdb.R;
import com.msemyak.carsdb.activities.owner_details.OwnerDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditOwnerActivity extends AppCompatActivity implements AddEditOwnerContract.View {

    @BindView(R.id.btn_save_owner_data)
    Button btnAddCar;
    @BindView(R.id.toolbar_add_owner)
    Toolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_middlename)
    EditText etMiddlename;
    @BindView(R.id.et_surname)
    EditText etSurname;
    @BindView(R.id.et_passport)
    EditText etPassport;
    @BindView(R.id.et_address)
    EditText etAddress;

    private int ownerId;

    private AddEditOwnerContract.Presenter myPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_owner);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // получаем id владельца при старте этой активити
        // если id есть - будем редактировать существующего владельца
        // если нет - создаем нового (в этом случае идентификатор будет равен NO_SUCH_ID)
        ownerId = getIntent().getIntExtra("owner_id", AddEditOwnerActivityPresenter.NO_SUCH_ID);

        // создаем презентер, который инициирует данные и сразу попросит их отобразить
        myPresenter = new AddEditOwnerActivityPresenter(this, ownerId);

    }

    @Override
    public void showOwnerData(String name, String midname, String surname, String passport, String address) {
        etName.setText(name);
        etMiddlename.setText(midname);
        etSurname.setText(surname);
        etPassport.setText(passport);
        etAddress.setText(address);
    }

    @OnClick(R.id.btn_save_owner_data)
    public void onButtonClick(View view) {

        myPresenter.addOrUpdateOwner(ownerId, etName.getText().toString(), etMiddlename.getText().toString(), etSurname.getText().toString(), etPassport.getText().toString(), etAddress.getText().toString());

    }

    @Override
    public void navigateToOwnerDetailsActivity(int ownerId) {
        Intent profileIntent = new Intent(getApplicationContext(), OwnerDetailsActivity.class);
        profileIntent.putExtra("owner_id", ownerId);
        profileIntent.putExtra("tab_to_open", 0);
        startActivity(profileIntent);
    }

    // определяем что делать при нажатии кнопки навигации в тулбаре
    @Override
    public boolean onSupportNavigateUp() {
        setResult(Activity.RESULT_OK);
        finish();
        return true;
    }

}
