package com.msemyak.carsdb.activities.adapters;

import android.view.View;

public interface RVItemClickListener {

    void onRVItemClick(View v, int id);

    void onRVDeleteButtonClick(int id);
}
