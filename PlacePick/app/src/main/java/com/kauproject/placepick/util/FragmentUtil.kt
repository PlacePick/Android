package com.kauproject.placepick.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.kauproject.placepick.R

object FragmentUtil {
   fun showFragment(fragmentManager: FragmentManager, fragment: Fragment, tag: String) {
       val transaction: FragmentTransaction =
           fragmentManager.beginTransaction()
               .replace(R.id.fl_main, fragment, tag)
       transaction.addToBackStack(tag).commitAllowingStateLoss()
   }
}