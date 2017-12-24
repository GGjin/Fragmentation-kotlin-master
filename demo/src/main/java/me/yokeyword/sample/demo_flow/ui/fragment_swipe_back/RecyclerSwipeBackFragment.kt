package me.yokeyword.sample.demo_flow.ui.fragment_swipe_back

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.sample.R
import me.yokeyword.sample.demo_flow.adapter.PagerAdapter
import me.yokeyword.sample.demo_flow.listener.OnItemClickListener
import java.util.*


class RecyclerSwipeBackFragment : BaseSwipeBackFragment() {

    private var mToolbar: Toolbar? = null

    private var mRecy: RecyclerView? = null
    private var mAdapter: PagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_swipe_back_recy, container, false)

        initView(view)

        return attachToSwipeBack(view)
    }

    private fun initView(view: View) {
        mRecy = view.findViewById<View>(R.id.recy) as RecyclerView

        mToolbar = view.findViewById<View>(R.id.toolbar) as Toolbar
        _initToolbar(mToolbar!!)

        mAdapter = PagerAdapter(_mActivity)
        val manager = LinearLayoutManager(_mActivity)
        mRecy!!.layoutManager = manager
        mRecy!!.adapter = mAdapter


        mAdapter!!.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int, view: View) {
                start(FirstSwipeBackFragment.newInstance())
            }

        })
        // Init Datas
        val items = ArrayList<String>()
        for (i in 0..19) {
            val item: String
            item = getString(R.string.favorite) + " " + i
            items.add(item)
        }
        mAdapter!!.setDatas(items)
    }

    companion object {
        private val ARG_FROM = "arg_from"

        fun newInstance(): RecyclerSwipeBackFragment {
            return RecyclerSwipeBackFragment()
        }
    }
}
