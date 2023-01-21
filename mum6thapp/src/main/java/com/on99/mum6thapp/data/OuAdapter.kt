package com.on99.mum6thapp.data

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.on99.mum6thapp.MainActivity
import com.on99.mum6thapp.R

class OuAdapter(context: Context, itemlist: ArrayList<List<String>>) : BaseAdapter() {

    //    private var activity: Activity? = null
    private var mContext: Context? = null
    private var itemlist: ArrayList<List<String>>? = null

    private val TAG:String = "OuAdapter"

    init {
//        this.activity = activity
        this.mContext = context
        this.itemlist = itemlist
    }


    override fun getCount(): Int {
        Log.e(TAG," override fun getCount(): Int {")
        return itemlist!!.size
    }

    override fun getItem(p0: Int): Any {
        Log.e(TAG," override fun getItem(p0: Int): Any {")
        return itemlist!!.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        Log.e(TAG,"override fun getItemId(p0: Int): Long {")
        return itemlist!!.get(p0)[0].toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var p1:View = LayoutInflater.from(mContext).inflate(R.layout.temp_list_uu,null)
        val text:TextView = p1.findViewById(R.id.textView_text)
        val date:TextView = p1.findViewById(R.id.textView_date)
        val author:TextView = p1.findViewById(R.id.textView_author)
        text.setText(itemlist!!.get(p0)[1])
        date.setText(itemlist!!.get(p0)[2])
        author.setText(itemlist!!.get(p0)[3])
        return p1

    }
}