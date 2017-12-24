package me.yokeyword.sample.demo_zhihu.ui.fragment.second.child

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.sample.R
import me.yokeyword.sample.demo_zhihu.adapter.ZhihuPagerFragmentAdapter

/**
 * Created by YoKeyword on 16/6/5.
 */
class ViewPagerFragment : SupportFragment() {
    private var mTab: TabLayout? = null
    private var mViewPager: ViewPager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.zhihu_fragment_second_pager, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        mTab = view.findViewById<View>(R.id.tab) as TabLayout
        mViewPager = view.findViewById<View>(R.id.viewPager) as ViewPager

        mTab!!.addTab(mTab!!.newTab())
        mTab!!.addTab(mTab!!.newTab())
        mTab!!.addTab(mTab!!.newTab())

        mViewPager!!.adapter = ZhihuPagerFragmentAdapter(childFragmentManager, arrayOf(
                getString(R.string.recommend), getString(R.string.hot), getString(R.string.favorite),
                getString(R.string.more)))
        mTab!!.setupWithViewPager(mViewPager)
    }

    companion object {

        fun newInstance(): ViewPagerFragment {

            val args = Bundle()

            val fragment = ViewPagerFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
