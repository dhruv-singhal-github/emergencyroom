package com.example.dhruv.project.medicalfac

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

public class dataparser




{

    private fun getplace(googleplacejson: JSONObject):HashMap<String,String>
    {


       val  googleplacesmap=HashMap<String,String>()



        var placename:String="-NA-"
        var vicinity:String="-NA-"
        var latitiude:String=""
        var  longitude:String=""
        var reference:String=""
try {
    if (!googleplacejson.isNull("name")) {

        placename = googleplacejson.getString("name")

    }

    if (!googleplacejson.isNull("vicinity")) {

        vicinity = googleplacejson.getString("vicinity")


    }

    latitiude = googleplacejson.getJSONObject("geometry").getJSONObject("location").getString("lat")
    longitude = googleplacejson.getJSONObject("geometry").getJSONObject("location").getString("lng")


    reference = googleplacejson.getString("reference")

    googleplacesmap.put("place_name", placename)
    googleplacesmap.put("vicinity", vicinity)
    googleplacesmap.put("lat", latitiude)
    googleplacesmap.put("lng", longitude)
    googleplacesmap.put("reference", reference)
}catch(e:JSONException)
{
e.printStackTrace()
}

        return googleplacesmap

    }



    private fun getplaces(jsonArray: JSONArray):List<HashMap<String,String>> {
        var count: Int = jsonArray.length()
        var placeslist = ArrayList<HashMap<String, String>>()
        var placemap: HashMap<String, String>? = null

            for (i in 0..count - 1) {
                try{
                placemap = getplace(jsonArray.get(i) as JSONObject)
                placeslist.add(placemap)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return placeslist
    }

          fun parse(jsondata:String):List<HashMap<String,String>>
        {
            var jsonObject:JSONObject
            var jsonArray:JSONArray?=null

            try {


                 jsonObject = JSONObject(jsondata)
                 jsonArray = jsonObject.getJSONArray("results")
            }catch (e:JSONException)
            {
                e.printStackTrace()
            }
            return getplaces(jsonArray!!)








        }




    }




