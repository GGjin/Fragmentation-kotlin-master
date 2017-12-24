package me.yokeyword.sample.demo_flow.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import me.yokeyword.sample.demo_flow.ui.fragment.discover.PagerChildFragment

/**
 * Created by YoKeyword on 16/2/5.
 */
class DiscoverFragmentAdapter(fm: FragmentManager, var mTitles:  Array<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            PagerChildFragment.newInstance(0)
        } else if (position == 1) {
            PagerChildFragment.newInstance(1)
        } else {
            PagerChildFragment.newInstance(2)
        }
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }
}
