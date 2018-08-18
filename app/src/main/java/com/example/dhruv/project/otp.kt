package com.example.dhruv.project

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import com.example.dhruv.project.tables.infoTable
import com.example.dhruv.project.tables.insertinfo
import com.example.dhruv.project.tables.mydbhelper
import kotlinx.android.synthetic.main.activity_otp.*
import java.util.*

class otp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        lateinit var tel:String

        intent?.let {

            tel = it.getStringExtra("mobile")

        }



            if (ContextCompat.checkSelfPermission(this, "Manifest.permission.SEND_SMS") != PackageManager.PERMISSION_GRANTED)

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 1)









        var r = Random()
        var num: Int = 0
        var flag = 0





            btngenotp.setOnClickListener {

                flag++
                num = 10000 + r.nextInt(99999)
               Log.d("otp",num.toString())
                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage( "$tel", null, "Your 5-digit verification code is"+num, null, null)


                Toast.makeText(this, "OTP sent to your mobile", Toast.LENGTH_LONG).show()

            }

            btnverify.setOnClickListener {
                if (flag > 0) {





                    if (etotp.text.toString() == num.toString()) {

                        val verify:String="UPDATE info SET VERIFIED=1 WHERE MOBILE="+tel
                        val dm=mydbhelper(this).writableDatabase
                        dm?.execSQL(verify)



                        val ini=Intent(this,MainActivity::class.java)

                        startActivity(ini)







                    } else Toast.makeText(this, "wrong otp entered", Toast.LENGTH_SHORT).show()

                }
            }


        }


    }
