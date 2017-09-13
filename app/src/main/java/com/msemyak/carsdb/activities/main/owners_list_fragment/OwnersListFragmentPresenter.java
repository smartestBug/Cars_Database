package com.msemyak.carsdb.activities.main.owners_list_fragment;

import com.msemyak.carsdb.CarManagerApplication;
import com.msemyak.carsdb.data.DBEngineSQLite;
import com.msemyak.carsdb.data.model.Owner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OwnersListFragmentPresenter implements OwnersListContract.Presenter {

    private OwnersListContract.View myView;
    private List<Owner> ownersData;
    private List<Integer> ownerCarCount;
    @Inject
    DBEngineSQLite dbController;

    OwnersListFragmentPresenter(OwnersListContract.View myView) {
        this.myView = myView;
        CarManagerApplication.getDatabaseEngineComponent().inject(this);

        initOwnersData();
    }

    // получаем список владельцев из базы, просим view показать
    private void initOwnersData() {
        ownersData = dbController.getAllOwners();

        ownerCarCount = new ArrayList<>();

        // формируем список количества авто по каждому владельцу
        // не самое удачное решение
        // TODO - рефакторить
        for (int i = 0; i < ownersData.size(); i++) {
            ownerCarCount.add(i, dbController.getOwnerCarsCount(ownersData.get(i).getId()));
        }

        myView.showOwnersList(ownersData, ownerCarCount);
    }

    // удаляем данные, просим view среагировать
    @Override
    public void removeDataAtPosition(int position) {
        dbController.deleteOwner(ownersData.get(position).getId());
        ownersData.remove(position);
        ownerCarCount.remove(position);
        myView.notifyDataChange(position, ownersData.size());
    }

}
