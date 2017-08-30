package com.msemyak.carsdb.di;

import android.content.Context;

import com.msemyak.carsdb.model.DBEngine;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseEngineModule {

    private static Context context;

    public DatabaseEngineModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public DBEngine provideDBEngine() {
        return new DBEngine(context);
    }

}
