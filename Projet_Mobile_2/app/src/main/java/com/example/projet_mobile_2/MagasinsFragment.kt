package com.example.projet_mobile_2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
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
import org.json.JSONObject


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


    val cities = "{\"cities\":[{\"city\":\"Bordeaux\",\"lan\":44.847807,\"lng\":-0.579472},\n" +
            "{\"city\":\"Pau\",\"lan\":43.293295,\"lng\":-0.363570},\n" +
            "{\"city\":\"Nantes\",\"lan\":47.215585,\"lng\":-1.554908},\n" +
            "{\"city\":\"Paris\",\"lan\":48.854885,\"lng\":2.338646},\n" +
            "{\"city\":\"Lille\",\"lan\":50.608719,\"lng\":3.063295},\n" +
            "{\"city\":\"Marseille\",\"lan\":43.293551,\"lng\":5.377397},\n" +
            "{\"city\":\"Nice\",\"lan\":43.701680,\"lng\":7.260711},\n" +
            "{\"city\":\"Lyon\",\"lan\":45.759132,\"lng\":4.834604},\n" +
            "{\"city\":\"Montpellier\",\"lan\":43.586120,\"lng\":3.896094},\n" +
            "{\"city\":\"Toulouse\",\"lan\":43.533513,\"lng\":1.411209},\n" +
            "{\"city\":\"Brest\",\"lan\":48.389353,\"lng\":-4.488616},\n" +
            "{\"city\":\"Limoges\",\"lan\":45.838771,\"lng\":1.262108},\n" +
            "{\"city\":\"Clermont-Ferrand\",\"lan\":45.780535,\"lng\":3.093242},\n" +
            "{\"city\":\"Tours\",\"lan\":47.404355,\"lng\":0.688930},\n" +
            "{\"city\":\"Strasbourg\",\"lan\":48.540395,\"lng\":7.727753}]}";

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

        //48.856614

        val jsonCities = JSONObject(cities)
        val items = jsonCities.getJSONArray("cities")

        for (i in 0..items.length() - 1) {
            val jsonCity = items.getJSONObject(i)
            val city = MarkerOptions()
            val cityLatLng = LatLng(jsonCity.optDouble("lan", 0.0), jsonCity.optDouble("lng", 0.0))
            city.title(jsonCity.optString("city"))
            city.snippet("Snippet Test")
            city.position(cityLatLng)
            googleMap.addMarker(city)

        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.854885, 2.338646), 5f))

        googleMap.setOnMapClickListener {
            //(activity as BaseActivity).showToast(it.toString())

        }


        googleMap.setOnInfoWindowClickListener {
            //(activity as BaseActivity).showToast(it.title.toString())
            val intent = Intent(activity, MagasinDetailActivity::class.java)
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