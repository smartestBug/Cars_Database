package com.msemyak.carsdb.activities.owner_details.owner_data_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.msemyak.carsdb.R;
import com.msemyak.carsdb.data.model.Owner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OwnerDataFragment extends Fragment implements OwnerDataContract.View {

    @BindView(R.id.tv_owner_name)
    TextView tvOwnerName;
    @BindView(R.id.tv_owner_midname)
    TextView tvOwnerMidname;
    @BindView(R.id.tv_owner_surname)
    TextView tvOwnerSurname;
    @BindView(R.id.tv_owner_passport)
    TextView tvOwnerPassport;
    @BindView(R.id.tv_owner_address)
    TextView tvOwnerAddress;

    private OwnerDataContract.Presenter myPresenter;

    public OwnerDataFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_owner_data, container, false);
        ButterKnife.bind(this, view);

        // получаем аргументы - id владельца
        int ownerId = getArguments().getInt("owner_id");

        // создаем новый презентер
        myPresenter = new OwnerDataFragmentPresenter(this);

        // получаем данные о владельце
        // TODO - нужно эту логику переносить в презентер
        Owner owner = myPresenter.getOwner(ownerId);

        // показываем данные о владельце
        tvOwnerName.setText(owner.getName());
        tvOwnerMidname.setText(owner.getMidname());
        tvOwnerSurname.setText(owner.getSurname());
        tvOwnerPassport.setText(owner.getPassport());
        tvOwnerAddress.setText(owner.getAddress());

        return view;
    }

}
