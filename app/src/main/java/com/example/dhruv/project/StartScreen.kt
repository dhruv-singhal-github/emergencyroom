package com.example.dhruv.project

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import android.support.constraint.ConstraintLayout
import android.util.Log


class StartScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_screen)
       try {
           val j = Intent(Intent(this, MainActivity::class.java))
           val handler=Handler()
           val task = Runnable { startActivity(j) }


            handler.postDelayed(task,4000)
       }catch (e:NullPointerException)
       {
           Log.d("ff","null point")
       }


    }
}
