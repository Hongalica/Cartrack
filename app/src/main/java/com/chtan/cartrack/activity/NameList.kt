package com.chtan.cartrack.activity


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.chtan.cartrack.R
import com.chtan.cartrack.network.RetroDetail

class NameList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_name_list, container, false)
        val activity = context as ViewUserDetail
        setTheListView(view,activity.masterDetailList)
        return view
    }

    fun setTheListView(view: View,theList:List<RetroDetail>){
        var user_name_list=ArrayList<String>()
        val list_view = view.findViewById<ListView>(R.id.master_name_list)
        theList.forEach { userDetail: RetroDetail ->
            user_name_list.add(userDetail.name.toString())
        }

        val adapter = ArrayAdapter(view.context,android.R.layout.simple_list_item_1, user_name_list)
        list_view?.adapter = adapter
        list_view.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val itemValue = position
                val activity = context as ViewUserDetail
                activity.masterDetailSelection = itemValue
                activity.showMasterDetail()
            }
        }
    }

}
