package com.chtan.cartrack.activity


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.chtan.cartrack.R
import com.chtan.cartrack.network.RetroDetail


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MasterDetail : Fragment() {

    var latitude = 0.0
    var longitude =0.0
    var username = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_master_detail, container, false)
        val activity = context as ViewUserDetail
        val locateBtn = view.findViewById<Button>(R.id.user_locate_button)
        setTheMasterDetail(view,activity.masterDetailList,activity.masterDetailSelection )

        locateBtn.setOnClickListener { view ->
            val intent = Intent(activity, UserLocationActivity::class.java)
            intent.putExtra("Latitude",latitude)
            intent.putExtra("Longtitude",longitude)
            intent.putExtra("Username",username)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return view
    }

    fun setTheMasterDetail(view: View,theList:List<RetroDetail>,itemPosition:Int) {
        username = theList[itemPosition].name!!
        view.findViewById<TextView>(R.id.user_id).text = theList[itemPosition].id.toString()
        view.findViewById<TextView>(R.id.user_title).text = theList[itemPosition].username
        view.findViewById<TextView>(R.id.user_email).text = theList[itemPosition].email
        view.findViewById<TextView>(R.id.user_street).text = theList[itemPosition].address!!.street
        view.findViewById<TextView>(R.id.user_suite).text = theList[itemPosition].address!!.suite
        view.findViewById<TextView>(R.id.user_city).text = theList[itemPosition].address!!.city
        view.findViewById<TextView>(R.id.user_zip_code).text = theList[itemPosition].address!!.zipcode
        latitude = theList[itemPosition].address!!.geo!!.lat!!.toDouble()
        longitude = theList[itemPosition].address!!.geo!!.lng!!.toDouble()
        view.findViewById<TextView>(R.id.user_phone).text = theList[itemPosition].phone
        view.findViewById<TextView>(R.id.user_website).text = theList[itemPosition].website
        view.findViewById<TextView>(R.id.user_company_name).text = theList[itemPosition].company!!.name
        view.findViewById<TextView>(R.id.user_company_catchphrase).text = theList[itemPosition].company!!.catchPhrase
        view.findViewById<TextView>(R.id.user_company_business).text = theList[itemPosition].company!!.bs
    }



}
