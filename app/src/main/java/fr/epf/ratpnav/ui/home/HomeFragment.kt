package fr.epf.ratpnav.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import fr.epf.ratpnav.*
import fr.epf.ratpnav.API.APIService
import fr.epf.ratpnav.API.GetTrafficResult
import fr.epf.ratpnav.API.MetroInfo
import fr.epf.ratpnav.adapters.MetroAdapter
import fr.epf.ratpnav.models.FullMetro
import fr.epf.ratpnav.models.MetroT
import fr.epf.ratpnav.utils.MDao
import fr.epf.ratpnav.utils.retrofit
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(),AdapterView.OnItemClickListener{

    private lateinit var homeViewModel: HomeViewModel
    private var MetroList:List<MetroT> = emptyList()
    private var InfoList:List<MetroInfo> = emptyList()
    private var AdapterCustomList:MutableList<FullMetro> = ArrayList()
    private var metro_grid_view:GridView?=null
    private var progress_bar:ProgressBar?=null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        metro_grid_view = root.findViewById(R.id.metro_grid_view)
        progress_bar = root.findViewById(R.id.my_progress_bar)
        metro_grid_view!!.onItemClickListener=this

        var MyCustomAdapter:BaseAdapter = MetroAdapter(activity!!,AdapterCustomList)
        metro_grid_view!!.adapter = MyCustomAdapter

        LaunchCard(MyCustomAdapter)

        return root
    }

    fun LaunchCard(Adapter:BaseAdapter){
        //asynchrone opération > synchrone et on fait attendre avec une petite animation
        val service = retrofit().create(APIService::class.java)
        val callasync:Call<GetTrafficResult> =service.getInfoForEachLineAsync("metros")
        callasync.enqueue(object: Callback<GetTrafficResult> {
            override fun onResponse(
                call: Call<GetTrafficResult>,
                response: Response<GetTrafficResult>
            ) {
                if(response.isSuccessful){
                    val MetroDAO = MDao()
                    runBlocking {
                        MetroList = MetroDAO.getMetro()
                    }
                    progress_bar!!.visibility=View.GONE
                    val result = response.body()
                    InfoList = result!!.result.metros
                    InfoList.forEachIndexed{index, metroInfo -> AdapterCustomList.add(
                        FullMetro(
                            MetroList[index],
                            metroInfo
                        )
                    )
                        Adapter.notifyDataSetChanged()
                    }
                }else{
                    Log.d("EPF","${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<GetTrafficResult>, t: Throwable) {
                Log.d("EPF","${t.localizedMessage}")
            }
        })
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val intent=Intent(context,MetroDetailActivity::class.java)
        val nom=MetroList[position].name
        val direction = MetroList[position].direction
        val _id=MetroList[position].id
        val card = view?.findViewById<CardView>(R.id.metro_card_view)
        val color=card?.cardBackgroundColor?.defaultColor

        intent.putExtra("nom",nom)
        intent.putExtra("direction",direction)
        intent.putExtra("id",_id)
        intent.putExtra("color",color)
        startActivity(intent)
    }
}


