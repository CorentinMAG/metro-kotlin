package fr.epf.ratpnav.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import fr.epf.ratpnav.R

class SensAadapter(var context:Context,var schedules:List<String>):BaseAdapter(){

    override fun getItem(position: Int): Any {
        return schedules[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return schedules.size
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View =View.inflate(context,
            R.layout.schedule_a,null)
        val schedule = schedules[position]
        val layout:LinearLayout=view.findViewById(R.id.my_a_layout)
        val txtview = TextView(context)
        txtview.text=schedule
        layout.addView(txtview)
        return view
    }

}
