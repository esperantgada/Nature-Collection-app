package com.striyank.naturecollection.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.striyank.naturecollection.MainActivity
import com.striyank.naturecollection.PlantRepository.Singleton.plantList
import com.striyank.naturecollection.R
import com.striyank.naturecollection.adapter.PlantAdapter
import com.striyank.naturecollection.adapter.PlantItemDecoration


class CollectionFragment(private val context : MainActivity) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater?.inflate(R.layout.fragment_collection, container, false)

        //filter will select only liked plants

        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_view)
        collectionRecyclerView.adapter = PlantAdapter(context, plantList.filter { it.isliked }, R.layout.item_vertical)
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)

        /**Creates space between items**/
        collectionRecyclerView.addItemDecoration(PlantItemDecoration())
        return view
    }
}