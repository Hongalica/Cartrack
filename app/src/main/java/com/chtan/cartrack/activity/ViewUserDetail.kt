package com.chtan.cartrack.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.chtan.cartrack.R
import com.chtan.cartrack.network.JsonPlaceHolderApi
import com.chtan.cartrack.network.RetroDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ViewUserDetail: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user_detail)
        queryMasterDetail()
    }

    fun setTheText(theList:List<RetroDetail>){
        Log.d("Iam herererererere", "muahahahhahaha" + this)
        var theText = findViewById<TextView>(R.id.text_view_result)
       theList.forEach { retroDetails: RetroDetail ->
            var content = ""
            content += "ID: " + retroDetails.id + "\n"
            content += "User ID: " + retroDetails.name + "\n"
            content += "Title: " + retroDetails.username + "\n"
            content += "Text: " + retroDetails.email + "\n"
            content += "Street: " + retroDetails.address?.street + "\n"
            content += "Suite: " + retroDetails.address?.suite + "\n"
            content += "City: " + retroDetails.address?.city + "\n"
            content += "Zipcode: " + retroDetails.address?.zipcode + "\n"
            content += "Latitude: " + retroDetails.address?.geo?.lat + "\n"
            content += "Longitude: " + retroDetails.address?.geo?.lng + "\n"
            content += "Phone: " + retroDetails.phone + "\n"
            content += "Website: " + retroDetails.website + "\n"
            content += "Company name:" +retroDetails.company?.name + "\n"
            content += "Company Catchphrase:" +retroDetails.company?.catchPhrase + "\n"
            content += "Company Business:" +retroDetails.company?.bs + "\n\n"

            theText.append(content)
        }

    }

    fun queryMasterDetail() {
        var masterDetailList = listOf<RetroDetail>()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getUserDetail()
        //val retroDetail = call.execute().body()

        // return retroDetail!!

        call.enqueue(object: Callback<List<RetroDetail>> {
            override fun onResponse(call: Call<List<RetroDetail>>, response: Response<List<RetroDetail>>){

                if (!response.isSuccessful) {
                    //  result.setText("Code: " + response.code())
                    return
                }

                val retroDetail = response.body()
                masterDetailList = retroDetail!!
                Log.d("just want to check query333", ""+masterDetailList)
                setTheText(masterDetailList)

            }

            override fun onFailure(call: Call<List<RetroDetail>>, t: Throwable) {
                // result.text=t.message
            }
        })

    }
}



