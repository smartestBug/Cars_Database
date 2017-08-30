package com.msemyak.carsdb.activities.car_details;

import com.msemyak.carsdb.CarManagerApplication;
import com.msemyak.carsdb.model.Car;
import com.msemyak.carsdb.model.DBEngine;
import com.msemyak.carsdb.model.Owner;

import javax.inject.Inject;

public class CarDetailsActivityPresenter implements CarDetailsContract.Presenter {

    private CarDetailsContract.View myView;
    @Inject
    DBEngine dbController;

    public CarDetailsActivityPresenter(CarDetailsContract.View myView) {
        this.myView = myView;
        CarManagerApplication.getDatabaseEngineComponent().inject(this);
    }

    @Override
    public Owner getOwner(int ownerId) {
        return dbController.getOwner(ownerId);
    }

    @Override
    public Car getCar(int carId) {
        return dbController.getCar(carId);
    }
}
