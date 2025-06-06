package com.example.myapplication.ui.BikeComponent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BikeAdapter extends RecyclerView.Adapter<BikeViewHolder> {
    List<BikeInfo> bikeInfoList = new ArrayList<>();

    @NonNull
    @Override
    public BikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bike_info_bottom_sheet, parent, false);
        return new BikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BikeViewHolder holder, int position) {
        holder.setTitle(bikeInfoList.get(position).getTitle());
        holder.setAddress(bikeInfoList.get(position).getAdress());
        holder.setAvailableBike(bikeInfoList.get(position).getAvailableBike());
    }

    @Override
    public int getItemCount() {
        return bikeInfoList.size();
    }
    public void addBikeInfo(BikeInfo bikeInfo){
        bikeInfoList.add(bikeInfo);
        this.notifyDataSetChanged();
    }

    public void clearBikeInfos(){
        bikeInfoList.clear();
    }
}
