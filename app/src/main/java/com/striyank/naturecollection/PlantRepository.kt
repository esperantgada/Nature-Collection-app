package com.striyank.naturecollection


import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.striyank.naturecollection.PlantRepository.Singleton.downloadedUri
import com.striyank.naturecollection.PlantRepository.Singleton.firebaseReference
import com.striyank.naturecollection.PlantRepository.Singleton.firebaseStorageInstance
import com.striyank.naturecollection.PlantRepository.Singleton.plantList
import java.util.*

class PlantRepository {

    object Singleton{

        val BUCKET_URL: String = "gs://nature-collection-561dc.appspot.com"
        val firebaseStorageInstance = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        //Get reference to the database in firebase
        val firebaseReference = FirebaseDatabase.getInstance().getReference("Plants")

        //Retrieve a list of plants from the database
        val plantList = arrayListOf<PlantModel>()

        //Current image link
        var downloadedUri : Uri? = null
    }

    //This callback helps to call the function when we want

    fun upadateData(callback : () -> Unit){
        //Get Data from the database and store it in plantList
        firebaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                //Remove former plants before add new plants in the lists
                plantList.clear()

                for (ds in snapshot.children){
                    //Build a plant object
                    val plant = ds.getValue(PlantModel::class.java)

                    if (plant != null){
                        plantList.add(plant)
                    }
                }

                //Act the callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    //Method to store image in firebase storage
    fun uploadImage(file : Uri, callback: () -> Unit){

        if (file != null) {
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = firebaseStorageInstance.child(fileName)
            val uploadTask = ref.putFile(file)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> {task ->
                if (!task.isSuccessful){
                    task.exception?.let { throw it }
                }

                return@Continuation ref.downloadUrl
            }).addOnCompleteListener{ task ->
                if (task.isSuccessful){

                    //Retrieves the image
                     downloadedUri = task.result
                    callback()
                }
            }
        }
    }



    //Updates a plant object in the database
    fun updatePlant(plant : PlantModel) = firebaseReference.child(plant.id).setValue(plant)

    //insert a plant object in the database
    fun insertPlant(plant : PlantModel) = firebaseReference.child(plant.id).setValue(plant)


    //Delete plant from the database
    fun deletePlant(plant : PlantModel) = firebaseReference.child(plant.id).removeValue()
}