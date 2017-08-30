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
    private static RVItemClickListener clickListener;

    public RVAdapterOwner(List<Owner> ownersData, List<Integer> ownerCarsCount, RVItemClickListener clickListener) {
        this.ownersData = ownersData;
        this.ownerCarsCount = ownerCarsCount;
        this.clickListener = clickListener;
    }

    @Override
    public RVAdapterOwner.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_owner_rv, parent, false);
        myViewHolder vh = new myViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

        Owner ownerForDataBind = ownersData.get(position);

        holder.position = position;
        holder.Id = ownerForDataBind.getId();
        holder.Name.setText(ownerForDataBind.getName() + " " + ownerForDataBind.getSurname());
        if (ownerCarsCount != null)
            holder.CarsNumber.setText(ownerCarsCount.get(position).toString());

        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onRVDeleteButtonClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ownersData.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int Id;
        public TextView Name;
        public TextView CarsNumber;
        public ImageButton DeleteButton;
        public int position;


        public myViewHolder(View v) {
            super(v);
            Name = (TextView) v.findViewById(R.id.tv_owner_item_name);
            CarsNumber = (TextView) v.findViewById(R.id.tv_owner_item_cars_number);
            DeleteButton = (ImageButton) v.findViewById(R.id.ibDelete);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onRVItemClick(v, Id);
        }
    }

}
