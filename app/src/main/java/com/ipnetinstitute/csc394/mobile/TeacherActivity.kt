package com.ipnetinstitute.csc394.mobile

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ipnetinstitute.csc394.mobile.ui.app.HomeFragment
import com.ipnetinstitute.csc394.mobile.ui.app.SurveyListFragment

class TeacherActivity : AppCompatActivity() {
    lateinit var toolbar: ActionBar

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.navigation_home -> {
                toolbar.title = "Home"
                val homeFragment = HomeFragment.newInstance()
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_pending -> {
                toolbar.title = "Pending"
                val surveyListFragment = SurveyListFragment.newInstance()
                openFragment(surveyListFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_completed -> {
                toolbar.title = "Completed"
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!

        val navigation = findViewById<BottomNavigationView>(R.id.navigationView)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        navigation.itemIconTintList = null
        navigation.selectedItemId = R.id.navigation_home
//        navigation.itemIconSize = 100
    }
}