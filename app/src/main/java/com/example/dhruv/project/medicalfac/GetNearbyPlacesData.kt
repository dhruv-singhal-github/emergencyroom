package com.example.dhruv.project.medicalfac

import android.os.AsyncTask
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

public  class GetNearbyPlacesData: AsyncTask<Any, String, String>() {


    lateinit var googleplacesdata: String
    lateinit var mMap: GoogleMap
    lateinit var url: String


    override fun doInBackground(vararg params: Any?): String {

        try {


            mMap = params[0] as GoogleMap
            url = params[0] as String
            val downloadUrl = DownloadUrl()

            googleplacesdata = downloadUrl.readurl(url)


        } catch (e: IOException) {
            e.printStackTrace()


        }
        return googleplacesdata

    }



    override fun onPostExecute(result: String) {


        var nearbyplacelist:List<HashMap<String,String>>

        val parser=dataparser()

        nearbyplacelist= parser.parse(result)
        shownearbyplace(nearbyplacelist)







    }






    private fun shownearbyplace(nearbyplacelist:List<HashMap<String,String>>)
    {
        for(i in 0..nearbyplacelist.size-1)
        {
            val markerOptions=MarkerOptions()
            val map:HashMap<String,String> = nearbyplacelist.get(i)
             var placename=map.get("place_name")
            var vicinity=map.get("vicinity")
            var lat:Double = (map.get("lat"))!!.toDouble()
            var lng:Double = (map.get("lng"))!!.toDouble()

            val latlng=LatLng(lat,lng)
            markerOptions.position(latlng)
            markerOptions.title(placename+ ":"+ vicinity)
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
            mMap.addMarker(markerOptions)

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(20f))

        }









        }






    }
