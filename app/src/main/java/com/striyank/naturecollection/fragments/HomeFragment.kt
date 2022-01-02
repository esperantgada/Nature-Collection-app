package com.striyank.naturecollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.striyank.naturecollection.MainActivity
import com.striyank.naturecollection.PlantRepository.Singleton.plantList
import com.striyank.naturecollection.R
import com.striyank.naturecollection.adapter.PlantAdapter
import com.striyank.naturecollection.adapter.PlantItemDecoration

class HomeFragment(private val context: MainActivity) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        val recyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recycler_view)
        recyclerView.adapter = PlantAdapter(context, plantList,R.layout.item_horizontal)


        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recycler_view)
            verticalRecyclerView.adapter = PlantAdapter(context,plantList.filter { it.isliked }, R.layout.item_vertical)
        verticalRecyclerView.addItemDecoration(PlantItemDecoration())

        return view
    }
}