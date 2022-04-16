package com.example.permitme.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.permitme.R
import com.example.permitme.adapters.ViewPagerAdapter
import com.example.permitme.databinding.FragmentUserBinding

import com.google.android.material.tabs.TabLayoutMediator


class UserFragment : Fragment() {

private lateinit var binding:FragmentUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUserBinding.inflate(inflater)
        // Inflate the layout for this fragment

        val topics= listOf("Accepted","Pending","Rejected")

        val viewPager=binding.viewPager
        val tabLayout=binding.tabLayout

        val adapter= ViewPagerAdapter(childFragmentManager,lifecycle)
        viewPager.adapter=adapter

        TabLayoutMediator(tabLayout,viewPager){
                tab,position-> tab.text=topics[position]

        }.attach()

        return binding.root
    }


}