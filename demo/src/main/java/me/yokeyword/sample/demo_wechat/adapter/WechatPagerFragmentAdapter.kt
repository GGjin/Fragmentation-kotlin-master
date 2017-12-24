package me.yokeyword.sample.demo_wechat.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import me.yokeyword.sample.demo_wechat.ui.fragment.second.FirstPagerFragment
import me.yokeyword.sample.demo_wechat.ui.fragment.second.OtherPagerFragment


/**
 * Created by YoKeyword on 16/6/5.
 */
class WechatPagerFragmentAdapter(fm: FragmentManager, var mTitles:  Array<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            FirstPagerFragment.newInstance()
        } else {
            OtherPagerFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return mTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }
}
