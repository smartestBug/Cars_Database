package com.msemyak.carsdb.activities.owner_details;

import com.msemyak.carsdb.data.model.Car;
import com.msemyak.carsdb.data.model.Owner;

import java.util.List;

public interface OwnerDetailsContract {
    // view contract
    interface View {
        void showCarChooserDialog(List<Car> carList);
    }

    //  presenter contract
    interface Presenter {
        Owner getOwner(int ownerId);

        int getCarsWithoutOwnerCount();

        void initCarsWithoutOwnerList();

        void linkCarToOwner(int ownerId, int carId);
    }
}
