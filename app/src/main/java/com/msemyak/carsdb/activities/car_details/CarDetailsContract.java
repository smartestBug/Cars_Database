package com.msemyak.carsdb.activities.car_details;

import com.msemyak.carsdb.model.Car;
import com.msemyak.carsdb.model.Owner;

public interface CarDetailsContract {
    // view contract
    interface View {
    }

    //  presenter contract
    interface Presenter {
        Owner getOwner(int ownerId);

        Car getCar(int carId);
    }
}
