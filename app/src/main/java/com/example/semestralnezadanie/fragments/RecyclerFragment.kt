package com.example.semestralnezadanie.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.semestralnezadanie.Pub
import com.example.semestralnezadanie.adapters.RecyclerAdapter
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


class RecyclerFragment : Fragment()
{
    private var mRecyclerView: RecyclerView? = null
    private var viewItems: ArrayList<Any> = ArrayList()

    private lateinit var mAdapter: RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>
    private var layoutManager: RecyclerView.LayoutManager? = null

    private val TAG = "MainActivity"
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
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view.findViewById(com.example.semestralnezadanie.R.id.my_recycler_view) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this@RecyclerFragment.context)
        mRecyclerView!!.layoutManager = layoutManager

        mAdapter = RecyclerAdapter(this.context, viewItems)
        mRecyclerView!!.adapter = mAdapter

        //addItemsFromJSON()
    }

    private fun addItemsFromJSON() {
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
    }
}