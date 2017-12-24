package me.yokeyword.sample.demo_wechat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import me.yokeyword.eventbusactivityscope.EventBusActivityScope
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.sample.R
import me.yokeyword.sample.demo_wechat.event.TabSelectedEvent
import me.yokeyword.sample.demo_wechat.ui.fragment.first.WechatFirstTabFragment
import me.yokeyword.sample.demo_wechat.ui.fragment.second.WechatSecondTabFragment
import me.yokeyword.sample.demo_wechat.ui.fragment.third.WechatThirdTabFragment
import me.yokeyword.sample.demo_wechat.ui.view.BottomBar
import me.yokeyword.sample.demo_wechat.ui.view.BottomBarTab

/**
 * Created by YoKeyword on 16/6/30.
 */
class MainFragment : SupportFragment() {

    private val mFragments = arrayOfNulls<SupportFragment>(3)

    private var mBottomBar: BottomBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.wechat_fragment_main, container, false)
        initView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val firstFragment = findChildFragment<WechatFirstTabFragment>(WechatFirstTabFragment::class.java)
        if (firstFragment == null) {
            mFragments[FIRST] = WechatFirstTabFragment.newInstance()
            mFragments[SECOND] = WechatSecondTabFragment.newInstance()
            mFragments[THIRD] = WechatThirdTabFragment.newInstance()

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD])
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment
            mFragments[SECOND] = findChildFragment<WechatSecondTabFragment>(WechatSecondTabFragment::class.java)
            mFragments[THIRD] = findChildFragment<WechatThirdTabFragment>(WechatThirdTabFragment::class.java)
        }
    }

    private fun initView(view: View) {
        mBottomBar = view.findViewById<View>(R.id.bottomBar) as BottomBar

        mBottomBar!!
                .addItem(BottomBarTab(_mActivity, R.drawable.ic_message_white_24dp, getString(R.string.msg)))
                .addItem(BottomBarTab(_mActivity, R.drawable.ic_account_circle_white_24dp, getString(R.string.discover)))
                .addItem(BottomBarTab(_mActivity, R.drawable.ic_discover_white_24dp, getString(R.string.more)))

        // 模拟未读消息
        mBottomBar!!.getItem(FIRST)!!.unreadCount = 9

        mBottomBar!!.setOnTabSelectedListener(object : BottomBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int, prePosition: Int) {
                showHideFragment(mFragments[position], mFragments[prePosition])

                val tab = mBottomBar!!.getItem(FIRST)
                if (position == FIRST) {
                    tab!!.unreadCount = 0
                } else {
                    tab!!.unreadCount = tab.unreadCount + 1
                }
            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabReselected(position: Int) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBusActivityScope.getDefault(_mActivity).post(TabSelectedEvent(position))
            }
        })
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == REQ_MSG && resultCode == ISupportFragment.RESULT_OK) {

        }
    }

    /**
     * start other BrotherFragment
     */
    fun startBrotherFragment(targetFragment: SupportFragment) {
        start(targetFragment)
    }

    companion object {
        private val REQ_MSG = 10

        val FIRST = 0
        val SECOND = 1
        val THIRD = 2


        fun newInstance(): MainFragment {

            val args = Bundle()

            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
