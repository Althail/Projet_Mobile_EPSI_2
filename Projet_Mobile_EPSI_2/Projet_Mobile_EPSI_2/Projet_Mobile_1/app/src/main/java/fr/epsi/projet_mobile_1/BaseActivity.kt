package fr.epsi.projet_mobile_1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Epsi G2","################ onCreate :"+javaClass.simpleName)
    }

    override fun onStart() {
        super.onStart()
        Log.i("Epsi G2","################ onStart :"+javaClass.simpleName)
    }

    override fun onResume() {
        super.onResume()
        Log.i("Epsi G2","################ onResume :"+javaClass.simpleName)
    }

    override fun onPause() {
        super.onPause()
        Log.i("Epsi G2","################ onPause :"+javaClass.simpleName)
    }

    override fun onStop() {
        super.onStop()
        Log.i("Epsi G2","################ onStop :"+javaClass.simpleName)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Epsi G2","################ onDestroy :"+javaClass.simpleName)
    }

    override fun finish() {
        super.finish()
        Log.i("Epsi G2","################ finish :"+javaClass.simpleName)
    }

    fun setHeaderTitle(title: String?) {
        val textView = findViewById<TextView>(R.id.textViewTitle)
        textView.setText(title)
    }

    fun showBack() {
        val imageViewBack = findViewById<ImageView>(R.id.imageViewBack)
        imageViewBack.visibility = View.VISIBLE
        imageViewBack.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

}
