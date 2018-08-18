package com.example.dhruv.project

import android.content.Intent
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dhruv.project.tables.mydbhelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btnSign.setOnClickListener {


            val g = Intent(this, signup::class.java)
            startActivity(g)
        }


        val db = mydbhelper(this).readableDatabase

        val srt: String = "SELECT * FROM info WHERE USERNAME =? AND PASSWORD=? AND VERIFIED =1"
        val srt2: String = "SELECT * FROM info WHERE USERNAME =? AND PASSWORD=? AND VERIFIED =0"
        var cursor: Cursor? = null
        var cursor1: Cursor? = null
        cursor = db?.rawQuery(srt, arrayOf(etusername.text.toString(), etpassword.text.toString()))
        cursor1 = db?.rawQuery(srt2, arrayOf(etusername.text.toString(), etpassword.text.toString()))

        btnLog.setOnClickListener {
            cursor = db?.rawQuery(srt, arrayOf(etusername.text.toString(), etpassword.text.toString()))
            cursor1 = db?.rawQuery(srt2, arrayOf(etusername.text.toString(), etpassword.text.toString()))


            if (cursor?.moveToNext()==true) {
                val j = Intent(this, HomePage::class.java)
                startActivity(j)


            } else if (cursor?.moveToNext() == true && cursor1?.moveToNext() != false) {
                Toast.makeText(this, "account not verified", Toast.LENGTH_SHORT).show()

            } else Toast.makeText(this, "wrong username or password", Toast.LENGTH_SHORT).show()


        }


    }

}