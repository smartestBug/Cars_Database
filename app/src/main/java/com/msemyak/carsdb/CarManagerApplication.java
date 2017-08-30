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
    }

    public static DatabaseEngineComponent getDatabaseEngineComponent() {
        return component;
    }

}
