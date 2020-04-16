package fr.epf.ratpnav

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import fr.epf.ratpnav.API.APIService
import fr.epf.ratpnav.models.MetroT
import fr.epf.ratpnav.services.MyIntentService
import fr.epf.ratpnav.utils.MDao
import fr.epf.ratpnav.utils.retrofit
import kotlinx.coroutines.runBlocking


class Splash : AppCompatActivity() {
    private var _receiver=MyReceiver()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        title = "Welcome"
        val filter = IntentFilter("fr.epf.ratpnav.Splash")
        this.registerReceiver(_receiver, filter)
        if(isNetworkConnected()){
            Log.d("EPF","Application is running")
            val service = retrofit().create(APIService::class.java)
            val MetroDao = MDao()
            runBlocking {
                if (MetroDao.getMetro().size != 18) {
                    //running service
                    //val intent = Intent(applicationContext, MyIntentService::class.java)
                    //startService(intent)
                }else{
                    // or switching to mainActivity (comme sa on à pas l'impression qu'une activité s'ouvre puis une
                    // autre ce qui est visuellement pas top top)
                    val intent=Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }else{
            Log.d("EPF","please enable internet")

            AlertDialog.Builder(this).setTitle("Pas de connection internet")
                .setMessage("Vérifier votre connexion internet et réessayer")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok) { _, _ ->finish() }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(this._receiver)
    }

    override fun onStop() {
        super.onStop()
        this.finish()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkConnected(): Boolean {
        //1
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //2
        val activeNetwork = connectivityManager.activeNetwork
        //3
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        //4
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
private class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent!!.getIntExtra("id",0)
        Log.d("EPF","splash activity end")
        val intent=Intent(context,MainActivity::class.java)
        context!!.startActivity(intent)
    }
}
