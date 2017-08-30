package com.msemyak.carsdb.di;

import com.msemyak.carsdb.activities.add_car.AddEditCarActivityPresenter;
import com.msemyak.carsdb.activities.add_owner.AddEditOwnerActivityPresenter;
import com.msemyak.carsdb.activities.car_details.CarDetailsActivityPresenter;
import com.msemyak.carsdb.activities.main.cars_list_fragment.CarsListFragmentPresenter;
import com.msemyak.carsdb.activities.main.owners_list_fragment.OwnersListFragmentPresenter;
import com.msemyak.carsdb.activities.owner_details.OwnerDetailsActivityPresenter;
import com.msemyak.carsdb.activities.owner_details.owner_data_fragment.OwnerDataFragmentPresenter;
import com.msemyak.carsdb.activities.owner_details.owner_garage_fragment.OwnerGarageFragmentPresenter;

import javax.inject.Singleton;

import dagger.Component;

// комопнент для связи модулей даггера
// определяем куда будем делать инъекции объекта DBEngine
@Singleton
@Component(modules = {DatabaseEngineModule.class})
public interface DatabaseEngineComponent {

    void inject(OwnersListFragmentPresenter dbEngine);

    void inject(OwnerDetailsActivityPresenter dbEngine);

    void inject(OwnerDataFragmentPresenter dbEngine);

    void inject(CarsListFragmentPresenter dbEngine);

    void inject(CarDetailsActivityPresenter dbEngine);

    void inject(AddEditOwnerActivityPresenter dbEngine);

    void inject(AddEditCarActivityPresenter dbEngine);

    void inject(OwnerGarageFragmentPresenter dbEngine);

}
