package fr.epf.ratpnav

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import com.like.LikeButton
import com.like.OnLikeListener
import fr.epf.ratpnav.API.APIService
import fr.epf.ratpnav.API.GetScheduleByLineResult
import fr.epf.ratpnav.adapters.SensAadapter
import fr.epf.ratpnav.models.LikedStation
import fr.epf.ratpnav.utils.LDao
import fr.epf.ratpnav.utils.SetImageMetro
import fr.epf.ratpnav.utils.retrofit
import kotlinx.android.synthetic.main.activity_metro_schedule.*
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MetroScheduleActivity : AppCompatActivity() {
    private var sens:String=""
    private var nom_station:String=""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metro_schedule)


        val metro_nom = intent.getStringExtra("nom_metro")
        nom_station = intent.getStringExtra("nom_station")
        var correspondances = intent.getStringArrayListExtra("correspondances")
        var color=intent.getIntExtra("color",0)
        if(color==0){
            color= Color.parseColor("#00AA91")
        }
        my_scroll_view.setEdgeEffectColor(color)
        runBlocking {
            like_button.isLiked = LDao().CheckLike(nom_station!!) != 0
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(color))

        val bar_view = supportActionBar?.customView
        val title = bar_view?.findViewById<TextView>(R.id.my_text)

        window.statusBarColor=color

        title?.text=when(metro_nom){
            ""->"Station ${nom_station}"
            else->"${metro_nom} - Station ${nom_station}"
        }

        title?.setSingleLine()
        title?.ellipsize= TextUtils.TruncateAt.MARQUEE
        title?.marqueeRepeatLimit=-1
        title?.isSelected=true
        nom_station_view.text = nom_station

        correspondances.map {
            var imgview = ImageView(this)
            imgview.setBackgroundResource(
                SetImageMetro(it)
            )
            layout_correspondance.addView(imgview,60,60)

        }
        if(correspondances!!.size==0){
            CreateSchedule(metro_nom)

        }else{
            correspondances.map{
                CreateSchedule(it)

            }
        }


        like_button.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                runBlocking {
                    LDao().addLike(LikedStation(0,nom_station))
                    Toast.makeText(applicationContext,"La station ${nom_station} a été ajouté aux favoris !",Toast.LENGTH_SHORT).show()
                }

            }
            override fun unLiked(likeButton: LikeButton) {
                runBlocking {
                    try{
                        val likedObject = LDao().getLike(nom_station)
                        LDao().deleteLike(likedObject)
                        Toast.makeText(applicationContext,"La station ${nom_station} a été retiré des favoris !",Toast.LENGTH_SHORT).show()
                    }catch (e:Exception){
                        Toast.makeText(applicationContext,"Erreur ! La station  ${nom_station} n'existe pas !",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        pullToRefreshFav.setOnRefreshListener {
            val intent = intent
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish()
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }

    }
    fun AppelApiAsync(textview:TextView,listview:ListView,metro:String,station:String,way:String,horaire:ArrayList<String>){
        val service = retrofit().create(APIService::class.java)
        val callasync: Call<GetScheduleByLineResult> =service.getScheduleByLineAsync(
            "metros",
            metro,station,
            way
        )
        callasync.enqueue(
            object : Callback<GetScheduleByLineResult> {
                override fun onResponse(
                    call: Call<GetScheduleByLineResult>,
                    response: Response<GetScheduleByLineResult>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        sens=result!!.result.schedules[0].destination
                        result.result.schedules.map {
                            horaire.add(it.message)
                        }
                        textview.text=sens
                        listview.adapter = SensAadapter(applicationContext,horaire)
                        setListViewHeightBasedOnChildren(listview)
                        my_progress_bar.visibility=View.GONE
                    } else {
                        Log.d("LAB", "${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<GetScheduleByLineResult>, t: Throwable) {
                }
            })
    }
    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter: ListAdapter = listView.getAdapter()
            ?: // pre-condition
            return
        var totalHeight: Int = listView.getPaddingTop() + listView.getPaddingBottom()
        for (i in 0 until listAdapter.getCount()) {
            val listItem: View = listAdapter.getView(i, null, listView)
            if (listItem is ViewGroup) {
                listItem.setLayoutParams(
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                )
            }
            listItem.measure(0, 0)
            totalHeight += listItem.getMeasuredHeight()
        }
        val params: ViewGroup.LayoutParams = listView.getLayoutParams()
        params.height = totalHeight + listView.getDividerHeight() * (listAdapter.getCount() - 1)
        listView.setLayoutParams(params)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun CreateSchedule(name:String){
        val tab= listOf("A","R")
        val first_layout = LinearLayout(this)
        val imgview=ImageView(this)
        imgview.setBackgroundResource(
            SetImageMetro(name)
           )
        my_layout_schedules.addView(first_layout)
        first_layout.layoutParams.width=LinearLayout.LayoutParams.MATCH_PARENT
        first_layout.layoutParams.height= LinearLayout.LayoutParams.WRAP_CONTENT
        first_layout.setPadding(10)
        first_layout.orientation=LinearLayout.HORIZONTAL
        first_layout.addView(imgview,80,80)
        for(i in tab){
            val txtview = TextView(this)
            val listview=ListView(this)
            val horaire= ArrayList<String>()
            val layout = LinearLayout(this)
            first_layout.addView(layout)
            layout.layoutParams.width=500
            layout.layoutParams.height= LinearLayout.LayoutParams.WRAP_CONTENT
            layout.setPadding(10)
            layout.orientation=LinearLayout.VERTICAL
            layout.addView(txtview)
            txtview.setSingleLine()
            txtview.ellipsize=TextUtils.TruncateAt.MARQUEE
            txtview.marqueeRepeatLimit=-1
            txtview.isSelected=true
            txtview.typeface= Typeface.DEFAULT_BOLD
            layout.addView(listview)
            AppelApiAsync(txtview,listview,name.split(" ")[1],nom_station,i,horaire)
        }
    }
}
