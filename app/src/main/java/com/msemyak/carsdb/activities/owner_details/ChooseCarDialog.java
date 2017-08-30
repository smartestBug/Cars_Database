package com.msemyak.carsdb.activities.owner_details;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.msemyak.carsdb.R;
import com.msemyak.carsdb.activities.adapters.RVAdapterCar;
import com.msemyak.carsdb.activities.adapters.RVItemClickListener;
import com.msemyak.carsdb.model.Car;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class ChooseCarDialog extends Dialog {

    @BindView(R.id.rv_cars_chooser)
    RecyclerView rvDialogCars;
    @BindView(R.id.btn_dialog_close)
    Button btnDialogClose;

    private int ownerId;
    private List<Car> carsWithoutOwnerList;
    private RVItemClickListener clickListener;

    ChooseCarDialog(Context context, int ownerId, List<Car> carsWithoutOwnersList, RVItemClickListener clickListener) {
        super(context);
        this.ownerId = ownerId;
        this.carsWithoutOwnerList = carsWithoutOwnersList;
        this.clickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_car_chooser);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        setCancelable(true);

        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel();
            }
        });

        ButterKnife.bind(this);

        rvDialogCars.setLayoutManager(new LinearLayoutManager(getContext()));

        RVAdapterCar rvDialogAdapter = new RVAdapterCar(carsWithoutOwnerList, RVAdapterCar.LIST_WITHOUT_DELETE_BUTTON, clickListener);
        rvDialogCars.setAdapter(rvDialogAdapter);

    }

    @OnClick(R.id.btn_dialog_close)
    public void onButtonClick() {
        cancel();
    }

}
