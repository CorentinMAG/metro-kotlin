package fr.epf.ratpnav.utils

import android.app.Service
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import fr.epf.ratpnav.R
import fr.epf.ratpnav.dao.LikedStationDAO
import fr.epf.ratpnav.dao.MetroDAO
import fr.epf.ratpnav.dao.StationDAO
import fr.epf.ratpnav.database.AppDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


fun AppCompatActivity.MDao(): MetroDAO {
    val database = Room.databaseBuilder(this,
        AppDatabase::class.java,"gestion_metro")
        .build()
    return database.getMetroDao()
}

fun AppCompatActivity.SDao(): StationDAO {
    val database = Room.databaseBuilder(this,
        AppDatabase::class.java,"gestion_metro")
        .build()
    return database.getStationDao()
}

fun AppCompatActivity.LDao(): LikedStationDAO {
    val database = Room.databaseBuilder(this,
        AppDatabase::class.java,"gestion_metro")
        .build()
    return database.getLikedDao()
}
fun Fragment.LDao(): LikedStationDAO {
    val database = Room.databaseBuilder(context!!,
        AppDatabase::class.java,"gestion_metro")
        .build()
    return database.getLikedDao()
}

fun retrofit(): Retrofit {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .connectTimeout(20, TimeUnit.SECONDS)
        .build()

    return Retrofit.Builder()
        .baseUrl("https://api-ratp.pierre-grimaud.fr/v4/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
}

fun Fragment.MDao(): MetroDAO {
    val database = Room.databaseBuilder(context!!,
        AppDatabase::class.java,"gestion_metro")
        .build()
    return database.getMetroDao()
}
fun Service.MDao(): MetroDAO {
    val database = Room.databaseBuilder(applicationContext,
        AppDatabase::class.java,"gestion_metro")
        .build()
    return database.getMetroDao()
}
fun Service.LDao(): LikedStationDAO {
    val database = Room.databaseBuilder(applicationContext,
        AppDatabase::class.java,"gestion_metro")
        .build()
    return database.getLikedDao()
}
fun Fragment.SDao(): StationDAO {
    val database = Room.databaseBuilder(context!!,
        AppDatabase::class.java,"gestion_metro")
        .build()
    return database.getStationDao()
}

fun Service.SDao(): StationDAO {
    val database = Room.databaseBuilder(applicationContext,
        AppDatabase::class.java,"gestion_metro")
        .build()
    return database.getStationDao()
}

fun SetImageMetro(name:String):Int{
    val myImage =
    when(name){
        "Métro 1"-> R.drawable.metro1
        "Métro 2"-> R.drawable.metro2
        "Métro 3"-> R.drawable.metro3
        "Métro 3b"-> R.drawable.metro3bis
        "Métro 4"-> R.drawable.metro4
        "Métro 5"-> R.drawable.metro5
        "Métro 6"-> R.drawable.metro6
        "Métro 7"-> R.drawable.metro7
        "Métro 7b"-> R.drawable.metro7bis
        "Métro 8"-> R.drawable.metro8
        "Métro 9"-> R.drawable.metro9
        "Métro 10"-> R.drawable.metro10
        "Métro 11"-> R.drawable.metro11
        "Métro 12"-> R.drawable.metro12
        "Métro 13"-> R.drawable.metro13
        "Métro 14"-> R.drawable.metro14
        else-> R.drawable.metro_bleu
    }
    return myImage
}
