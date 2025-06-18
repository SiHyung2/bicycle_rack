package com.example.myapplication.ui.BikeComponent;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class BikeViewHolder extends RecyclerView.ViewHolder {
    TextView title; TextView address; TextView availableBike; RatingBar ratingBar; TextView is_exist_air_pump;
    public BikeViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.text_station_name);
        address = itemView.findViewById(R.id.text_station_address);
        availableBike = itemView.findViewById(R.id.text_available_bikes);
        is_exist_air_pump = itemView.findViewById(R.id.text_is_exist_air_pump);
        ratingBar = itemView.findViewById(R.id.bikerack_reminder);
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

    public void setIs_exist_air_pump(String is_exist_air_pump){
        if(is_exist_air_pump == null){
            is_exist_air_pump = "N";
        }
        this.is_exist_air_pump.setText("공기주입기여부 : " + is_exist_air_pump);
    }

}
