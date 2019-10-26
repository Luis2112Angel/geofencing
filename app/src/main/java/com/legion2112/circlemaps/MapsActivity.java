package com.legion2112.circlemaps;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location miLocalizacion = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));
        double latitude = miLocalizacion.getLatitude();
        double longitud = miLocalizacion.getLongitude();
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(miLocalizacion));
        mMap.setMyLocationEnabled(true);
        Circle circleNear = googleMap.addCircle(new CircleOptions().center(new LatLng(19.309959, -99.177147)) .radius(10) .strokeColor(Color.BLUE) );
        Circle circleInside = googleMap.addCircle(new CircleOptions().center(new LatLng(19.309959, -99.177147)) .radius(25) .strokeColor(Color.RED) );
        float[] distance = new float[2];
        Location.distanceBetween(miLocalizacion.getLatitude(), miLocalizacion.getLongitude(), circleNear.getCenter().latitude, circleNear.getCenter().longitude, distance);
        if(distance[0] > circleNear.getRadius() ){
            Toast.makeText(getBaseContext(), "Esta fuera", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), "Esta dentro", Toast.LENGTH_LONG).show();
        }


    }
}
