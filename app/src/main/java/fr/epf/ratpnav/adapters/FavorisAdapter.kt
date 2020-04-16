package fr.epf.ratpnav.adapters

import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import fr.epf.ratpnav.R
import fr.epf.ratpnav.models.Favoris
import fr.epf.ratpnav.utils.SetImageMetro

class FavorisAdapter(var context: Context, var favoris:List<Favoris>): BaseAdapter(){

    override fun getItem(position: Int): Any {
        return favoris[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return favoris.size
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context,
            R.layout.favoris_view,null)
        val txtview:TextView = view.findViewById(R.id.nom_station_favoris)
        val nom_station = favoris[position].station
        txtview.text=nom_station
        txtview.setSingleLine()
        txtview.ellipsize= TextUtils.TruncateAt.MARQUEE
        txtview.marqueeRepeatLimit=-1
        txtview.isSelected=true
        val layout: LinearLayout =view.findViewById(R.id.all_correspondances)
        favoris[position].correspondances.map {
            val imgview = ImageView(context)
            imgview.setBackgroundResource(
                SetImageMetro(it)
            )
            layout.addView(imgview,50,50)
        }

        return view
    }

}
