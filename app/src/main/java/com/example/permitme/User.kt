package com.example.permitme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.permitme.adapters.ViewPagerAdapter
import com.example.permitme.databinding.ActivityUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class User : AppCompatActivity() {
    private lateinit var binding:ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        val topics= listOf("Accepted","Pending","Rejected")

        val viewPager=binding.viewPager
        val tabLayout=binding.tabLayout

        val adapter=ViewPagerAdapter(supportFragmentManager,lifecycle)
        viewPager.adapter=adapter

        TabLayoutMediator(tabLayout,viewPager){
            tab,position-> tab.text=topics[position]

        }.attach()
    }
}