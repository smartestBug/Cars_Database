package com.msemyak.carsdb.activities.car_details;

import com.msemyak.carsdb.CarManagerApplication;
import com.msemyak.carsdb.data.DBEngineSQLite;
import com.msemyak.carsdb.data.model.Car;
import com.msemyak.carsdb.data.model.Owner;

import javax.inject.Inject;

public class CarDetailsActivityPresenter implements CarDetailsContract.Presenter {

    private CarDetailsContract.View myView;
    @Inject
    DBEngineSQLite dbController;

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
