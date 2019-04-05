package com.chtan.cartrack.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import android.net.ConnectivityManager
import com.chtan.cartrack.R
import com.chtan.cartrack.database.DatabaseHandler
import com.chtan.cartrack.database.UserModelClass


const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun loginButtonClick(view: View){
        val username_edittext = findViewById<EditText>(R.id.signup_user_name_input).text
        val password_edittect = findViewById<EditText>(R.id.signup_password_input).text
        val country_spinner = findViewById<Spinner>(R.id.signup_country_list)

        val databaseHandler: DatabaseHandler =
            DatabaseHandler(this)
        val status =  databaseHandler.checkUserInfo(
            UserModelClass(
                username_edittext.toString(),
                password_edittect.toString(),
                country_spinner.selectedItem.toString()
            )
        )
        if(status)
        {
            Toast.makeText(applicationContext,"Login Successful", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ViewUserDetail::class.java)
            startActivity(intent)
        }
        else
        {
            Toast.makeText(applicationContext,"Login Failed", Toast.LENGTH_LONG).show()
            country_spinner.setSelection(0)
            username_edittext.clear()
            password_edittect.clear()
        }

    }

    fun signUpButtonClick(view: View)
    {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }
}
