package com.example.comffee.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.comffee.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        supportActionBar?.hide()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val homeFragment=HomeFragment()
        val itemFragment=ItemListFragment()
        val cartFragment=CartFragment()
        val profileFragment=ProfileFragment()
        setCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.icon_home->{
                    val intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                    // Biar gada transisi blink
                    overridePendingTransition(0, 0)
                }
                R.id.icon_item->{
                    val intent = Intent(this, ItemListFragment::class.java)
                    startActivity(intent)
                    setCurrentFragment(itemFragment)
                }
                R.id.icon_cart->{
                    val intent = Intent(this, CartFragment::class.java)
                    startActivity(intent)
                    setCurrentFragment(cartFragment)
                }
                R.id.icon_profile->{
                    val intent = Intent(this, ProfileFragment::class.java)
                    startActivity(intent)
                    setCurrentFragment(profileFragment)
                }
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.home_container,fragment)
            commit()
        }
}