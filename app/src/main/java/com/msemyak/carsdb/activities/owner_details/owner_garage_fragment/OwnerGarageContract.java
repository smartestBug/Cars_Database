package com.msemyak.carsdb.activities.owner_details.owner_garage_fragment;

import com.msemyak.carsdb.model.Car;

import java.util.List;

public interface OwnerGarageContract {
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
