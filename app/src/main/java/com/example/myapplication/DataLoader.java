package com.example.myapplication;

import android.content.Context;
import android.util.Log;

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

    public static Map<BikeRack, Object> readExcel(Context context) throws IOException, CsvException {
        Map<BikeRack, Object> bikeData = new HashMap<>();


            InputStream is = context.getResources().openRawResource(R.raw.data);    // res/raw/item.csv 파일을 불러오기 위해 해당 코드 작성
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line = "";

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");    // , 기준으로 분리
                try {
                if (tokens.length >= 6) {
                    bikeData.put(new BikeRack(
                            Integer.parseInt(tokens[0]),
                            new LatLng(Double.parseDouble(tokens[4]), Double.parseDouble(tokens[5]))
                    ), tokens[1]);
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