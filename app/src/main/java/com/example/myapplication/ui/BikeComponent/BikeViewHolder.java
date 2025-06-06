package com.example.myapplication.ui.BikeComponent;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class BikeViewHolder extends RecyclerView.ViewHolder {
    TextView title; TextView address; TextView availableBike;
    public BikeViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.text_station_name);
        address = itemView.findViewById(R.id.text_station_address);
        availableBike = itemView.findViewById(R.id.text_available_bikes);
    }

    public void setTitle(String title){
        this.title.setText(title);
    }
    public void setAddress(String address){
        this.address.setText(address);
    }

    public void setAvailableBike(int availableBike){
        this.availableBike.setText("사용가능한 자전거 : " + availableBike);
    }
}
