package com.capstone.hidroponichy02.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.hidroponichy02.fragment.DataFragment
import com.capstone.hidroponichy02.fragment.GraphFragment

class SectionPagerAdapter(activity: AppCompatActivity, data: Bundle): FragmentStateAdapter(activity) {

    private var fBundle: Bundle = data


    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position){
            0 -> fragment = DataFragment()
            1 -> fragment = GraphFragment()
        }
        fragment?.arguments = this.fBundle
        return fragment as Fragment
    }
}