package com.striyank.naturecollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.striyank.naturecollection.*
import com.striyank.naturecollection.fragments.HomeFragment

class PlantAdapter(
    val context : MainActivity,
    private val plantList : List<PlantModel>,
    private val layoutId : Int) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {


    class PlantViewHolder( view : View) : RecyclerView.ViewHolder(view){

        val plantImage = view.findViewById<ImageView>(R.id.image_item)
        val plantName : TextView? = view.findViewById(R.id.plant_name)
        val plantDescription : TextView? = view.findViewById(R.id.plant_description)
        val starImage = view.findViewById<ImageView>(R.id.star_imageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layoutId, parent, false)

        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val currentPlant = plantList[position]

        //Get reference to the repository
        val repo = PlantRepository()

        /**Get plant list from the internet using Glide**/
        Glide.with(context).load(Uri.parse(currentPlant.imageUrl)).into(holder.plantImage)
        holder.plantName?.text   = currentPlant.name
        holder.plantDescription?.text = currentPlant.description

        if (currentPlant.isliked){
            holder.starImage.setImageResource(R.drawable.ic_full_star)
        }else{
            holder.starImage.setImageResource(R.drawable.ic_empty_star)
        }

        /**Interact with star by inversing its state**/
        holder.starImage.setOnClickListener {
            currentPlant.isliked = !currentPlant.isliked
            repo.updatePlant(currentPlant)
        }

        /**New interaction on plant details**/
        holder.itemView.setOnClickListener{
            PlantPopUp(this, currentPlant).show()
        }
    }

    override fun getItemCount() = plantList.size
}