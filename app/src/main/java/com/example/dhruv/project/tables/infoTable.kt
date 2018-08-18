package com.example.dhruv.project.tables

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.dhruv.project.InfoClass
import com.example.dhruv.project.tables.infoTable.tablename


object infoTable {
 val tablename = "info"

 object columns {


  val id = "ID"
  val username = "USERNAME"
  val password = "PASSWORD"
  val email = "EMAIL"
  val address="ADDRESS"
  val mobile = "MOBILE"
  val verified="VERIFIED"

 }


 val cmd_crt_table = """
     CREATE TABLE IF NOT EXISTS $tablename
     (
     ${infoTable.columns.id} INTEGER AUTO INCREMENT,
     ${infoTable.columns.username} TEXT PRIMARY KEY,
     ${infoTable.columns.password} TEXT,
      ${infoTable.columns.email} TEXT,
      ${infoTable.columns.address} TEXT,

       ${infoTable.columns.mobile} TEXT,
       ${infoTable.columns.verified} INTEGER



     );



 """.trimIndent()


}



fun insertinfo (db: SQLiteDatabase?,infoclass:InfoClass)
{

Log.d("TAG","${infoclass.email}")
 val row = ContentValues()
 row.put(infoTable.columns.username,infoclass.username)
 row.put(infoTable.columns.password,infoclass.password)
 row.put(infoTable.columns.email,infoclass.email)
 row.put(infoTable.columns.address,infoclass.address)

 row.put(infoTable.columns.mobile,infoclass.mobile)

 row.put (infoTable.columns.verified,0)
 db?.insert("info",null,row)

}


class mydbhelper(context:Context):SQLiteOpenHelper(context,"info",null,1)
{
 override fun onCreate(db: SQLiteDatabase?) {
  db?.execSQL(infoTable.cmd_crt_table)

 }

 override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

 }

}