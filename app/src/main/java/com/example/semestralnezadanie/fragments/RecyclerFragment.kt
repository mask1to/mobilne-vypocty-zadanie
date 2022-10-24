package com.example.semestralnezadanie.fragments

import android.app.DownloadManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralnezadanie.Pub
import com.example.semestralnezadanie.Pubs
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.adapters.PubAdapter
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


class RecyclerFragment : Fragment()
{

    private lateinit var recyclerView: RecyclerView
    private lateinit var pubsList : ArrayList<Pub>
    private lateinit var pubAdapter: PubAdapter

    private val TAG = "RecyclerFragment"
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        parentFragment?.activity?.actionBar?.hide()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(com.example.semestralnezadanie.R.layout.recycler_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        recyclerView = view.findViewById(R.id.my_recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // solve onItemClick
        fetchViaJson(recyclerView)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun fetchViaJson(recyclerView: RecyclerView)
    {
        val url = "https://android.mpage.sk/data/pubs.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Failure")
            }

            override fun onResponse(call: Call, response: Response) {
                val bodyResult = response.body!!.string()
                val gson = GsonBuilder().create()

                val pubs = gson.fromJson(bodyResult, Pubs::class.java)
                activity?.runOnUiThread {
                    recyclerView.adapter = PubAdapter(pubs)
                }
            }

        })
    }

    /*private fun addItemsFromJSON() {
        try {
            val jsonDataString = readJSONDataFromFile()
            val jsonArray = JSONArray(jsonDataString)
            for (i in 0 until jsonArray.length()) {
                val itemObj = jsonArray.getJSONObject(i)
                val name = itemObj.getString("name")
                val date = itemObj.getString("date")
                val pubs = Pub(name, date)
                viewItems.add(pubs)
            }
        } catch (e: JSONException) {
            Log.d(TAG, "addItemsFromJSON: ", e)
        } catch (e: IOException) {
            Log.d(TAG, "addItemsFromJSON: ", e)
        }
    }

    private fun readJSONDataFromFile(): String {
        var inputStream: InputStream? = null
        val builder = StringBuilder()
        try {
            var jsonString: String? = null
            inputStream = resources.openRawResource(com.example.semestralnezadanie.R.raw.pubs)
            val bufferedReader = BufferedReader(
                InputStreamReader(inputStream, "UTF-8")
            )
            while (bufferedReader.readLine().also { jsonString = it } != null) {
                builder.append(jsonString)
            }
        } finally {
            if (inputStream != null)
            {
                inputStream.close()
            }
        }
        return String(builder)
    }*/
}