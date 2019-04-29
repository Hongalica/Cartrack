package com.chtan.cartrack.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chtan.cartrack.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class UserLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var latitude = 0.0
    private var longitude = 0.0
    private var username =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_location)
        latitude = intent.getDoubleExtra("Latitude", 0.0)
        longitude = intent.getDoubleExtra("Longitude", 0.0)
        username = intent.getStringExtra("Username")
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker in user location and move the camera
        val user_location = LatLng(latitude, longitude)
        val markerName= "$username's location"
        googleMap.addMarker(MarkerOptions().position(user_location).title(markerName))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user_location, 5f))
    }
}
