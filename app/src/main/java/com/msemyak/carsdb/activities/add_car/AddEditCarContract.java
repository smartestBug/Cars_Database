package com.msemyak.carsdb.activities.add_car;

public interface AddEditCarContract {
    // view contract
    interface View {
        void showCarData(String brand, String model, String year, String regnumber);

        void navigateToCarList(int ownerId);
    }

    //  presenter contract
    interface Presenter {
        void addOrUpdateCar(int carId, String brand, String model, String year, String regnumber);
    }
}
