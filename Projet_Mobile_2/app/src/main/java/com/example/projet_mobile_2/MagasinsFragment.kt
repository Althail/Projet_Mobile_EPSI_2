package com.example.projet_mobile_2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class MagasinsFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var googleMap: GoogleMap

    @SuppressLint("MissingPermission")
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                googleMap.isMyLocationEnabled = true
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                googleMap.isMyLocationEnabled = true
            }
            else -> {
                // No location access granted.
            }
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        var items: JSONArray? = null
        CoroutineScope(Dispatchers.IO).launch{
            val url = getString(R.string.magasin_data)
            val response = URL(url).readText()
            val jsonObject = JSONObject(response)
            items = jsonObject.getJSONArray("stores")

        }
        runBlocking {
            delay(1000)
        }

        for (i in 0..items!!.length() - 1) {
            val jsonCity = items!!.getJSONObject(i)
            val city = MarkerOptions()
            val cityLatLng = LatLng(jsonCity.optDouble("latitude", 0.0), jsonCity.optDouble("longitude", 0.0))
            city.title(jsonCity.optString("name"))
            city.snippet(jsonCity.optString("address") + " - " +jsonCity.optString("zipcode") + " " +jsonCity.optString("city") )
            city.position(cityLatLng)
            googleMap.addMarker(city)
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.854885, 2.338646), 5f))

        googleMap.setOnMapClickListener {
            //(activity as BaseActivity).showToast(it.toString())

        }


        googleMap.setOnInfoWindowClickListener {
            //(activity as BaseActivity).showToast(it.title.toString())
            val title = it.title.toString()
            var address=""
            var code_postal=""
            var description=""
            var city=""
            var store_img_url = ""

            for (i in 0..items!!.length() - 1) {
                val jsonCity = items!!.getJSONObject(i)
                if(jsonCity.getString("name") == it.title.toString()){
                    store_img_url = jsonCity.getString("pictureStore")
                    address=jsonCity.getString("address")
                    code_postal=jsonCity.getString("zipcode")
                    city=jsonCity.getString("city")
                    description=jsonCity.getString("description")
                    break
                }
            }

            val intent = Intent(activity, MagasinDetailActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("store_img_url", store_img_url)
            intent.putExtra("address", address)
            intent.putExtra("code_postal", code_postal)
            intent.putExtra("city", city)
            intent.putExtra("description", description)

            startActivity(intent)
        }
        this.googleMap = googleMap
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

    }

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_magasins, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Settings.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MagasinsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}