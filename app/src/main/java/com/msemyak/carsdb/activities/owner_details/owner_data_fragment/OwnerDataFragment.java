package com.msemyak.carsdb.activities.owner_details.owner_data_fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.msemyak.carsdb.R;
import com.msemyak.carsdb.model.Owner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OwnerDataFragment extends Fragment implements OwnerDataContract.View {

    private int ownerId;

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

        ownerId = getArguments().getInt("owner_id");

        myPresenter = new OwnerDataFragmentPresenter(this);

        Owner owner = myPresenter.getOwner(ownerId);

        tvOwnerName.setText(owner.getName());
        tvOwnerMidname.setText(owner.getMidname());
        tvOwnerSurname.setText(owner.getSurname());
        tvOwnerPassport.setText(owner.getPassport());
        tvOwnerAddress.setText(owner.getAddress());

        return view;
    }

}
