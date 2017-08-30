package com.msemyak.carsdb.activities.owner_details.owner_garage_fragment;

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
import com.msemyak.carsdb.model.Car;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OwnerGarageFragment extends Fragment implements OwnerGarageContract.View, RVItemClickListener {

    private RVAdapterCar listAdapter;
    private int ownerId;
    private OwnerGarageContract.Presenter myPresenter;

    @BindView(R.id.rv_owner_cars) RecyclerView rvCars;

    public OwnerGarageFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_owner_garage, container, false);
        ButterKnife.bind(this, view);

        ButterKnife.bind(this, view);

        ownerId = getArguments().getInt("owner_id");

        myPresenter = new OwnerGarageFragmentPresenter(ownerId, this);

        return view;
    }

    @Override
    public void showCarsList(List<Car> carsList) {

        rvCars.setLayoutManager(new LinearLayoutManager(getContext()));

        listAdapter = new RVAdapterCar(carsList, RVAdapterCar.LIST_WITH_DELETE_BUTTON, this);
        rvCars.setAdapter(listAdapter);
    }

    @Override
    public void notifyDataChange(int position, int size) {
        listAdapter.notifyItemRemoved(position);
        listAdapter.notifyItemRangeChanged(position, size);
    }

    @Override
    public void onRVItemClick(View v, int carId) {
        Intent carIntent = new Intent(getActivity(), CarDetailsActivity.class);
        carIntent.putExtra("car_id", carId);
        carIntent.putExtra("owner_id", ownerId);
        carIntent.putExtra("tab_to_open", 1);
        getActivity().startActivity(carIntent);
    }

    @Override
    public void onRVDeleteButtonClick(int id) {
        myPresenter.removeDataAtPosition(id);
    }


}
