package com.example.furnitureapp.util

import android.view.View
import androidx.fragment.app.Fragment
import com.example.furnitureapp.R
import com.example.furnitureapp.activities.ShoppingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

fun Fragment.showBottomNavigationView() {
    val bottomNavigationView = (activity as ShoppingActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
    bottomNavigationView.visibility = View.VISIBLE
}

fun Fragment.hideBottomNavigationView() {
    val bottomNavigationView = (activity as ShoppingActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
    bottomNavigationView.visibility = View.GONE
}