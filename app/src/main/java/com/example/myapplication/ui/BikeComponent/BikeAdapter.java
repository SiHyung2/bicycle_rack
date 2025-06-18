package com.example.myapplication.ui.BikeComponent;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BikeAdapter extends RecyclerView.Adapter<BikeViewHolder> {
    List<BikeRack> bikeRackList = new ArrayList<>();

    @NonNull
    @Override
    public BikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bike_info_bottom_sheet, parent, false);
        return new BikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BikeViewHolder holder, int position) {
        holder.setTitle(bikeRackList.get(position).getName());
        holder.setAddress(bikeRackList.get(position).getRoadAddress());
        holder.setAvailableBike(bikeRackList.get(position).getAvailableBike());
        holder.setIs_exist_air_pump(bikeRackList.get(position).getIs_exist_air_pump());
        Context context = holder.itemView.getContext();

        // SharedPreferences을 통하여 거치위치 저장한 것은 캐쉬로 계속 저장되어있음!
        SharedPreferences prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE);
        String key = bikeRackList.get(position).getName();  // 즐겨찾기 키 기준 (unique한 ID로 넣었음)
        boolean isFavorite = prefs.getBoolean(key, false);
        holder.ratingBar.setRating(isFavorite ? 1.0f : 0.0f);

        holder.ratingBar.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                RatingBar ratingBar = (RatingBar) v;
                if (ratingBar.getRating() > 0) {
                    ratingBar.setRating(0);
                    // 여기서 캐시에서도 제거
                    prefs.edit().remove(key).apply();
                } else {
                    ratingBar.setRating(1);
                    prefs.edit().putBoolean(key, true).apply();
                }
            }
            return true; // 터치 이벤트 소비
        });
    }

    @Override
    public int getItemCount() {
        return bikeRackList.size();
    }
    public void addBikeRack(BikeRack bikeRack){
        bikeRackList.add(bikeRack);
        this.notifyDataSetChanged();
    }

    public void clearBikeInfos(){
        bikeRackList.clear();
    }
}
