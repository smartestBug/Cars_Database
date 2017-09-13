package com.msemyak.carsdb.activities.main.cars_list_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msemyak.carsdb.R;
import com.msemyak.carsdb.activities.adapters.RVAdapterCar;
import com.msemyak.carsdb.activities.adapters.RVItemClickListener;
import com.msemyak.carsdb.activities.car_details.CarDetailsActivity;

import com.msemyak.carsdb.eventbus.eventFabVisible;
import com.msemyak.carsdb.data.model.Car;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarsListFragment extends Fragment implements CarsListContract.View, RVItemClickListener {

    private RVAdapterCar listAdapter;
    private CarsListContract.Presenter myPresenter;

    @BindView(R.id.rv_cars_list)
    RecyclerView rvCars;

    private eventFabVisible fabVisibility;

    public CarsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_car, container, false);
        ButterKnife.bind(this, view);

        // создадим презентер для этого фрагмента
        myPresenter = new CarsListFragmentPresenter(this);

        // создадим событие для Event Bus загодя
        fabVisibility = new eventFabVisible();

        // обработчик прокрутки для того, чтобы убрать или показать плавающую кнопку
        rvCars.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    // шлем просьбу спрятать плавающую кнопку по шине
                    EventBus.getDefault().post(fabVisibility.setVisibility(false));
                } else {
                    // шлем просьбу показать плавающую кнопку по шине
                    EventBus.getDefault().post(fabVisibility.setVisibility(true));
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });

        return view;
    }

    // создаем адаптер и показываем список всех автомобилей
    @Override
    public void showCarsList(List<Car> carsList) {

        rvCars.setLayoutManager(new LinearLayoutManager(getContext()));

        listAdapter = new RVAdapterCar(carsList, RVAdapterCar.LIST_WITH_DELETE_BUTTON, this);
        rvCars.setAdapter(listAdapter);
    }

    // просим адаптер обновить данные списка
    @Override
    public void notifyDataChange(int position, int size) {
        listAdapter.notifyItemRemoved(position);
        listAdapter.notifyItemRangeChanged(position, size);
    }

    @Override
    public void onRVItemClick(View v, int id) {
        Intent carIntent = new Intent(getContext(), CarDetailsActivity.class);
        carIntent.putExtra("car_id", id);
        carIntent.putExtra("tab_to_open", 0);
        getContext().startActivity(carIntent);
    }

    @Override
    public void onRVDeleteButtonClick(int id) {
        myPresenter.removeDataAtPosition(id);
    }


}
