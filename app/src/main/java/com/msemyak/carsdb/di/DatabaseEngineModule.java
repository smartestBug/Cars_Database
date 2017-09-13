package com.msemyak.carsdb.di;

import android.content.Context;

import com.msemyak.carsdb.data.DBEngineSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

// модуль даггера для инъекции объекта управления базой данных
// дает нам объект класса DBEngineSQLite, Context получаем при инициации
@Module
public class DatabaseEngineModule {

    private Context context;

    public DatabaseEngineModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    DBEngineSQLite provideDBEngine() {
        return new DBEngineSQLite(context);
    }

}
