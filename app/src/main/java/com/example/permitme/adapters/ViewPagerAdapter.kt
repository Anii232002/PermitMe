package com.example.permitme.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.permitme.ui.main.AcceptedFragment
import com.example.permitme.ui.main.PendingFragment
import com.example.permitme.ui.main.RejectedFragment

private const val TABS=3;

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return TABS;
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0-> return AcceptedFragment()
            1-> return PendingFragment()
            2-> return RejectedFragment()
        }

        return AcceptedFragment()
    }


}