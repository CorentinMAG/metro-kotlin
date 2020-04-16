package fr.epf.ratpnav.adapters

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import fr.epf.ratpnav.R
import fr.epf.ratpnav.models.MetroSchedules
import fr.epf.ratpnav.utils.SetImageMetro


class StationAdapter(var context: Context, var Stationlist:MutableList<MetroSchedules>): BaseAdapter(){

    override fun getItem(position: Int): Any {
        return Stationlist[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return Stationlist.size
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context,
            R.layout.station_single_view,null)
        val layout:LinearLayout = view.findViewById(R.id.station_layout)
        val textView:TextView =view.findViewById(R.id.my_station_name)
        val layoutImage:LinearLayout=view.findViewById(R.id.corres_container)
        val first_schedule_a:TextView = view.findViewById(R.id.schedule_a)
        val first_schedule_r:TextView = view.findViewById(R.id.schedule_r)
        val station=Stationlist[position]
        first_schedule_a.text = "${station.schedules.schedules[0].destination} : ${station.schedules.schedules[0].message}"
        val size = station.schedules.schedules.size
        if(size>1){
            var i=0
            var dest=station.schedules.schedules[0].destination
            while (dest==station.schedules.schedules[0].destination && i<size-1) {
                i += 1
                dest = station.schedules.schedules[i].destination

            }
            if(i==size-1){
                first_schedule_r.text = "${station.schedules.schedules[1].destination} : ${station.schedules.schedules[1].message}"
            }else{
                first_schedule_r.text = "${station.schedules.schedules[i].destination} : ${station.schedules.schedules[i].message}"
            }

        }else{
            first_schedule_r.text = "${station.schedules.schedules[0].destination} : ${station.schedules.schedules[0].message}"
        }


        first_schedule_a.isSelected=true
        first_schedule_r.isSelected=true
        textView.text=station.correspondances.station.name
        textView.isSelected=true
        station.correspondances.correspondance.map {
            var imageview= ImageView(view.context)
            imageview.setBackgroundResource(
                SetImageMetro(it)
            )
            layoutImage.addView(imageview,50,50)
        }

        return view
    }
    fun deleteAdaper(){
        Stationlist.clear()
    }

}
