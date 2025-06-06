package com.example.myapplication.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.BikeComponent.BikeAdapter;
import com.example.myapplication.ui.BikeComponent.BikeInfo;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheet extends BottomSheetDialogFragment {
    BikeInfo[] bikeInfo;
    TextView textView;
    public BottomSheet(BikeInfo[] bikeInfo){
        this.bikeInfo = bikeInfo;
    }
    BikeAdapter bikeAdapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bikeAdapter = new BikeAdapter();

        RecyclerView recyclerView = view.findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(bikeAdapter);
        textView = view.findViewById(R.id.textView2);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(bikeInfo == null){
            textView.setText("검색 결과가 없습니다");
        }else {
            for (int a = 0; a < bikeInfo.length; a++) {
                bikeAdapter.addBikeInfo(bikeInfo[a]);
            }
            textView.setText("검색결과 " + bikeInfo.length + "건");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container,false);
        return view;
    }

}

