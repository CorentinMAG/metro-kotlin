package fr.epf.ratpnav

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


class MyScannerActivity : Activity(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null
    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        mScannerView = ZXingScannerView(this) // Programmatically initialize the scanner view
        setContentView(mScannerView) // Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera() // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera() // Stop camera on pause
    }

    override fun handleResult(rawResult: Result?) {
        val list = rawResult.toString().split("/")
        val metro:String = list[0]
        val station:String=list[1]
        val correspondances = list[2].split(",")
        val color:String=list[3]
        val intent= Intent(this,MetroScheduleActivity::class.java)
        intent.putExtra("nom_metro",metro)
        intent.putExtra("nom_station",station)
        intent.putExtra("correspondances",ArrayList<String>(correspondances))
        intent.putExtra("color",color)
        Log.d("AQW","${correspondances}")
        startActivity(intent)
    }

}
