package com.msemyak.carsdb.activities.main.cars_list_fragment;

import com.msemyak.carsdb.model.Car;

import java.util.List;

public interface CarsListContract {

    // view contract
    interface View {
        void showCarsList(List<Car> carsList);

        void notifyDataChange(int position, int size);
    }

    //  presenter contract
    interface Presenter {
        void removeDataAtPosition(int position);
    }

}

