package com.chtan.cartrack.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.chtan.cartrack.network.JsonPlaceHolderApi
import com.chtan.cartrack.R
import com.chtan.cartrack.network.RetroDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Response


class ViewUserDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user_detail)

        val result = findViewById<TextView>(R.id.text_view_result)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getUserDetail()

        call.enqueue(object : Callback<List<RetroDetail>> {
            override fun onResponse(call: Call<List<RetroDetail>>, response: Response<List<RetroDetail>>) {

                if (!response.isSuccessful) {
                    result.setText("Code: " + response.code())
                    return
                }

                val retroDetail = response.body()

                retroDetail?.forEach { retroDetails: RetroDetail ->
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

                    result.append(content)
                }
            }

            override fun onFailure(call: Call<List<RetroDetail>>, t: Throwable) {
                result.text=t.message
            }
        })


    }
}
