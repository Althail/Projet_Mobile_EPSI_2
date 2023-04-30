package com.example.projet_mobile_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class OffresFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offres, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val offres = arrayListOf<Offres>()
        val recyclerviewOffers: RecyclerView =
            view.findViewById<RecyclerView>(R.id.recyclerviewOffers)
        recyclerviewOffers.layoutManager = LinearLayoutManager(requireContext())
        val offresAdapter = OffresAdapter(offres)
        recyclerviewOffers.adapter = offresAdapter

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
        val mRequestURL = getString(R.string.offres_data)
        val request = Request.Builder()
            .url(mRequestURL)
            .get()
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // handle failure
            }

            override fun onResponse(call: Call, response: Response) {
                val data = response.body?.string()

                if (data != null) {
                    val jsOffers = JSONObject(data)
                    val jsArrayOffers = jsOffers.getJSONArray("items")
                    for (i in 0 until jsArrayOffers.length()) {
                        val js = jsArrayOffers.getJSONObject(i)
                        val offre = Offres(
                            js.optString("name", "not found"),
                            js.optString("description", "not found"),
                            js.optString("picture_url", "not found")
                        )
                        offres.add(offre)
                    }
                    requireActivity().runOnUiThread {
                        offresAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    companion object {
        // factory method and constants
    }

}