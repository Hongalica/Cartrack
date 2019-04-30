package com.chtan.cartrack.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.chtan.cartrack.R
import com.chtan.cartrack.database.DatabaseHandler
import com.chtan.cartrack.database.UserModelClass


class SignUpActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val username_edittext = findViewById<EditText>(R.id.signup_user_name_input)
        username_edittext.onFocusChangeListener = View.OnFocusChangeListener{view, focus ->
            if(!focus)
            {
                if(username_edittext.text.toString().trim().length < 3)
                {
                    username_edittext.error = "Please enter min 3 characters"
                }
                else
                {
                    username_edittext.error = null
                }
            }

        }

        val password_edittext = findViewById<EditText>(R.id.signup_password_input)
        password_edittext.onFocusChangeListener = View.OnFocusChangeListener{view, focus ->
            if(!focus)
            {
                if(password_edittext.text.toString().trim().length < 8)
                {
                    password_edittext.error = "Please enter min. 8 characters"
                }
                else
                {
                    password_edittext.error = null
                }
            }

        }
    }

    fun submitButtonClick(view: View){
        val user_name = findViewById<EditText>(R.id.signup_user_name_input).text
        val password = findViewById<EditText>(R.id.signup_password_input).text
        val confirm_password = findViewById<EditText>(R.id.confirm_password_input).text
        val country = findViewById<Spinner>(R.id.signup_country_list)

        val user_name_length_check = (user_name.trim().length < 3)
        val user_name_check = DatabaseHandler(this).checkUsername(user_name.toString())
        val password_length_check = password.trim().length<8
        val password_confirm_check = password.toString().trim().equals(confirm_password.toString().trim())
        if(user_name_length_check
            ||password_length_check
            || !password_confirm_check
            || !user_name_check
        )
        {
            if (!user_name_check)
            {
                Toast.makeText(applicationContext,"Username Used", Toast.LENGTH_LONG).show()
            }
            else
            {
                //Signup failed reset all field
                Toast.makeText(applicationContext,"Sign Up Failed", Toast.LENGTH_LONG).show()
            }
            user_name.clear()
            password.clear()
            confirm_password.clear()
            country.setSelection(0)
        }
        else
        {
            val databaseHandler: DatabaseHandler =
                DatabaseHandler(this)
            val status = databaseHandler.addUser(
                UserModelClass(
                    user_name.toString(),
                    password.toString(),
                    country.selectedItem.toString()
                )
            )
            if(status > -1)
            {
                Toast.makeText(applicationContext,"Sign Up Successful", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(applicationContext,"database failure", Toast.LENGTH_LONG).show()
            }

        }
    }
}
