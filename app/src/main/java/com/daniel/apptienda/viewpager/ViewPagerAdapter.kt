package com.daniel.apptienda.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.daniel.apptienda.fragments.ContactFragment
import com.daniel.apptienda.fragments.ListFragment
import com.daniel.apptienda.fragments.PreferencesFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ListFragment()
            1 -> ContactFragment()
            2 -> PreferencesFragment()
            else -> ListFragment()
        }
    }
}
