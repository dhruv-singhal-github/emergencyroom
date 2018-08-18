package com.example.dhruv.project

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.dhruv.project.doctors.doctormain
import com.example.dhruv.project.emerge.emergencyroom
import com.example.dhruv.project.medicalfac.MapsActivity
import com.example.dhruv.project.medicalfac.menu
import com.example.dhruv.project.medicine.pharmacy
import kotlinx.android.synthetic.main.activity_home_page2.*

class HomePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page2)


        btnhosp.setOnClickListener {

            val k: Intent= Intent(this,MapsActivity::class.java)
            startActivity(k)


        }

        doctor.setOnClickListener {

            val k: Intent= Intent(this,doctormain::class.java)
            startActivity(k)


        }

       btnmedicine.setOnClickListener {

          val k: Intent= Intent(this,pharmacy::class.java)
            startActivity(k)


       }

       btnambu.setOnClickListener {

            val k: Intent= Intent(this,emergencyroom::class.java)
           startActivity(k)


        }



    }
}
