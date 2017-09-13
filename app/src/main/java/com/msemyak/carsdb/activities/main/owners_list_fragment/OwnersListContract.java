package com.msemyak.carsdb.activities.main.owners_list_fragment;

import com.msemyak.carsdb.data.model.Owner;

import java.util.List;

public interface OwnersListContract {

    // view contract
    interface View {
        void showOwnersList(List<Owner> ownersList, List<Integer> ownerCarsCount);

        void notifyDataChange(int position, int size);
    }

    //  presenter contract
    interface Presenter {
        void removeDataAtPosition(int position);
    }

}

