package com.striyank.naturecollection

class PlantModel(
    val id : String = "Plant0",
    val name : String = "Tulipe",
    val description : String = "Small description",
    val imageUrl : String = "http://graven.yt/plante.jpg",
    val growing : String = "low",
    val consumption : String = "high",
    var isliked : Boolean = false
)