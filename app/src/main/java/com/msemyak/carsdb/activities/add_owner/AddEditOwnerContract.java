package com.msemyak.carsdb.activities.add_owner;

public interface AddEditOwnerContract {
    // view contract
    interface View {
        void showOwnerData(String name, String midname, String surname, String passport, String address);

        void navigateToOwnerDetailsActivity(int ownerId);
    }

    //  presenter contract
    interface Presenter {
        void addOrUpdateOwner(int ownerId, String name, String midname, String surname, String passport, String address);
    }
}
