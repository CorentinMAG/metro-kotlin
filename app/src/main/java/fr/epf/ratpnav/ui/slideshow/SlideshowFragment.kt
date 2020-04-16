package fr.epf.ratpnav.ui.slideshow

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import fr.epf.ratpnav.MetroScheduleActivity
import fr.epf.ratpnav.R
import fr.epf.ratpnav.adapters.FavorisAdapter
import fr.epf.ratpnav.models.Favoris
import fr.epf.ratpnav.models.LikedStation
import fr.epf.ratpnav.utils.LDao
import fr.epf.ratpnav.utils.SDao
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SlideshowFragment : Fragment(),AdapterView.OnItemClickListener {

    private lateinit var slideshowViewModel: SlideshowViewModel
    private var ListLikedStation:List<LikedStation> = ArrayList()
    private var correspondances:List<String> = emptyList()
    private var adapter:BaseAdapter?=null
    private var textview:TextView?=null
    private var ListFavoris:ArrayList<Favoris> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)

        val listview:ListView = root.findViewById(R.id.favoris_list_view)
        adapter=FavorisAdapter(activity!!, ListFavoris)
        listview.onItemClickListener=this
        listview.adapter = adapter
        textview = root.findViewById(R.id.nothing_favoris)

                runBlocking {
                    launch {
                        if(LDao().CheckTable()>0){
                            textview!!.visibility=View.GONE
                            ListLikedStation = LDao().getLiked()
                            ListLikedStation.map {
                                correspondances = SDao().getAllMetroFromStation(it.name)
                                ListFavoris.add(Favoris(it.name,correspondances))
                                (adapter as FavorisAdapter).notifyDataSetChanged()
                            }
                        }
                    }

                }
        return root
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = ListFavoris[position]
        val intent = Intent(activity!!,MetroScheduleActivity::class.java)
        intent.putExtra("nom_metro","")
        intent.putExtra("nom_station",item.station)
        intent.putExtra("correspondances",ArrayList<String>(item.correspondances))
        startActivity(intent)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete_favoris-> {
                runBlocking {
                    LDao().deleteAllLike()
                    ListFavoris.clear()
                    adapter!!.notifyDataSetChanged()
                    textview!!.visibility=View.VISIBLE
                }
            }
            }
        return super.onOptionsItemSelected(item)
    }
}
