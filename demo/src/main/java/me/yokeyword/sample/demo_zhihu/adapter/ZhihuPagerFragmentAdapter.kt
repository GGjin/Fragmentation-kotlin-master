package me.yokeyword.sample.demo_zhihu.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import me.yokeyword.sample.demo_zhihu.ui.fragment.second.child.childpager.FirstPagerFragment
import me.yokeyword.sample.demo_zhihu.ui.fragment.second.child.childpager.OtherPagerFragment

/**
 * Created by YoKeyword on 16/6/5.
 */
class ZhihuPagerFragmentAdapter(fm: FragmentManager, var mTitles: Array<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            FirstPagerFragment.newInstance()
        } else {
            OtherPagerFragment.newInstance(mTitles[position])
        }
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }
}
