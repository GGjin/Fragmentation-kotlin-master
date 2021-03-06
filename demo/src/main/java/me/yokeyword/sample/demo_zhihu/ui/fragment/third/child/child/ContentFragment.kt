package me.yokeyword.sample.demo_zhihu.ui.fragment.third.child.child


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultNoAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import me.yokeyword.sample.R
import me.yokeyword.sample.demo_zhihu.ui.fragment.CycleFragment
import me.yokeyword.sample.demo_zhihu.ui.fragment.third.child.ShopFragment

/**
 * Created by YoKeyword on 16/2/9.
 */
class ContentFragment : SupportFragment() {

    private var mTvContent: TextView? = null
    private var mBtnNext: Button? = null

    private var mMenu: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments
        if (args != null) {
            mMenu = args.getString(ARG_MENU)
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultNoAnimator()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_content, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        mTvContent = view.findViewById<View>(R.id.tv_content) as TextView
        mBtnNext = view.findViewById<View>(R.id.btn_next) as Button

        mTvContent!!.text = "Content:\n" + mMenu!!

        mBtnNext!!.setOnClickListener {
            // 和MsgFragment同级别的跳转 交给MsgFragment处理
            if (parentFragment is ShopFragment) {
                (parentFragment as ShopFragment).start(CycleFragment.newInstance(1))
            }
        }
    }

    override fun onBackPressedSupport(): Boolean {
        // ContentFragment是ShopFragment的栈顶子Fragment,可以在此处理返回按键事件
        return super.onBackPressedSupport()
    }

    companion object {
        private val ARG_MENU = "arg_menu"

        fun newInstance(menu: String): ContentFragment {

            val args = Bundle()
            args.putString(ARG_MENU, menu)

            val fragment = ContentFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
