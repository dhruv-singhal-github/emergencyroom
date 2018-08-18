package com.example.dhruv.project.doctors

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.util.Log
import com.example.dhruv.project.MainActivity
import com.example.dhruv.project.R

import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_doctormain.*
import kotlinx.android.synthetic.main.doctorlayout.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class doctormain : AppCompatActivity() {
    companion object {
        val doctors = ArrayList<doctor>()

    }

    var url = ""
    lateinit var locMan: LocationManager
    lateinit var locLis: LocationListener
    var latitude: Double? = null
    var longitude: Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctormain)





        locLis = object : LocationListener {
            override fun onLocationChanged(location: Location?) {


                latitude = location?.latitude
                longitude = location?.longitude
                Log.d("loc",latitude.toString()+" "+longitude.toString())


            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {


            }

            override fun onProviderDisabled(provider: String?) {
            }

        }

        val coarsePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val finePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
if(coarsePerm!=PackageManager.PERMISSION_GRANTED&&finePerm!=PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(
            this,
            arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            ),
            212
    )
}

       // var mLocationManager =  getSystemService(LOCATION_SERVICE) as  LocationManager;

      //  mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,LOCATION_R,
          //      LOCATION_REFRESH_DISTANCE, locLis)
        latitude=37.773
        longitude=-122.413
        url = "https://api.betterdoctor.com/2016-03-01/practices?location=" + latitude.toString() + "%2C" + longitude.toString() + "%2C100&user_location=" + latitude.toString() + "%2C" + longitude.toString() + "&sort=distance-asc&skip=0&limit=3&user_key=7ad23472d2d0b2c6205230dbc3383359"
        Log.d("url", url)
        Downloaddata().execute(url)
        rv.layoutManager = GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)
        rv.adapter = doctorAdapter(doctors)

    }
        class Downloaddata(): AsyncTask<String, Void, String?>()
        {
            override fun doInBackground(vararg params: String?): String? {
                val urlstring = params[0]
                var result: String?=null

                try {

                    Log.d("back","do in background start")
                    val url = URL(urlstring)
                    val httpURLConnection = url.openConnection() as HttpURLConnection
                    val inputStream = httpURLConnection.getInputStream()
                    val inputstreamreader = InputStreamReader(inputStream)
                    val bufferedreader = BufferedReader(inputstreamreader)
                    val sb = StringBuilder()
                    var line: String? = ""
                    while (bufferedreader != null) {
                        Log.d("dh",sb.toString())
                        sb.append(line)
                        line = bufferedreader.readLine()


                    }


                    result = sb.toString()
                    Log.d("dh","gfhg")
                    return result
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                    Log.e("ma","mal",e)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.e("ha","io",e)

                }
                return "data not found"

            }


            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                  Log.d("dta",result)
                val ob = JSONObject(result)
                val obs = ob.getJSONArray("data")
                for (i in 0..obs.length() - 1) {

                    var o = obs.getJSONObject(i)
                    var address = o.getJSONObject("visit_address")
                    var city = address.getString("city")
                    var statelong = address.getString("state_long")
                    var street = address.getString("street")
                    var phone = o.getJSONArray("phones")

                    var nos = phone.getJSONObject(0)
                    var no = nos.getString("number")
                    var docs = o.getJSONArray("doctors")
                    for (j in 0..docs.length() - 1) {
                        var doc = docs.getJSONObject(j)
                        var profile = doc.getJSONObject("profile")

                        var firstname = profile.getString("first_name")
                        var lastname = profile.getString("last_name")
                        var title = profile.getString("title")
                        var image = profile.getString("image_url")
                        var bio = profile.getString("bio")
                        var specialities = doc.getJSONArray("specialities")
                        var speciality = specialities.getJSONObject(0)
                        var uid = speciality.getString("uid")
                        var description = speciality.getString("description")
                        doctors.add(doctor(firstname, lastname, title, uid, description, bio, statelong
                                , city, street, no, image))

                    }
                }

            }



        }






    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)

    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 212) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locMan = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                locMan.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        1000,
                        10f,
                        locLis
                )
            }
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                locMan = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                locMan.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        1000,
                        10f,
                        locLis
                )
            }
        }
    }













    }














