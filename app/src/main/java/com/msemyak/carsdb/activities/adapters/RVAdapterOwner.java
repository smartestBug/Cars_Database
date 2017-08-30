package com.msemyak.carsdb.activities.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.msemyak.carsdb.R;
import com.msemyak.carsdb.model.Owner;

import java.util.List;

public class RVAdapterOwner extends RecyclerView.Adapter<RVAdapterOwner.myViewHolder> {
    private List<Owner> ownersData;
    private List<Integer> ownerCarsCount;
    private RVItemClickListener clickListener;

    public RVAdapterOwner(List<Owner> ownersData, List<Integer> ownerCarsCount, RVItemClickListener clickListener) {
        this.ownersData = ownersData;
        this.ownerCarsCount = ownerCarsCount;
        this.clickListener = clickListener;
    }

    @Override
    public RVAdapterOwner.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_owner_rv, parent, false);
        return new myViewHolder(v, clickListener);
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {

        Owner ownerForDataBind = ownersData.get(position);

        holder.Id = ownerForDataBind.getId();
        holder.Name.setText(ownerForDataBind.getName() + " " + ownerForDataBind.getSurname());
        if (ownerCarsCount != null)
            holder.CarsNumber.setText(ownerCarsCount.get(position).toString());

        // ставим слушатель на кнопку удаления элемента - отправляем в слушатель, котоорый определили в конструкторе
        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onRVDeleteButtonClick(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return ownersData.size();
    }

    // внутренний класс вьюхолдера для адаптера
    static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int Id;
        public TextView Name;
        public TextView CarsNumber;
        public ImageButton DeleteButton;
        RVItemClickListener clickListener;

        public myViewHolder(View v, RVItemClickListener clickListener) {
            super(v);
            Name = (TextView) v.findViewById(R.id.tv_owner_item_name);
            CarsNumber = (TextView) v.findViewById(R.id.tv_owner_item_cars_number);
            DeleteButton = (ImageButton) v.findViewById(R.id.ibDelete);
            this.clickListener = clickListener;
            v.setOnClickListener(this);
        }

        // обрабатывем нажатие на элемент списка - прокидывем дальше в слушатель, который зарегистрировали в конструкторе
        @Override
        public void onClick(View v) {
            clickListener.onRVItemClick(v, Id);
        }
    }

}
