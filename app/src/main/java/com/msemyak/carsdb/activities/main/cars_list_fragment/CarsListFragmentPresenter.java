package com.msemyak.carsdb.activities.main.cars_list_fragment;

import com.msemyak.carsdb.CarManagerApplication;
import com.msemyak.carsdb.model.Car;
import com.msemyak.carsdb.model.DBEngine;

import java.util.List;

import javax.inject.Inject;

public class CarsListFragmentPresenter implements CarsListContract.Presenter {

    private CarsListContract.View myView;
    private List<Car> carsData;
    @Inject
    DBEngine dbController;

    CarsListFragmentPresenter(CarsListContract.View myView) {
        this.myView = myView;
        CarManagerApplication.getDatabaseEngineComponent().inject(this);
        initCarsData();
    }

    // получаем список авто из базы, просим view показать
    private void initCarsData() {
        carsData = dbController.getAllCars();
        myView.showCarsList(carsData);
    }

    // удаляем данные, просим view среагировать
    @Override
    public void removeDataAtPosition(int position) {
        dbController.deleteCar(carsData.get(position).getId());
        carsData.remove(position);
        myView.notifyDataChange(position, carsData.size());
    }

}
