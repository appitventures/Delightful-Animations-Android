package com.appitventures.delightfulanimations

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.drawable.*
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.util.Log
import android.support.v7.app.AppCompatActivity
import android.support.v7.content.res.AppCompatResources
import android.view.View
import kotlinx.android.synthetic.main.activity_home_page.*


class HomePageActivity : AppCompatActivity() {

    private var selectedItemId = 0
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        initialiseBottomNavigationClickListener()
        initialiseDefaultFragment()
    }

    private fun initialiseDefaultFragment() {
        bottomNavigationDashboardId.selectedItemId = R.id.item_home_fragment

    }

    private fun initialiseBottomNavigationClickListener() {
        bottomNavigationDashboardId.itemIconTintList = null
        bottomNavigationDashboardId.setOnNavigationItemSelectedListener {
            item: MenuItem ->
            addFragmentBasedOnId(item.itemId)
            true
        }
         disableShiftMode(bottomNavigationDashboardId)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentDashboardContentId, fragment, fragment.javaClass.simpleName)
                .commit()

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addFragmentBasedOnId(itemId: Int) {
        selectedItemId = itemId
        when (itemId) {
            R.id.item_home_fragment -> {
                var currentFragment = supportFragmentManager.findFragmentByTag(getString(R.string.home_fragment))
                if (currentFragment == null)
                    addFragment(HomeFragment.newInstance())
            }
            R.id.item_appointments_fragment -> {
                var currentFragment = supportFragmentManager.findFragmentByTag(getString(R.string.appointments_fragment))
                if (currentFragment == null)
                    addFragment(AppointmentsFragment.newInstance())
            }
            R.id.item_locations_fragment -> {
                var currentFragment = supportFragmentManager.findFragmentByTag(getString(R.string.location_fragment))
                if (currentFragment == null)
                    addFragment(LocationsFragment.newInstance())
            }
            R.id.item_settings_fragment -> {
                var currentFragment = supportFragmentManager.findFragmentByTag(getString(R.string.settings_fragment))
                if (currentFragment == null)
                    addFragment(SettingsFragment.newInstance())
            }
            else -> {
                var currentFragment = supportFragmentManager.findFragmentByTag(getString(R.string.home_fragment))
                if (currentFragment == null)
                    addFragment(HomeFragment.newInstance())
            }
        }

    }


    @SuppressLint("RestrictedApi")
    private fun disableShiftMode(view: BottomNavigationView) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView

                item.setShiftingMode(false)
                // set once again checked value, so view will be updated

                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: Exception) {
            Log.e("BNVHelper", "Unable to get shift mode field", e)
        } catch (e: IllegalAccessException) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e)
        }
    }

}
