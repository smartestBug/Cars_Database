package com.msemyak.carsdb.data;

import com.msemyak.carsdb.data.model.Car;
import com.msemyak.carsdb.data.model.Owner;

import java.util.List;

interface DBEngine {

    // добавить новый автомобиль
    int addCar(Car car);

    // возвращает список всех автомобилей
    List<Car> getAllCars();

    // возвращает список всех автомобилей без владельца
    List<Car> getAllCarsWithoutOwner();

    // возвращает владельца конкретного автомобиля
    List<Car> getOwnerCars(int ownerId);

    // возвращает количество автомобилей, которые не принадлежат никомуу
    int getCarsWithoutOwnerCount();

    // возвращает конкретный автомобиль
    Car getCar(int id);

    // удаляем автомобиль
    void deleteCar(int carId);

    // обновляем данные про автомобиль
    int updateCar(Car car);

    // связываем владельца и автомобиль
    int linkCarToOwner(int ownerId, int carId);

    // создаем нового владельца
    int addOwner(Owner owner);

    // удаляем владельца
    void deleteOwner(int ownerId);

    // возвращает конкретного пользователя
    Owner getOwner(int id);

    // обновление записи конкретного владельца
    int updateOwner(Owner owner);

    // возвращает количество автомобилей, принадлежащих конкретному владельцу
    int getOwnerCarsCount(int ownerId);

    // возвращает список всех владельцев
    List<Owner> getAllOwners();
}
