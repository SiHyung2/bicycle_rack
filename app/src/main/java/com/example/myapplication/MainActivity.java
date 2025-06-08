package com.example.myapplication;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.ui.BikeComponent.BikeInfo;
import com.example.myapplication.ui.BottomSheet;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.clustering.Clusterer;
import com.naver.maps.map.clustering.ClusteringKey;
import com.naver.maps.map.overlay.Marker;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private SearchView searchEditText;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        searchEditText = findViewById(R.id.searchView2);

        searchEditText.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                openSheet();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        }));

        initNaverMap(savedInstanceState);
    }

    private void initNaverMap(Bundle savedInstanceState){
        NaverMapSdk.getInstance(this).setClient(new NaverMapSdk.NcpKeyClient("")); //클라이언트 id 넣으면 됨
        mapView = findViewById(R.id.map_fragment);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    private void openSheet(){
        BottomSheet bottomSheet = new BottomSheet(new BikeInfo[]{new BikeInfo("test1","test2",12),
                new BikeInfo("test3","test4",13)});
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
    }


    @Override protected void onStart() { super.onStart(); mapView.onStart(); }
    @Override protected void onResume() { super.onResume(); mapView.onResume(); }
    @Override protected void onPause() { mapView.onPause(); super.onPause(); }
    @Override protected void onStop() { mapView.onStop(); super.onStop(); }
    @Override protected void onDestroy() { mapView.onDestroy(); super.onDestroy(); }
    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        /*
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.5666102, 126.9783881)); // 서울시청 좌표
        marker.setMap(naverMap);

        marker.setOnClickListener(overlay -> {
            openSheet();
            return true;
        });
         */

        // 데이터 가져오기
        Map<BikeRack, Object> bikeRacks;
        try {
            bikeRacks = DataLoader.readExcel(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

        // 클러스터링 마커에 데이터 & 지도 추가
        Clusterer<BikeRack> clusterer = new Clusterer.Builder<BikeRack>().build();
        clusterer.addAll(bikeRacks);
        clusterer.setMap(naverMap);

        // 학교 위치
        LatLng initialPosition = new LatLng(35.859602, 128.487495);

        // 카메라 위치 업데이트
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
        naverMap.moveCamera(cameraUpdate);

    }
}