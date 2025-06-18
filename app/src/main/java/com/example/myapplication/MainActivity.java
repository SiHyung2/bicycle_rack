package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.ui.BikeComponent.BikeRack;
import com.example.myapplication.ui.BikeInfoBottomSheet;
import com.example.myapplication.ui.BottomSheet;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.clustering.Cluster;
import com.naver.maps.map.clustering.Clusterer;
import com.naver.maps.map.clustering.Leaf;
import com.naver.maps.map.clustering.Node;
import com.naver.maps.map.overlay.Marker;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private ActivityMainBinding binding; // 뷰바인딩 사용
    private Map<BikeRack, Object> bikeRacks;
    private NaverMap naverMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 검색 리스너 설정
        setupSearchListener();

        initNaverMap(savedInstanceState);
    }

    // 검색 리스너 설정 메서드
    private void setupSearchListener() {
        // EditText를 사용하는 경우
        if (binding.searchEditText != null) {
            binding.searchEditText.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    openSheet();
                    return true;
                }
                return false;
            });
        }
    }

    private void initNaverMap(Bundle savedInstanceState){
        NaverMapSdk.getInstance(this).setClient(new NaverMapSdk.NcpKeyClient("fl1m8z91vz"));

        // 뷰바인딩 사용 (수정)
        binding.mapFragment.onCreate(savedInstanceState);
        binding.mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        // naverMap 멤버 변수에 할당 (추가)
        this.naverMap = naverMap;

        // 데이터 가져오기
        try {
            bikeRacks = DataLoader.readExcel(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }



        // 클러스터러 세부 설정
        // 리프 마커 설정
        Clusterer.Builder<BikeRack> bikeRackBuilder = new Clusterer.Builder<BikeRack>();
        bikeRackBuilder.leafMarkerUpdater((info, marker) -> {
            marker.setCaptionText(null);


            // 클릭 이벤트 등록
            marker.setOnClickListener(overlay -> {
                // BikeRack 찾기: marker의 위치(position)를 기준으로
                LatLng position = marker.getPosition();
                BikeRack matched = null;
                for (BikeRack br : bikeRacks.keySet()) {
                    if (br.getPosition().equals(position)) {
                        matched = br;
                        break;
                    }
                }
                // BikeRack을 tag에 저장
                if (matched != null) {
                    marker.setTag(matched);
                }

                Object tag = marker.getTag();  // 마커에서 데이터 꺼냄
                Log.d("MarkerClick", "Tag class: " + (tag != null ? tag.getClass().getSimpleName() : "null"));
                Log.d("MatchCheck", "마커 위치: " + position + ", 거치대 위치: " + marker.getPosition());
                if (tag instanceof BikeRack) {
                    BikeRack bikeRack = (BikeRack) tag;

                    // 실제 UI 띄우는 부분
                    BikeInfoBottomSheet bikeInfoBottomSheet = new BikeInfoBottomSheet(bikeRack);
                    bikeInfoBottomSheet.show(getSupportFragmentManager(), bikeInfoBottomSheet.getTag());
                }
                return true;
            });
        });

        /*
        // 클러스터 마커 설정
        bikeRackBuilder.clusterMarkerUpdater((info, marker) -> {
            marker.setOnClickListener(overlay -> {
                return true;
            });
        });

         */

        //Clusterer.Builder<BikeRack> bikeRackBuilder = new Clusterer.Builder<BikeRack>();


        // 클러스터러 생성
        Clusterer<BikeRack> clusterer = bikeRackBuilder.build();

        // 클러스터러에 데이터 & 지도 추가
        clusterer.addAll(bikeRacks);
        clusterer.setMap(naverMap);
        
        // 학교 위치
        LatLng initialPosition = new LatLng(35.859602, 128.487495);

        // 카메라 위치 업데이트
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
        naverMap.moveCamera(cameraUpdate);

    }

    private void openSheet(){
        String query = binding.searchEditText.getText().toString().trim();
        boolean filterAirPumpOnly = binding.airPumpCheckBox.isChecked();  // 체크박스 상태 가져오기

        if (query.isEmpty()) {
            BottomSheet bottomSheet = new BottomSheet(new BikeRack[]{
                    new BikeRack(-1, "검색어를 입력하세요", "", "", "", "", new LatLng(0,0), 0),
                    new BikeRack(-2, "예: 경북대, 대구역", "", "", "", "", new LatLng(0,0), 0)
            });
            bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
            return;
        }

        List<BikeRack> searchResults = new ArrayList<>();
        BikeRack foundBikeRack = null;

        for (BikeRack bikeRack : bikeRacks.keySet()) {
            String name = bikeRack.getName();
            String roadAddress = bikeRack.getRoadAddress();
            String isExistAirPump = bikeRack.getIs_exist_air_pump();  // Y 또는 N

            boolean matchesQuery = name.toLowerCase().contains(query.toLowerCase()) ||
                    roadAddress.toLowerCase().contains(query.toLowerCase());
            boolean matchesPump = !filterAirPumpOnly || "Y".equalsIgnoreCase(isExistAirPump);

            if (matchesQuery && matchesPump) {
                searchResults.add(bikeRack);
                if (foundBikeRack == null) {
                    foundBikeRack = bikeRack;
                }
            }
        }

        if (searchResults.isEmpty()) {
            Toast.makeText(this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        BikeRack[] resultArray = searchResults.toArray(new BikeRack[0]);
        BottomSheet bottomSheet = new BottomSheet(resultArray);
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());

        if (foundBikeRack != null) {
            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(foundBikeRack.getPosition());
            naverMap.moveCamera(cameraUpdate);
        }
    }


    @Override protected void onStart() {
        super.onStart();
        binding.mapFragment.onStart();
    }

    @Override protected void onResume() {
        super.onResume();
        binding.mapFragment.onResume();
    }

    @Override protected void onPause() {
        binding.mapFragment.onPause();
        super.onPause();
    }

    @Override protected void onStop() {
        binding.mapFragment.onStop();
        super.onStop();
    }

    @Override protected void onDestroy() {
        binding.mapFragment.onDestroy();
        super.onDestroy();
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapFragment.onSaveInstanceState(outState);
    }

    @Override public void onLowMemory() {
        super.onLowMemory();
        binding.mapFragment.onLowMemory();
    }
}