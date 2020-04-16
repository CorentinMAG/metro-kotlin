package fr.epf.ratpnav

import android.app.Application
import com.facebook.stetho.Stetho

class MetroApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}
