package com.msemyak.carsdb.activities.owner_details.owner_garage_fragment;

import com.msemyak.carsdb.CarManagerApplication;
import com.msemyak.carsdb.model.Car;
import com.msemyak.carsdb.model.DBEngine;

import java.util.List;

import javax.inject.Inject;

public class OwnerGarageFragmentPresenter implements OwnerGarageContract.Presenter {

    private OwnerGarageContract.View myView;
    private List<Car> carsData;
    @Inject DBEngine dbController;
    private int ownerId;

    public OwnerGarageFragmentPresenter(int ownerId, OwnerGarageContract.View myView) {
        this.myView = myView;
        this.ownerId = ownerId;
        CarManagerApplication.getDatabaseEngineComponent().inject(this);
        initOwnersData();
    }

    private void initOwnersData() {
        carsData = dbController.getOwnerCars(ownerId);
        myView.showCarsList(carsData);
    }

    @Override
    public void removeDataAtPosition(int position) {
        dbController.linkCarToOwner(0, carsData.get(position).getId());
        carsData.remove(position);
        myView.notifyDataChange(position, carsData.size());
    }



}
