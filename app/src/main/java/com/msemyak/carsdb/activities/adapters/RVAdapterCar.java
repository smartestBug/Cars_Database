package com.msemyak.carsdb.activities.adapters;

import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.msemyak.carsdb.R;
import com.msemyak.carsdb.data.model.Car;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class RVAdapterCar extends RecyclerView.Adapter<RVAdapterCar.myViewHolder> {

    private List<Car> carsData;
    @listType
    private int listVariant;
    private RVItemClickListener clickListener;

    // этот адаптер может показывать элементы как с кнопкой удаления так и без нее
    // определяем константы для определения типа адаптера
    public static final int LIST_WITH_DELETE_BUTTON = 0;
    public static final int LIST_WITHOUT_DELETE_BUTTON = 1;

    @IntDef({LIST_WITH_DELETE_BUTTON, LIST_WITHOUT_DELETE_BUTTON})
    @Retention(RetentionPolicy.SOURCE)
    public @interface listType { }

    public RVAdapterCar(List<Car> carsData, @listType int listVariant, RVItemClickListener clickListener) {
        this.carsData = carsData;
        this.listVariant = listVariant;
        this.clickListener = clickListener;
    }

    @Override
    public RVAdapterCar.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car_rv, parent, false);
        return new myViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {

        Car carForDataBind = carsData.get(position);

        holder.carId = carForDataBind.getId();
        holder.BrandAndModel.setText(carForDataBind.getBrand() + " " + carForDataBind.getModel());
        holder.Regnumber.setText(carForDataBind.getRegnumber());

        if (listVariant == LIST_WITH_DELETE_BUTTON) {
            // ставим слушатель на кнопку удаления элемента - отправляем в слушатель, котоорый определили в конструкторе
            holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onRVDeleteButtonClick(holder.getAdapterPosition());
                }
            });
        } else {
            holder.DeleteButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return carsData.size();
    }

    // внутренний класс вьюхолдера для адаптера
    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int carId;
        public TextView BrandAndModel;
        public TextView Regnumber;
        public ImageButton DeleteButton;
        RVItemClickListener clickListener;

        public myViewHolder(View v, RVItemClickListener clickListener) {
            super(v);
            BrandAndModel = (TextView) v.findViewById(R.id.tv_brand_model);
            Regnumber = (TextView) v.findViewById(R.id.tv_regnumber);
            DeleteButton = (ImageButton) v.findViewById(R.id.ib_delete);
            this.clickListener = clickListener;
            v.setOnClickListener(this);
        }

        // обрабатывем нажатие на элемент списка - прокидывем дальше в слушатель, который зарегистрировали в конструкторе
        @Override
        public void onClick(View v) {
                clickListener.onRVItemClick(v, carId);
        }

    }

}


