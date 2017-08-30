package com.msemyak.carsdb.di;

import android.content.Context;

import com.msemyak.carsdb.model.DBEngine;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

// модуль даггера для инъекции объекта управления базой данных
// дает нам объект класса DBEngine, Context получаем при инициации
@Module
public class DatabaseEngineModule {

    private Context context;

    public DatabaseEngineModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    DBEngine provideDBEngine() {
        return new DBEngine(context);
    }

}
