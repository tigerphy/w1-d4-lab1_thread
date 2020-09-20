package fi.metropolia.fetchtext

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val sampleTxtUrl = "https://raw.githubusercontent.com/joonasmkauppinen/sensor-based-moblie-applications/master/w1-d4-lab_1/sample-text.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloadButton.setOnClickListener(this)

        if (isNetworkAvailable()) showSnackWithMessage("Network available!")
        else showSnackWithMessage("No internet connection.")
    }

    override fun onClick(v: View?) {
        if (isNetworkAvailable()) {
            showSnackWithMessage("Downloading text...")
            Thread(Runnable {
                val result = URL(sampleTxtUrl).readText()
                this@MainActivity.runOnUiThread { this.textView.text = result }
            }).start()
        } else {
            showSnackWithMessage("No internet connection.")
        }
    }

    private fun showSnackWithMessage(msg: String) {
        val snack = Snackbar.make(
            rootLayout,
            msg,
            Snackbar.LENGTH_SHORT
        )
        snack.view.background = resources.getDrawable(R.drawable.snackbar_background, this.theme)
        snack.show()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = this.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnected == true
    }
}
