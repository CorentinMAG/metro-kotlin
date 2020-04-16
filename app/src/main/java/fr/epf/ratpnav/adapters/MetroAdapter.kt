package fr.epf.ratpnav.adapters

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import fr.epf.ratpnav.R
import fr.epf.ratpnav.models.FullMetro
import fr.epf.ratpnav.utils.SetImageMetro


class MetroAdapter(var context:Context,var Metrolist:MutableList<FullMetro>):BaseAdapter(){

    override fun getItem(position: Int): Any {
        return Metrolist[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
       return Metrolist.size
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View =View.inflate(context,
            R.layout.card_metro,null)
        var icon:ImageView = view.findViewById(R.id.metro_icon)
        val destination:TextView = view.findViewById(R.id.destinations)
        val card:CardView=view.findViewById(R.id.metro_card_view)
        val infoTextview:TextView = view.findViewById(R.id.infoMetroTextView)

        val metro=Metrolist[position]
        icon.setBackgroundResource(
           SetImageMetro(metro.metro.name)
        )
        card.setCardBackgroundColor(
            when(metro.metro.code){
                "1"->Color.parseColor("#f9ca24")
                "2"->Color.parseColor("#686de0")
                "3"->Color.parseColor("#8a8a00")
                "3b"->Color.parseColor("#48dbfb")
                "4"->Color.parseColor("#be2edd")
                "5"->Color.parseColor("#e67e22")
                "6"->Color.parseColor("#2ecc71")
                "7"->Color.parseColor("#ff9ff3")
                "7b"->Color.parseColor("#1dd1a1")
                "8"->Color.parseColor("#f368e0")
                "9"->Color.parseColor("#f9ca24")
                "10"->Color.parseColor("#f0932b")
                "11"->Color.parseColor("#cd6133")
                "12"->Color.parseColor("#218c74")
                "13"->Color.parseColor("#34ace0")
                "14"->Color.parseColor("#c56cf0")
                "Fun"->Color.parseColor("#7d5fff")
                "Orv"->Color.parseColor("#fed330")
                else ->0
            }
        )
        destination.text=metro.metro.direction
        infoTextview.text = metro.info.message
        infoTextview.isSelected=true


        return view
    }

}
