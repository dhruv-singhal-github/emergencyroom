package com.example.dhruv.project.emerge

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.Toast
import com.example.dhruv.project.MainActivity
import com.example.dhruv.project.R


class emergencyroom : AppCompatActivity() {

    lateinit var inte: Intent
    val handler = Handler()
    val task = Runnable {
        Toast.makeText(this,"calling ambulance",Toast.LENGTH_SHORT).show()
        startActivity(inte) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergencyroom)
        try {


            inte = Intent(Intent.ACTION_CALL)
            var somethin = "102"
            inte.data = Uri.parse("tel:" + somethin)

            val perm = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
            if (perm == PackageManager.PERMISSION_GRANTED)


                handler.postDelayed(task, 2000)
            else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 121)


            }


        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == 121) {
            Log.d("results", grantResults.toList().toString())

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                handler.postDelayed(task, 2000)
            else {


                Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT)
                        .show()


                inte.action = Intent.ACTION_DIAL
                startActivity(inte)


            }
        }
    }
}