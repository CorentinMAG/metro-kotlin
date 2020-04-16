package fr.epf.ratpnav

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fr.epf.ratpnav.API.APIService
import fr.epf.ratpnav.API.GetScheduleByLineResult
import fr.epf.ratpnav.API.Schedule
import fr.epf.ratpnav.API.Schedules
import fr.epf.ratpnav.adapters.StationAdapter
import fr.epf.ratpnav.models.Correspondances
import fr.epf.ratpnav.models.MetroSchedules
import fr.epf.ratpnav.models.StationT
import fr.epf.ratpnav.utils.SDao
import fr.epf.ratpnav.utils.retrofit
import kotlinx.android.synthetic.main.detail_metro.*
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MetroDetailActivity: AppCompatActivity(), AdapterView.OnItemClickListener {
    private var idMetro:Int?=null
    private var MetroName:String?=null
    private var ListeAdapt: MutableList<MetroSchedules> =ArrayList()
    private var Listschedules:List<Schedule> = ArrayList()
    private var progressbarschedule:ProgressBar?=null
    private var color:Int=0
    private var MyAdaper:BaseAdapter = StationAdapter(this,ListeAdapt)

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_metro)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setCustomView(R.layout.custom_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        color = intent.getIntExtra("color",0)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
        val bar_view = supportActionBar?.customView
        val title = bar_view?.findViewById<TextView>(R.id.my_text)
        window.statusBarColor=color
        MetroName = intent.getStringExtra("nom")
        title?.text="${MetroName} - ${intent.getStringExtra("direction")}"
        title?.setSingleLine()
        title?.ellipsize=TextUtils.TruncateAt.MARQUEE
        title?.marqueeRepeatLimit=-1
        title?.isSelected=true
        progressbarschedule = findViewById(R.id.progress_bar_schedule)
        my_list_view.setEdgeEffectColor(color)

        my_list_view.onItemClickListener=this

        my_list_view.adapter = MyAdaper

        val StationDao = SDao()

        idMetro = intent.getIntExtra("id",0)

        val pullToRefresh = findViewById<SwipeRefreshLayout>(R.id.pullToRefresh)
        pullToRefresh.setOnRefreshListener {
            ListeAdapt.clear()
            MyAdaper.notifyDataSetChanged()

            runBlocking {
                val ListeStation = StationDao.getStationsName(idMetro!!)
                LoadAdapterAsync(ListeStation)
                pullToRefresh.isRefreshing = false
            }
        }

        runBlocking {
            val ListeStation = StationDao.getStationsName(idMetro!!)
                LoadAdapterAsync(ListeStation)

        }


    }
    fun LoadAdapterAsync(listeStation:List<StationT>) {
        //pas top top mais c'est pour éviter que les stations arrivent dans le désordre à chaque ouverture de l'activité
        val service = retrofit().create(APIService::class.java)
        val StationDao = SDao()
        var i = 0
        fun handleAllRequest() {
            val callasync: Call<GetScheduleByLineResult> =
                service.getScheduleByLineAsync(
                    "metros",
                    MetroName!!.split(" ")[1],
                    listeStation[i].slug,
                    "A+R"
                )
            callasync.enqueue(object : Callback<GetScheduleByLineResult> {
                override fun onResponse(
                    call: Call<GetScheduleByLineResult>,
                    response: Response<GetScheduleByLineResult>
                ) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Listschedules = result!!.result.schedules
                        runBlocking {
                            val connexion =
                                StationDao.getAllMetroFromStation(listeStation[i].name)
                            ListeAdapt.add(
                                MetroSchedules(
                                    Correspondances(
                                        listeStation[i],
                                        connexion
                                    ),
                                    Schedules(Listschedules)
                                )
                            )
                            MyAdaper.notifyDataSetChanged()

                            progressbarschedule!!.visibility = View.GONE
                            i += 1
                            try {
                                handleAllRequest()
                            } catch (e: Exception) {
                                Log.d("EPF", "Requests finished")
                            }
                        }
                    } else {
                        Log.d("EPF", "${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<GetScheduleByLineResult>, t: Throwable) {
                }
            })
        }
        handleAllRequest()
    }
    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val intent= Intent(this,MetroScheduleActivity::class.java)
        val metro = MetroName!!.split("-")[0]
        val station = ListeAdapt[position].correspondances.station.name
        val correspondances = ListeAdapt[position].correspondances.correspondance
        intent.putExtra("nom_metro",metro)
        intent.putExtra("nom_station",station)
        intent.putExtra("correspondances",ArrayList<String>(correspondances))
        intent.putExtra("color",color)

        startActivity(intent)
    }

}