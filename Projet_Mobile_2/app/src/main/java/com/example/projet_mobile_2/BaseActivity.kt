package com.example.projet_mobile_2

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.i(
            "EPSI PROJET - Sangmin SHIM / Anthony DURET",
            "########## onCreate : " + javaClass.simpleName
        )
    }

    override fun onResume() {
        super.onResume()
        Log.i(
            "EPSI PROJET - Sangmin SHIM / Anthony DURET",
            "########## onResume : " + javaClass.simpleName
        )
    }

    override fun onPause() {
        super.onPause()
        Log.i(
            "EPSI PROJET - Sangmin SHIM / Anthony DURET",
            "########## onPause : " + javaClass.simpleName
        )
    }

    override fun onStop() {
        super.onStop()
        Log.i(
            "EPSI PROJET - Sangmin SHIM / Anthony DURET",
            "########## onStop : " + javaClass.simpleName
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(
            "EPSI PROJET - Sangmin SHIM / Anthony DURET",
            "########## onDestroy : " + javaClass.simpleName
        )
    }

    override fun finish() {
        super.finish()
        Log.i(
            "EPSI PROJET - Sangmin SHIM / Anthony DURET",
            "########## onFinish : " + javaClass.simpleName
        )
    }

    fun setHeaderTitle(title: String?) {
        val textViewHeaderTitle: TextView = findViewById<TextView>(R.id.textViewHeaderTitle)
        textViewHeaderTitle.text = title
    }

    fun showBack() {
        val imageViewBackButton: ImageView = findViewById<ImageView>(R.id.imageViewBackButton)
        imageViewBackButton.visibility = View.VISIBLE
        imageViewBackButton.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    fun setUserData(pathString: String): DatabaseReference {
        val database = FirebaseDatabase.getInstance(getString(R.string.db_url)).reference

        return database.child(pathString)
    }

}