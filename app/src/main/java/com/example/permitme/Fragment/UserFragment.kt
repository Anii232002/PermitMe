package com.example.permitme.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.permitme.Home
import com.example.permitme.R
import com.example.permitme.adapters.ViewPagerAdapter
import com.example.permitme.databinding.FragmentUserBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import io.grpc.NameResolver
import kotlin.properties.Delegates


class UserFragment : Fragment() {

private lateinit var binding:FragmentUserBinding
var amount by Delegates.notNull<Int>()
   var email by Delegates.notNull<String>()

    private val args: UserFragmentArgs by navArgs<UserFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUserBinding.inflate(inflater)
        // Inflate the layout for this fragment

        val topics= listOf("Accepted","Pending","Rejected")

         amount = args.myArg
        email = args.email.toString()
        val viewPager=binding.viewPager
        val tabLayout=binding.tabLayout

        val adapter= ViewPagerAdapter(childFragmentManager,lifecycle)
        viewPager.adapter=adapter

        TabLayoutMediator(tabLayout,viewPager){
                tab,position-> tab.text=topics[position]

        }.attach()

        return binding.root
    }
  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.signout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.signout -> {

                FirebaseAuth.getInstance()
                    .signOut()
                val i = Intent(context, Home::class.java)
                startActivity(i)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}