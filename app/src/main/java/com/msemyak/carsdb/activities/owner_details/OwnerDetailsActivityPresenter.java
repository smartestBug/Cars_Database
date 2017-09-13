package com.msemyak.carsdb.activities.owner_details;

import com.msemyak.carsdb.CarManagerApplication;
import com.msemyak.carsdb.data.DBEngineSQLite;
import com.msemyak.carsdb.data.model.Owner;

import javax.inject.Inject;

public class OwnerDetailsActivityPresenter implements OwnerDetailsContract.Presenter {

    private OwnerDetailsContract.View myView;
    @Inject
    DBEngineSQLite dbController;

    public OwnerDetailsActivityPresenter(OwnerDetailsContract.View myView) {
        this.myView = myView;
        CarManagerApplication.getDatabaseEngineComponent().inject(this);
    }

    @Override
    public Owner getOwner(int ownerId) {
        return dbController.getOwner(ownerId);
    }

    @Override
    public int getCarsWithoutOwnerCount() {
        return dbController.getCarsWithoutOwnerCount();
    }

    @Override
    public void initCarsWithoutOwnerList() {
        myView.showCarChooserDialog(dbController.getAllCarsWithoutOwner());
    }

    @Override
    public void linkCarToOwner(int ownerId, int carId) {
        dbController.linkCarToOwner(ownerId, carId);
    }
}
