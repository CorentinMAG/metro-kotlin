package fr.epf.ratpnav.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import fr.epf.ratpnav.R


class MapsFragment : Fragment() {
    lateinit var locationManager:LocationManager
    private var hasGps=false
    private var time=true
    private lateinit var locationGPS:Location
    private lateinit var Map:GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        Map=googleMap
        getLocation(Map)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_maps, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(activity!!,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 10)
        }else {
            mapFragment?.getMapAsync(callback)
        }
    }
    @SuppressLint("MissingPermission")
    private fun getLocation(googleMap: GoogleMap) {
        locationManager= activity!!.getSystemService(LOCATION_SERVICE) as LocationManager
        hasGps=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if(hasGps){
            if(hasGps){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, object:LocationListener{
                    override fun onLocationChanged(location: Location?) {
                        if(location!=null){
                            locationGPS=location
                            var current_position=LatLng(locationGPS.latitude,locationGPS.longitude)
                            googleMap.addMarker(MarkerOptions().position(current_position).title("Ma position"))
                            if(time){
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_position,12f))
                                time=false
                            }

                        }
                    }

                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

                    }

                    override fun onProviderEnabled(provider: String?) {
                    }

                    override fun onProviderDisabled(provider: String?) {
                    }
                })
            }

        }
    }

}
