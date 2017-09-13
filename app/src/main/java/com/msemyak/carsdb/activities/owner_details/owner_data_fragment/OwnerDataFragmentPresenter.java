package com.msemyak.carsdb.activities.owner_details.owner_data_fragment;

import com.msemyak.carsdb.CarManagerApplication;
import com.msemyak.carsdb.data.DBEngineSQLite;
import com.msemyak.carsdb.data.model.Owner;

import javax.inject.Inject;


public class OwnerDataFragmentPresenter implements OwnerDataContract.Presenter {

    private OwnerDataContract.View myView;
    @Inject
    DBEngineSQLite dbController;

    public OwnerDataFragmentPresenter(OwnerDataContract.View myView) {
        this.myView = myView;
        CarManagerApplication.getDatabaseEngineComponent().inject(this);
    }

    @Override
    public Owner getOwner(int ownerId) {
        return dbController.getOwner(ownerId);
    }
}
