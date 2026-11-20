package com.daniel.apptienda.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.daniel.apptienda.fragments.FavFragment
import com.daniel.apptienda.fragments.ListFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ListFragment()
            1 -> FavFragment()
            else -> ListFragment()
        }
    }
}
