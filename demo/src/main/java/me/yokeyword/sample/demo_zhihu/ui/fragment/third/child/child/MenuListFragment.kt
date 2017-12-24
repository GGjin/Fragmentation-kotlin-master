package me.yokeyword.sample.demo_zhihu.ui.fragment.third.child.child

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultNoAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import me.yokeyword.sample.R
import me.yokeyword.sample.demo_zhihu.adapter.MenuAdapter
import me.yokeyword.sample.demo_zhihu.listener.OnItemClickListener
import me.yokeyword.sample.demo_zhihu.ui.fragment.third.child.ShopFragment
import java.util.*

/**
 * Created by YoKeyword on 16/2/9.
 */
class MenuListFragment : SupportFragment() {

    private var mRecy: RecyclerView? = null
    private var mAdapter: MenuAdapter? = null

    private var mMenus: ArrayList<String>? = null
    private var mCurrentPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments
        if (args != null) {
            mMenus = args.getStringArrayList(ARG_MENUS)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list_menu, container, false)
        initView(view)
        return view
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultNoAnimator()
    }

    private fun initView(view: View) {
        mRecy = view.findViewById<View>(R.id.recy) as RecyclerView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val manager = LinearLayoutManager(_mActivity)
        mRecy!!.layoutManager = manager
        mAdapter = MenuAdapter(_mActivity)
        mRecy!!.adapter = mAdapter
        mAdapter!!.setDatas(mMenus!!)

        mAdapter!!.setOnItemClickListener(object :OnItemClickListener{
            override fun onItemClick(position: Int, view: View, vh: RecyclerView.ViewHolder) {
                showContent(position as Int)
            }

        })

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(SAVE_STATE_POSITION)
            mAdapter!!.setItemChecked(mCurrentPosition)
        } else {
            mCurrentPosition = 0
            mAdapter!!.setItemChecked(0)
        }
    }

    private fun showContent(position: Int) {
        if (position == mCurrentPosition) {
            return
        }

        mCurrentPosition = position

        mAdapter!!.setItemChecked(position)

        val fragment = ContentFragment.newInstance(mMenus!![position])

        (parentFragment as ShopFragment).switchContentFragment(fragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SAVE_STATE_POSITION, mCurrentPosition)
    }

    companion object {
        private val ARG_MENUS = "arg_menus"
        private val SAVE_STATE_POSITION = "save_state_position"

        fun newInstance(menus: ArrayList<String>): MenuListFragment {

            val args = Bundle()
            args.putStringArrayList(ARG_MENUS, menus)

            val fragment = MenuListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
