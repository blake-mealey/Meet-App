package com.example.aidin.meetapp;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.List;

public class MainPage extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText addressInput;
    private Button addressGo;

    private MeetUp meetUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        addressInput = (EditText) findViewById(R.id.addressInput);
        addressGo = (Button) findViewById(R.id.addressGo);

        addressGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = addressInput.getText().toString();
                LatLng position = getLocationFromAddress(address);
                if (position != null) {
                    meetUp.addLocation(position);
                    recalculateMeetupMarker();
                }
            }
        });

        meetUp = new MeetUp();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        recalculateMeetupMarker();

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    public void recalculateMeetupMarker() {
        LatLng position = meetUp.getMeetUpLocation();
        if(position == null) return;
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(position).title("Meet Up Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    public LatLng getLocationFromAddress(String strAddress){
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng position = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address == null) return null;

            Address location = address.get(0);
            position = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return position;
    }

    public MeetUp getMeetUp() {
        return meetUp;
    }
}
