package com.msemyak.carsdb;

import android.app.Application;

import com.msemyak.carsdb.di.DaggerDatabaseEngineComponent;
import com.msemyak.carsdb.di.DatabaseEngineComponent;
import com.msemyak.carsdb.di.DatabaseEngineModule;

public class CarManagerApplication extends Application {

    private static DatabaseEngineComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerDatabaseEngineComponent.builder()
                .databaseEngineModule(new DatabaseEngineModule(this))
                .build();
/*

        if (dbEngine.getOwnersCount() < 1) {
            dbEngine.addOwner(new Owner(0, "Max", "NIkolaevich", "Semyak", "AA123456", "Kiev, Zamok str."));
            dbEngine.addOwner(new Owner(0, "Петро", "Иванович", "Колесник", "RE76574393", "Львов, площа Рынок"));
            dbEngine.addOwner(new Owner(0, "Василий", "Сергеевич", "Шульга", "HY747474", "Сумы, ул. Шевченко 30, кв. 20"));
            dbEngine.addOwner(new Owner(0, "Иван", "Андреевич", "Сковорода", "US838383", "Киев, ул. Заньковецкая 30"));
            dbEngine.addOwner(new Owner(0, "Константин", "Петрович", "Долгий", "BD8562384", "Чернигов, центр"));
            dbEngine.addOwner(new Owner(0, "Николай", "Олегович", "Васильев", "OO8747474", "Одесса, Привоз 4, дом 54"));
            dbEngine.addOwner(new Owner(0, "Сергей", "Андреевич", "Сорокин", "KI8765653", "Львов, ул. Петлюры 77, кв. 99"));
            dbEngine.addOwner(new Owner(0, "Иван", "Иванович", "Бодяга", "EE8467464", "Александрия, ул. Бандеры 22, дом 5"));
        }

        if (dbEngine.getCarsCount() < 1) {
            dbEngine.addCar(new Car(0, 0, "Audi", "A6", "AA3424CE", "2015"));
            dbEngine.addCar(new Car(0, 0, "Audi", "A4", "BK3424CE", "2012"));
            dbEngine.addCar(new Car(0, 0, "Mercedes", "E420", "AT3424CE", "1999"));
            dbEngine.addCar(new Car(0, 0, "ZAZ", "303", "KI3424CE", "1988"));
            dbEngine.addCar(new Car(0, 0, "BMW", "3", "CE3424CE", "2012"));
            dbEngine.addCar(new Car(0, 0, "Ford", "Focus", "AC3424CE", "2014"));
            dbEngine.addCar(new Car(0, 0, "Citroen", "C4", "AA7323EE", "2016"));
            dbEngine.addCar(new Car(0, 0, "BMW", "5 series", "EE7323EE", "2015"));
            dbEngine.addCar(new Car(0, 0, "Seat", "Leon", "BK4913AM", "2008"));
            dbEngine.addCar(new Car(0, 0, "Honda", "Civic", "AE7323EE", "2013"));
        }
*/

    }

    public static DatabaseEngineComponent getDatabaseEngineComponent() {
        return component;
    }

}
