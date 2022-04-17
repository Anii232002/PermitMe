package com.example.permitme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.example.permitme.Adapter.Admin_Adapter
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth

class Admin : AppCompatActivity() {
    lateinit var top_menu : TabLayout
    lateinit var viewPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        top_menu = findViewById(R.id.top_menu)
        viewPager = findViewById(R.id.viewpager)
        top_menu.addTab(top_menu.newTab().setText("Student"))
        top_menu.addTab(top_menu.newTab().setText("Faculty"))
        top_menu.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = Admin_Adapter(this, supportFragmentManager,
            top_menu.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(top_menu))
        top_menu.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab:TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.signout, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.signout -> {

                FirebaseAuth.getInstance()
                    .signOut()
                val i = Intent(this,Home::class.java)
                startActivity(i)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

private fun AppBarLayout.setNavigationOnClickListener(function: () -> Unit) {

}

private fun AppBarLayout.setOnMenuItemClickListener(any: Any) {

}
