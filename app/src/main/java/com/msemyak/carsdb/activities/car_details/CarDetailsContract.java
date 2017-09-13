package com.msemyak.carsdb.activities.car_details;

import com.msemyak.carsdb.data.model.Car;
import com.msemyak.carsdb.data.model.Owner;

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
