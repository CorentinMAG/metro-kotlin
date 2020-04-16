package fr.epf.ratpnav.services

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import fr.epf.ratpnav.API.APIService
import fr.epf.ratpnav.utils.MDao
import fr.epf.ratpnav.utils.SDao
import fr.epf.ratpnav.models.MetroT
import fr.epf.ratpnav.models.StationT
import fr.epf.ratpnav.utils.retrofit
import kotlinx.coroutines.runBlocking

class MyIntentService : IntentService("MyIntentService") {

    override fun onHandleIntent(arg0: Intent?) {

    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val task = SrvTask().executeOnExecutor(
            AsyncTask.THREAD_POOL_EXECUTOR, startId)
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
    }
    private inner class SrvTask : AsyncTask<Int, Int, String>() {

        override fun onPreExecute() {

        }

        override fun doInBackground(vararg params: Int?): String {

            val startId = params[0]

            val service = retrofit().create(APIService::class.java)
            val MetroDao = MDao()
            val StationDao=SDao()

            //ces données ne changent pas c'est pourquoi on les stocke dès le début en bdd
                runBlocking {
                        var result = service.getDestination("metros")
                        result.result.metros.map {
                            MetroDao.addMetro(
                                MetroT(
                                    0,
                                    it.code,
                                    it.name,
                                    it.directions,
                                    it.id
                                )
                            )
                            var StationR = service.getStations("metros", it.code)
                            var code = it.id
                            StationR.result.stations.map {
                                StationDao.addStation(
                                    StationT(
                                        0,
                                        code,
                                        it.name,
                                        it.slug
                                    )
                                )
                            }
                        }
                }

            val intent=Intent()
            intent.action="fr.epf.ratpnav.Splash"
            intent.putExtra("id",startId)
            sendBroadcast(intent)
            return "Service complete $startId"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            val counter = values.get(0)
        }

        override fun onPostExecute(result: String) {
        }
    }
}