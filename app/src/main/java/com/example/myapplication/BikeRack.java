package com.example.myapplication;

import androidx.annotation.NonNull;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.clustering.ClusteringKey;

class BikeRack implements ClusteringKey {
    private int id;
    @NonNull
    private LatLng position;

    public BikeRack(int id, @NonNull LatLng position) {
        this.id = id;
        this.position = position;
    }

    @Override
    @NonNull
    public LatLng getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BikeRack bikeRack = (BikeRack)o;
        return id == bikeRack.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}
