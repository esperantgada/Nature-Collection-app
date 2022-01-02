package com.striyank.naturecollection

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.striyank.naturecollection.fragments.AddPlantFragment
import com.striyank.naturecollection.fragments.CollectionFragment
import com.striyank.naturecollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment(this), R.string.home_page_title)

        //import bottomNavigationView
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigation.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.homeFragment -> {
                    loadFragment(HomeFragment(this), R.string.home_page_title)
                    return@setOnNavigationItemSelectedListener true
                }


                R.id.collectionFragment -> {
                    loadFragment(CollectionFragment(this), R.string.collection_page_title)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.addPlantFragment -> {
                    loadFragment(AddPlantFragment(this), R.string.add_plants_page_title)
                    return@setOnNavigationItemSelectedListener true
                }
                
                else -> false
            }
        }
    }

    private fun loadFragment(fragment : Fragment, title : Int) {
        val repository = PlantRepository()

        //Update page title
        findViewById<TextView>(R.id.home_page_title).text = resources.getString(title)

        //When data is retrieved in the list by updataData method, load the fragment
        repository.upadateData {
            /**Put fragments in the fragment container which is the FrameLayout**/
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}