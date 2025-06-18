package com.example.myapplication.ui.BikeComponent;

import androidx.annotation.NonNull;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.clustering.ClusteringKey;

public class BikeRack implements ClusteringKey {
    private final int id;
    private final String name;
    private final String roadAddress;
    private final String jibunAddress;

    private final String is_exist_air_pump;
    private final String air_pump_type;
    int availableBike;

    @NonNull
    private final LatLng position;

    public BikeRack(@NonNull int id,
                    @NonNull String name,
                    @NonNull String roadAddress,
                    @NonNull String jibunAddress,
                    @NonNull String is_exist_air_pump,
                    @NonNull String air_pump_type,
                    @NonNull LatLng position,
                    @NonNull int availableBike) {
        this.id = id;
        this.name = name;
        this.roadAddress = roadAddress;
        this.jibunAddress = jibunAddress;
        this.is_exist_air_pump = is_exist_air_pump;
        this.air_pump_type = air_pump_type;
        this.position = position;
        this.availableBike = availableBike;
    }

    // 기존 ClusteringKey 용 메서드: 마커 찍을 좌표
    @Override @NonNull
    public LatLng getPosition() {
        return position;
    }

    // 새로 추가한 getter들: 정보 꺼내기
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getRoadAddress() {
        return roadAddress;
    }
    public String getJibunAddress() {
        return jibunAddress;
    }
    public String getIs_exist_air_pump() {
        return is_exist_air_pump;
    }
    public String getAir_pump_type() { return air_pump_type;}
    public int getAvailableBike() { return availableBike;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BikeRack)) return false;
        BikeRack other = (BikeRack) o;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
