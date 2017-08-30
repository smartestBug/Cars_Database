package com.msemyak.carsdb.eventbus;

// класс события для работы по шине Event Bus
// служит для информирования активити о том нужно ли спрятать плавающую кнопку
public class eventFabVisible {

    public boolean visible;

    public eventFabVisible setVisibility(boolean visible) {
        this.visible = visible;
        return this;
    }
}
