package com.example.dhruv.project

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dhruv.project.tables.insertinfo
import com.example.dhruv.project.tables.mydbhelper
import kotlinx.android.synthetic.main.activity_signup.*

class signup : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        val db= mydbhelper(this).writableDatabase


       {
           Toast.makeText(this,"values not inserted",Toast.LENGTH_SHORT)

       }




        btnRegister.setOnClickListener {
            val info1=InfoClass(etUsername.text.toString(),etPassword.text.toString(),etEmail.text.toString(),etAddress.text.toString(),
                    etMobile.text.toString())



               val i = Intent(this, otp::class.java)
            insertinfo(db, info1)



            i.putExtra("mobile", info1.mobile)
            i.putExtra("mobil", info1.username)
            i.putExtra("mobi", info1.password)
            i.putExtra("mob", info1.email)
            i.putExtra("mo", info1.address)

               startActivity(i)


           }


    }
}
