package com.msemyak.carsdb.activities.add_car;

import com.msemyak.carsdb.CarManagerApplication;
import com.msemyak.carsdb.model.Car;
import com.msemyak.carsdb.model.DBEngine;

import javax.inject.Inject;

public class AddEditCarActivityPresenter implements AddEditCarContract.Presenter {

    private AddEditCarContract.View myView;
    @Inject
    DBEngine dbController;
    static final int NO_SUCH_ID = -1;

    AddEditCarActivityPresenter(AddEditCarContract.View myView, int carId) {
        this.myView = myView;
        CarManagerApplication.getDatabaseEngineComponent().inject(this);

        // если id автомобиля существующее, то показываем данные про это авто
        if (carId != NO_SUCH_ID) {
            Car car = dbController.getCar(carId);
            myView.showCarData(car.getBrand(), car.getModel(), car.getYear(), car.getRegnumber());
        }
    }

    @Override
    public void addOrUpdateCar(int carId, String brand, String model, String year, String regnumber) {

        Car car = new Car(carId, 0, brand, model, year, regnumber);

        // если id автомобиля не существует, то сохраняем новый авто
        // иначе обновляем существующий
        if (carId == NO_SUCH_ID) carId = dbController.addCar(car);
        else dbController.updateCar(car);

        // делаем работу и просим View перейти куда нужно
        myView.navigateToCarList(carId);

    }
}
