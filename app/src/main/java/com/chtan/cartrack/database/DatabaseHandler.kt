package com.chtan.cartrack.database

import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.chtan.cartrack.activity.MainActivity
import java.util.logging.Logger

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME,null,
    DATABASE_VERSION
) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "LoginInfoDatabase"
        private val TABLE_LOGIN = "LoginInfoTable"
        private val KEY_USERNAME = "username"
        private val KEY_PASSWORD = "password"
        private val KEY_COUNTRY = "country"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_LOGIN_TABLE = ("CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_USERNAME + " TEXT,"
                + KEY_PASSWORD + " TEXT,"
                + KEY_COUNTRY + " TEXT"  +")")
        db?.execSQL(CREATE_LOGIN_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN)
        onCreate(db)
    }

    //method to insert data
    fun addUser(user: UserModelClass):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_USERNAME, user.userName) // EmpModelClass Name
        contentValues.put(KEY_PASSWORD,user.userPassword ) // EmpModelClass Phone
        contentValues.put(KEY_COUNTRY,user.userCountry ) // EmpModelClass Phone
        // Inserting Row
        val success = db.insert(TABLE_LOGIN, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun checkUserInfo(user: UserModelClass):Boolean{
        val Log = Logger.getLogger(MainActivity::class.java.name)
        val selectQuery = "SELECT  * FROM $TABLE_LOGIN"
        var result = false
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return result
        }

        var userName: String
        var userPassword: String
        var userCountry: String
        if(cursor.moveToFirst())
        {
            do {
                userName = cursor.getString(cursor.getColumnIndex(KEY_USERNAME))
                userPassword = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))
                userCountry = cursor.getString(cursor.getColumnIndex(KEY_COUNTRY))
                if(userName.equals(user.userName) and userPassword.equals(user.userPassword) and userCountry.equals(user.userCountry))
                {
                    result = true
                    break
                }

            }while(cursor.moveToNext())
        }
        return result
    }
}