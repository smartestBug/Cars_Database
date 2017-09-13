package com.msemyak.carsdb.activities.add_owner;

import com.msemyak.carsdb.CarManagerApplication;
import com.msemyak.carsdb.data.DBEngineSQLite;
import com.msemyak.carsdb.data.model.Owner;

import javax.inject.Inject;

public class AddEditOwnerActivityPresenter implements AddEditOwnerContract.Presenter {

    private AddEditOwnerContract.View myView;
    @Inject
    DBEngineSQLite dbController;
    static final int NO_SUCH_ID = -1;

    AddEditOwnerActivityPresenter(AddEditOwnerContract.View myView, int ownerId) {

        this.myView = myView;
        CarManagerApplication.getDatabaseEngineComponent().inject(this);

        // если id владельца существующее, то показываем данные про этого владельца
        if (ownerId != NO_SUCH_ID) {
            Owner owner = dbController.getOwner(ownerId);
            myView.showOwnerData(owner.getName(), owner.getMidname(), owner.getSurname(), owner.getPassport(), owner.getAddress());
        }

    }

    @Override
    public void addOrUpdateOwner(int ownerId, String name, String midname, String surname, String passport, String address) {

        Owner owner = new Owner(ownerId, name, midname, surname, passport, address);

        // если id владельца не существует, то сохраняем нового владельца
        // иначе обновляем существующего
        if (ownerId == NO_SUCH_ID) ownerId = dbController.addOwner(owner);
        else dbController.updateOwner(owner);

        // делаем работу и просим View перейти куда нужно
        myView.navigateToOwnerDetailsActivity(ownerId);
    }

}
