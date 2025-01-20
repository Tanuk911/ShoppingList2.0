package com.example.activityonesqlite.constants;

import java.util.ArrayList;

public class AppConstants {

    public static ArrayList<String> getLocations(){
        ArrayList<String> locationsList = new ArrayList<>();
        locationsList.add("Keels");
        locationsList.add("Cargills");
        locationsList.add("Arpico");
        locationsList.add("Glomark");
        locationsList.add("SPAR");

        return locationsList;
    }

    public static ArrayList<String> getUnits(){
        ArrayList<String> unitsList = new ArrayList<>();
        unitsList.add("Kg");
        unitsList.add("L");
        unitsList.add("Packets");
        unitsList.add("No");

        return unitsList;
    }
}
