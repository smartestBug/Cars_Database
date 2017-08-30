package com.msemyak.carsdb.activities.add_owner;

import com.msemyak.carsdb.CarManagerApplication;
import com.msemyak.carsdb.model.DBEngine;
import com.msemyak.carsdb.model.Owner;

import javax.inject.Inject;

public class AddEditOwnerActivityPresenter implements AddEditOwnerContract.Presenter {

    private AddEditOwnerContract.View myView;
    @Inject
    DBEngine dbController;
    public static final int NO_SUCH_ID = -1;

    public AddEditOwnerActivityPresenter(AddEditOwnerContract.View myView, int ownerId) {

        this.myView = myView;
        CarManagerApplication.getDatabaseEngineComponent().inject(this);

        if (ownerId != NO_SUCH_ID) {
            Owner owner = dbController.getOwner(ownerId);
            myView.showOwnerData(owner.getName(), owner.getMidname(), owner.getSurname(), owner.getPassport(), owner.getAddress());
        }

    }

    @Override
    public void addOrUpdateOwner(int ownerId, String name, String midname, String surname, String passport, String address) {

        Owner owner = new Owner(ownerId, name, midname, surname, passport, address);

        if (ownerId == NO_SUCH_ID) ownerId = dbController.addOwner(owner);
        else dbController.updateOwner(owner);

        myView.navigateToOwnerDetailsActivity(ownerId);
    }

}