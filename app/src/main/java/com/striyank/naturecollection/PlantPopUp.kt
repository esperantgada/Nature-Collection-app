package com.striyank.naturecollection

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.striyank.naturecollection.adapter.PlantAdapter

/**Will display a pop up message for plant details**/

class PlantPopUp(private val adapter : PlantAdapter,
                 private val currentPlant : PlantModel) : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.plant_details)

        setUpComponent()
        setUpCloseButton()
        setUpDeleteButton()
        setUpStarButton()
    }

    private fun updateStarButton(button : ImageView){

        if (currentPlant.isliked){
            button.setImageResource(R.drawable.ic_full_star)
        }else{
            button.setImageResource(R.drawable.ic_empty_star)
        }

    }

    private fun setUpStarButton() {
        val startButton = findViewById<ImageView>(R.id.star_button)

        if (currentPlant.isliked){
            startButton.setImageResource(R.drawable.ic_full_star)
        }else{
            startButton.setImageResource(R.drawable.ic_empty_star)
        }

        //Update the dataBase
        startButton.setOnClickListener {

            currentPlant.isliked = !currentPlant.isliked
            val repo = PlantRepository()
            repo.updatePlant(currentPlant)

            updateStarButton(startButton)
        }
    }

    private fun setUpDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener {
            val repo = PlantRepository()
            repo.deletePlant(currentPlant)
        }
    }

    private fun setUpCloseButton() {
        findViewById<ImageView>(R.id.close_imageView).setOnClickListener {
            dismiss()
        }
    }

    private fun setUpComponent(){
        val detailImage = findViewById<ImageView>(R.id.detail_image)

        Glide.with(adapter.context).load(Uri.parse(currentPlant.imageUrl)).into(detailImage)
        findViewById<TextView>(R.id.plant_name).text = currentPlant.name
        findViewById<TextView>(R.id.description_type).text = currentPlant.description
        findViewById<TextView>(R.id.consumption_type).text = currentPlant.consumption
        findViewById<TextView>(R.id.growing_type).text = currentPlant.growing
    }
}