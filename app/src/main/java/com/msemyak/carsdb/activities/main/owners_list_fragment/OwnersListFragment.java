package com.msemyak.carsdb.activities.main.owners_list_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msemyak.carsdb.R;
import com.msemyak.carsdb.activities.adapters.RVAdapterOwner;
import com.msemyak.carsdb.activities.adapters.RVItemClickListener;
import com.msemyak.carsdb.activities.owner_details.OwnerDetailsActivity;
import com.msemyak.carsdb.eventbus.eventFabVisible;
import com.msemyak.carsdb.model.Owner;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OwnersListFragment extends Fragment implements OwnersListContract.View, RVItemClickListener {

    private RecyclerView.Adapter listAdapter;
    private OwnersListContract.Presenter myPresenter;

    @BindView(R.id.rv_owners_list)
    RecyclerView rvOwners;

    private eventFabVisible fabVisibility;

    public OwnersListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_owner, container, false);
        ButterKnife.bind(this, view);

        // создадим презентер для этого фрагмента
        myPresenter = new OwnersListFragmentPresenter(this);

        // создадим событие для Event Bus загодя
        fabVisibility = new eventFabVisible();

        // обработчик прокрутки для того, чтобы убрать или показать плавающую кнопку
        rvOwners.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

    // создаем адаптер и показываем список всех владельцев
    @Override
    public void showOwnersList(List<Owner> ownersList, List<Integer> ownerCarsCount) {

        rvOwners.setLayoutManager(new LinearLayoutManager(getContext()));

        listAdapter = new RVAdapterOwner(ownersList, ownerCarsCount, this);
        rvOwners.setAdapter(listAdapter);
    }

    // просим адаптер обновить данные списка
    @Override
    public void notifyDataChange(int position, int size) {
        listAdapter.notifyItemRemoved(position);
        listAdapter.notifyItemRangeChanged(position, size);
    }

    @Override
    public void onRVItemClick(View v, int id) {
        Intent ownerIntent = new Intent(getContext(), OwnerDetailsActivity.class);
        ownerIntent.putExtra("owner_id", id);
        ownerIntent.putExtra("tab_to_open", 0);
        getContext().startActivity(ownerIntent);
    }

    @Override
    public void onRVDeleteButtonClick(int id) {
        myPresenter.removeDataAtPosition(id);
    }

}
