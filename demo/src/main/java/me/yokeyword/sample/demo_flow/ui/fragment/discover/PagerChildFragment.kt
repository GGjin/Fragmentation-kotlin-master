package me.yokeyword.sample.demo_flow.ui.fragment.discover

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.yokeyword.sample.R
import me.yokeyword.sample.demo_flow.adapter.PagerAdapter
import me.yokeyword.sample.demo_flow.base.MySupportFragment
import me.yokeyword.sample.demo_flow.listener.OnItemClickListener
import me.yokeyword.sample.demo_flow.ui.fragment.CycleFragment
import java.util.*


class PagerChildFragment : MySupportFragment() {

    private var mFrom: Int = 0

    private var mRecy: RecyclerView? = null
    private var mAdapter: PagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments
        if (args != null) {
            mFrom = args.getInt(ARG_FROM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pager, container, false)

        initView(view)

        return view
    }

    private fun initView(view: View) {
        mRecy = view.findViewById<View>(R.id.recy) as RecyclerView

        mAdapter = PagerAdapter(_mActivity)
        val manager = LinearLayoutManager(_mActivity)
        mRecy!!.layoutManager = manager
        mRecy!!.adapter = mAdapter


        mAdapter!!.setOnItemClickListener(object :OnItemClickListener{
            override fun onItemClick(position: Int, view: View) {
                if (parentFragment is DiscoverFragment) {
                    (parentFragment as DiscoverFragment).start(CycleFragment.newInstance(1))
                }
            }
        })

        mRecy!!.post {
            // Init Datas
            val items = ArrayList<String>()
            for (i in 0..19) {
                val item: String
                if (mFrom == 0) {
                    item = getString(R.string.recommend) + " " + i
                } else if (mFrom == 1) {
                    item = getString(R.string.hot) + " " + i
                } else {
                    item = getString(R.string.favorite) + " " + i
                }
                items.add(item)
            }
            mAdapter!!.setDatas(items)
        }
    }

    companion object {
        private val ARG_FROM = "arg_from"

        fun newInstance(from: Int): PagerChildFragment {
            val args = Bundle()
            args.putInt(ARG_FROM, from)

            val fragment = PagerChildFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
