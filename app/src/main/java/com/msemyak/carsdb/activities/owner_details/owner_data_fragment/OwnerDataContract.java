package com.msemyak.carsdb.activities.owner_details.owner_data_fragment;

import com.msemyak.carsdb.model.Owner;

public interface OwnerDataContract {
    // view contract
    interface View {
    }

    //  presenter contract
    interface Presenter {
        Owner getOwner(int ownerId);
    }
}
