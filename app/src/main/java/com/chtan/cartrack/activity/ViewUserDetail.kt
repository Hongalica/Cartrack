package com.chtan.cartrack.activity

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.chtan.cartrack.R
import com.chtan.cartrack.network.JsonPlaceHolderApi
import com.chtan.cartrack.network.RetroDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ViewUserDetail: FragmentActivity() {

    var masterDetailList = listOf<RetroDetail>()
    val manager = supportFragmentManager
    var masterDetailSelection:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user_detail)
        checkNetworkConnection(this)
    }

    fun queryMasterDetail() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)
        val call = jsonPlaceHolderApi.getUserDetail()

        call.enqueue(object: Callback<List<RetroDetail>> {
            override fun onResponse(call: Call<List<RetroDetail>>, response: Response<List<RetroDetail>>){
                if (!response.isSuccessful) {
                    Toast.makeText(applicationContext,("Error Code: " + response.code()), Toast.LENGTH_LONG).show()
                    return
                }
                val retroDetail = response.body()
                masterDetailList = retroDetail!!
                showMasterNameList()
            }
            override fun onFailure(call: Call<List<RetroDetail>>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun showMasterNameList() {
        val transaction = manager.beginTransaction()
        val fragment = NameList()
        transaction.replace(R.id.fragment_holder, fragment, "theNamelist")
        transaction.addToBackStack("")
        transaction.commit()
    }

    fun showMasterDetail() {
        val transaction = manager.beginTransaction()
        val fragment = MasterDetail()
        transaction.replace(R.id.fragment_holder, fragment, "theMasterDetail")
        transaction.addToBackStack("")
        transaction.commit()
    }

    fun checkNetworkConnection(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected)
        {
            queryMasterDetail()
        }
        else
        {
            val no_internet_dialog = AlertDialog.Builder(context)
            no_internet_dialog.setMessage("No internet connection")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Retry", DialogInterface.OnClickListener {
                        dialog, id ->checkNetworkConnection(context)
                })
                // negative button text and action
                .setNegativeButton("Exit", DialogInterface.OnClickListener {
                        dialog, id -> finish()
                })
            // create dialog box
            val alert = no_internet_dialog.create()
            // set title for alert dialog box
            alert.setTitle("Connection Error")
            // show alert dialog
            alert.show()
        }
    }

    override fun onBackPressed() {
        Log.d("let check it", ""+manager.backStackEntryCount)
        if (manager.backStackEntryCount >1) {
            manager.popBackStackImmediate();
        } else {
            finish()
        }
    }
}



