package com.example.aidin.meetapp;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by aidin on 3/9/2016.
 */
public class MeetUp {
    private ArrayList<LatLng> locations;

    public MeetUp() {
        locations = new ArrayList<LatLng>();
    }

    public void addLocation(LatLng location) {
        locations.add(location);
    }

    public void clearMeetUpLocations() {
        locations.clear();
    }

    public LatLng getMeetUpLocation() {
        double count = (double)getMemberCount();
        if(count < 1) return null;
        double totalLat = 0;
        double totalLng = 0;
        Log.d("list: " , locations.toString());
        for(LatLng location : locations) {
            totalLng += ((location.longitude + 360) % 360);
            totalLat += location.latitude;
            Log.d("total lat: ", String.valueOf(totalLat));
        }
        double lat = (totalLat)/count;
        lat = ((lat + 180) % 360) - 180;
        double lng = (totalLng)/count;
        return new LatLng(lat, lng);
    }

    public int getMemberCount() {
        return locations.size();
    }
}
