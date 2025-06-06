package com.example.myapplication.ui.BikeComponent;

public class BikeInfo {
    String title; String adress; int availableBike;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getAvailableBike() {
        return availableBike;
    }

    public void setAvailableBike(int availableBike) {
        this.availableBike = availableBike;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BikeInfo(String title, String adress, int availableBike){
        this.title = title;
        this.adress = adress;
        this.availableBike = availableBike;

    }
}
