package com.example.dhruv.project.medicalfac

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.dhruv.project.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.common.ConnectionResult
import android.support.annotation.NonNull
import android.Manifest.permission
    import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.widget.Toast
import android.location.Geocoder
import android.location.Location
import android.widget.EditText
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.common.api.GoogleApiClient
import android.os.Build
import android.support.annotation.Nullable
import com.google.android.gms.maps.model.Marker


import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import com.google.android.gms.location.LocationListener


abstract class MapsActivity : FragmentActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private var mMap: GoogleMap? = null
    private var client: GoogleApiClient? = null
    private var locationRequest: LocationRequest?= null
    private var lastlocation: Location? = null
    private var currentLocationmMarker: Marker? = null
    internal var PROXIMITY_RADIUS = 10000
    internal var latitude: Double = 0.toDouble()
    internal var longitude: Double = 0.toDouble()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission()

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (client == null) {
                        bulidGoogleApiClient()
                    }
                    mMap!!.isMyLocationEnabled = true
                }
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient()
            mMap!!.isMyLocationEnabled = true
        }
    }


    @Synchronized
    protected fun bulidGoogleApiClient() {
        client = GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build()
        client!!.connect()

    }

     override fun onLocationChanged(location: Location) {

        latitude = location.getLatitude()
        longitude = location.getLongitude()
        lastlocation = location
        if (currentLocationmMarker != null) {
            currentLocationmMarker!!.remove()

        }
        Log.d("lat = ", "" + latitude)
        val latLng = LatLng(location.getLatitude(), location.getLongitude())
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title("Current Location")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
        currentLocationmMarker = mMap!!.addMarker(markerOptions)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mMap!!.animateCamera(CameraUpdateFactory.zoomBy(10f))

        if (client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(client,this as LocationListener)
        }
    }

    fun onClick(v: View) {
        val dataTransfer = arrayOfNulls<Any>(2)
        val getNearbyPlacesData = GetNearbyPlacesData()

        when (v.getId()) {



            R.id.btnhospitals -> {
                mMap!!.clear()
                val hospital = "hospital"
                var url = getUrl(latitude, longitude, hospital)
                dataTransfer[0] = mMap
                dataTransfer[1] = url

                getNearbyPlacesData.execute(dataTransfer)
                Toast.makeText(this@MapsActivity, "Showing Nearby Hospitals", Toast.LENGTH_SHORT).show()
            }


            R.id.btnlabs -> {
                mMap!!.clear()
                val school = "diagnostic lab"
               var  url = getUrl(latitude, longitude, school)
                dataTransfer[0] = mMap
                dataTransfer[1] = url

                getNearbyPlacesData.execute(dataTransfer)
                Toast.makeText(this@MapsActivity, "Showing Nearby Schools", Toast.LENGTH_SHORT).show()
            }
            R.id.btnpharmacies-> {
                mMap!!.clear()
                val resturant = "pharmacy"
                var url = getUrl(latitude, longitude, resturant)
                dataTransfer[0] = mMap
                dataTransfer[1] = url

                getNearbyPlacesData.execute(*dataTransfer)
                Toast.makeText(this@MapsActivity, "Showing Nearby Restaurants", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun getUrl(latitude: Double, longitude: Double, nearbyPlace: String): String {

        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?")
        googlePlaceUrl.append("location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=$PROXIMITY_RADIUS")
        googlePlaceUrl.append("&type=$nearbyPlace")
        googlePlaceUrl.append("&sensor=true")
        googlePlaceUrl.append("&key=" + "AIzaSyAnOApAUo4HFfFxYPI8isJq-BaCRq-HkZE")

        Log.d("MapsActivity", "url = " + googlePlaceUrl.toString())

        return googlePlaceUrl.toString()
    }

    override fun onConnected(@Nullable bundle: Bundle?) {

        locationRequest = LocationRequest()
        locationRequest!!.setInterval(100)
        locationRequest!!.setFastestInterval(1000)
        locationRequest!!.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this as LocationListener)
        }
    }


    fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_CODE)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_CODE)
            }
            return false

        } else
            return true
    }


    override fun onConnectionSuspended(i: Int) {}

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}

    companion object {
        val REQUEST_LOCATION_CODE = 99
    }
}


