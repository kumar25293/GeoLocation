package com.demo.geolocation

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var btn_getlocation:Button

    lateinit var tv_location:TextView

    lateinit var ev_address:EditText

    var address:String = "22/187,Puthumanai Fifth Street, Melapuliyur, Tenkasi, Tamil Nadu 627814, India"

     val TAG :String = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_getlocation = findViewById(R.id.btn_getlocation)

        tv_location = findViewById(R.id.tv_location)

        ev_address = findViewById(R.id.ev_address)

        ev_address.setText(address)

        btn_getlocation.setOnClickListener {

         CoroutineScope(Dispatchers.Main).let {

             getLocationFromAddress()
         }
        }

    }

    fun getLocationFromAddress(){

        val geocoder = Geocoder(this, Locale.getDefault())
        var result: String? = null
        try {
            val addressList: List<*>? = geocoder.getFromLocationName(address, 1)
            var latitude:Double =0.0
            var longitude:Double =0.0
            if (addressList != null && addressList.size > 0) {
                val address: Address = addressList[0] as Address
                latitude =address.getLatitude()
                longitude =address.getLongitude()

                val sb = StringBuilder()
                sb.append(address.getLatitude()).append("\n")
                sb.append(address.getLongitude()).append("\n")
                result = sb.toString()
            }

            println("Location result === ${result.toString()}")

            getAddressFromLocation(latitude,longitude)

        } catch (e: IOException) {
            Log.e(TAG, "Unable to connect to Geocoder", e)
        }
    }

    fun calculateDistance(){
        val source = Location.CREATOR.createFromParcel()
//        val distanceValue = SphericalUtil.computeDistanceBetween(mSource, mDestination)
    }

    fun getAddressFromLocation( latitude:Double, longitude:Double){

        val geocoder = Geocoder(this, Locale.getDefault())
        var result: String? = null



        try{
            val addresses = geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)

            println("Address size ${addresses.size}")

                val fetchedAddress = addresses[0]
                val strAddress = StringBuilder()
            if(fetchedAddress.maxAddressLineIndex>0) {
                for (i in 0 until fetchedAddress.maxAddressLineIndex) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append(" ")
                }
            }
            else{
                strAddress.append(fetchedAddress.getAddressLine(0)).append(" ")
            }

                println("Address From Location === ${strAddress.toString()}")
        }

        catch (e: IOException) {
            Log.e(TAG, "Get address Exception", e)
        }
    }


}