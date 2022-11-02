package com.example.semestralnezadanie.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semestralnezadanie.R
import com.example.semestralnezadanie.adapters.PubAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


class RecyclerFragment : Fragment()
{
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingButton : FloatingActionButton
    private lateinit var sortingFloating : FloatingActionButton

    companion object{
        var isSorted : Boolean = true
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    //disabled onBackPressed
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
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
        recyclerView.adapter = PubAdapter(requireContext())

        floatingButton = view.findViewById(R.id.btnFloat)
        sortingFloating = view.findViewById(R.id.btnSort)

        sortingFloating.setOnClickListener {
            isSorted = !isSorted
            recyclerView.adapter = PubAdapter(requireContext())
        }

        floatingButton.setOnClickListener {
            val action = RecyclerFragmentDirections.actionRecyclerFragmentToFormFragment()
            findNavController().navigate(action)
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when(item.itemId){
            R.id.switch_sort -> {
                isSorted = !isSorted
                showIcon(item)

                return true
            }

            else -> false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        inflater.inflate(R.menu.sorting_menu, menu)

        val btn = menu.findItem(R.id.switch_sort)
        showIcon(btn)
    }

    private fun showIcon(item : MenuItem?)
    {
        if(item == null)
        {
            return
        }

        item.icon =
            if(isSorted)
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_baseline_sort_24)
            else
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_baseline_sort_by_alpha_24)
    }

}