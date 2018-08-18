package com.example.dhruv.project.medicalfac

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

public class DownloadUrl
{
    @Throws(IOException::class)
    public fun  readurl(myurl:String):String
    {

        lateinit var data:String
        lateinit var inputStream: InputStream
        lateinit var httpurlconnection:HttpURLConnection
        try {


            val url = URL(myurl)


            httpurlconnection = url.openConnection() as HttpURLConnection

            httpurlconnection.connect()

            inputStream = httpurlconnection.inputStream
            val bufferedreader = BufferedReader(InputStreamReader(inputStream))
            lateinit var sb: StringBuilder

            var line: String=""

            while (bufferedreader != null) {
                sb.append(line)
                line = bufferedreader.readLine()


            }

            data = sb.toString()
            bufferedreader.close()
        }
        catch (e:MalformedURLException)
        {
            e.printStackTrace()
        }
        catch (e:IOException)
        {e.printStackTrace()}

        finally {
            inputStream.close()

            httpurlconnection.disconnect()

        }
        Log.d("tag",data)
        return data


    }















}