package com.msemyak.carsdb.activities.main.owners_list_fragment;

import com.msemyak.carsdb.CarManagerApplication;
import com.msemyak.carsdb.model.DBEngine;
import com.msemyak.carsdb.model.Owner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OwnersListFragmentPresenter implements OwnersListContract.Presenter {

    private OwnersListContract.View myView;
    private List<Owner> ownersData;
    private List<Integer> ownerCarCount;
    @Inject
    DBEngine dbController;

    public OwnersListFragmentPresenter(OwnersListContract.View myView) {
        this.myView = myView;
        CarManagerApplication.getDatabaseEngineComponent().inject(this);

        initOwnersData();
    }

    private void initOwnersData() {
        ownersData = dbController.getAllOwners();

        ownerCarCount = new ArrayList<>();

        for (int i = 0; i < ownersData.size(); i++) {
            ownerCarCount.add(i, dbController.getOwnerCarsCount(ownersData.get(i).getId()));
        }

        myView.showOwnersList(ownersData, ownerCarCount);
    }

    @Override
    public void removeDataAtPosition(int position) {
        dbController.deleteOwner(ownersData.get(position).getId());
        ownersData.remove(position);
        myView.notifyDataChange(position, ownersData.size());
    }

}