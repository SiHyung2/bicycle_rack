package com.example.myapplication;
import android.content.Context;
import android.util.Log;

import com.example.myapplication.ui.BikeComponent.BikeRack;
import com.naver.maps.geometry.LatLng;
import com.opencsv.exceptions.CsvException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DataLoader {
    public static Map<BikeRack, Object> readExcel(Context context)
            throws IOException, CsvException {
        Map<BikeRack, Object> bikeData = new HashMap<>();

        // res/raw/data.csv 파일 열기
        InputStream is = context.getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        String line;
        while ((line = reader.readLine()) != null) {
            // 콤마로 분리
            String[] tokens = line.split(",");
            try {
                if (tokens.length >= 6) {
                    int    id          = Integer.parseInt(tokens[0]);
                    String name        = tokens[1];  // 보관소명
                    String roadAddress = tokens[2];  // 도로명주소
                    String jibunAddress= tokens[3];  // 지번주소
                    String is_exist_air_pump = tokens[9];  // 공기주입기비치여부
                    String air_pump_type = tokens[10];  // 공기주입기유형
                    double lat         = Double.parseDouble(tokens[4]);
                    double lng         = Double.parseDouble(tokens[5]);
                    LatLng pos         = new LatLng(lat, lng);
                    int    ava         = Integer.parseInt((tokens[6]));

                    // BikeRack 객체 생성
                    BikeRack bikeRack = new BikeRack(
                            id,
                            name,
                            roadAddress,
                            jibunAddress,
                            is_exist_air_pump,
                            air_pump_type,
                            pos,
                            ava
                    );

                    // Map에 추가 (value는 null 또는 필요한 객체로 설정)
                    bikeData.put(bikeRack, null);

                    // 또는 추가 데이터가 있다면:
                    // bikeData.put(bikeRack, someAdditionalData);
                }
            } catch (NumberFormatException e) {
                Log.d("DataLoader", "숫자 파싱 실패: " + line);
            }
        }

        reader.close();
        is.close();
        return bikeData;
    }
}