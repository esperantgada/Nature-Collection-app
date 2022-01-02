package com.striyank.naturecollection.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.striyank.naturecollection.MainActivity
import com.striyank.naturecollection.PlantModel
import com.striyank.naturecollection.PlantRepository
import com.striyank.naturecollection.PlantRepository.Singleton.downloadedUri
import com.striyank.naturecollection.R
import java.util.*

class AddPlantFragment(private val context : MainActivity) : Fragment() {

    private var uploadedImage : ImageView? = null
    private var file : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add_plant, container, false)

        uploadedImage = view.findViewById(R.id.uploaded_image)
        val pickUpButton = view.findViewById<Button>(R.id.upload_image_button)

        pickUpButton.setOnClickListener {
            pickUpImage()
        }

        val confirmButton = view.findViewById<Button>(R.id.button_confirm)
        confirmButton.setOnClickListener { sendImage(view) }

        return view
    }

    private fun sendImage(view: View) {
        //Insert image in the firebase storage
        val repo = PlantRepository()

        file?.let {
            repo.uploadImage(it){
                val plantName = view.findViewById<TextView>(R.id.add_plant_name)
                val plantDescription = view.findViewById<TextView>(R.id.add_plant_description)
                val grow = view.findViewById<Spinner>(R.id.plant_growing_spinner).selectedItem.toString()
                val consumption = view.findViewById<Spinner>(R.id.consumption_spinner).selectedItem.toString()
                val downloadedImageUrl = downloadedUri

                //New plant object
                val plant = PlantModel(
                        UUID.randomUUID().toString(),
                        plantName.toString(),
                        plantDescription.toString(),
                        downloadedImageUrl.toString(),
                        grow,
                        consumption
                    )

                    //insertion in the database
                    repo.insertPlant(plant)

            }
        }
    }

    //Select an image with intent
    private fun pickUpImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 47)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 47 && resultCode == Activity.RESULT_OK){

            //Retrieve image
            if (data == null || data.data == null) return
             file = data.data

            //update Image view with selected image
            uploadedImage?.setImageURI(file)

        }
    }
}