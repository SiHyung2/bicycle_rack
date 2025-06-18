package com.example.myapplication.ui;

import android.os.Bundle;
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
import com.example.myapplication.ui.BikeComponent.BikeRack;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheet extends BottomSheetDialogFragment {
    BikeRack[] bikerack;
    TextView textView;
    public BottomSheet(BikeRack[] bikeRack){
        this.bikerack = bikeRack;
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
        if(bikerack == null){
            textView.setText("검색 결과가 없습니다");
        }else {
            for (int a = 0; a < bikerack.length; a++) {
                bikeAdapter.addBikeRack(bikerack[a]);
            }
            textView.setText("검색결과 " + bikerack.length + "건");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container,false);
        return view;
    }

}

