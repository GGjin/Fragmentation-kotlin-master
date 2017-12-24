package me.yokeyword.sample.demo_flow.ui.fragment

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import me.yokeyword.sample.R
import me.yokeyword.sample.demo_flow.base.BaseBackFragment

/**
 * Created by YoKeyword on 16/2/7.
 */
class CycleFragment : BaseBackFragment() {

    private var mToolbar: Toolbar? = null
    private var mTvName: TextView? = null
    private var mBtnNext: Button? = null
    private var mBtnNextWithFinish: Button? = null

    private var mNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            mNumber = args.getInt(ARG_NUMBER)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cycle, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        mToolbar = view.findViewById<View>(R.id.toolbar) as Toolbar
        mTvName = view.findViewById<View>(R.id.tv_name) as TextView
        mBtnNext = view.findViewById<View>(R.id.btn_next) as Button
        mBtnNextWithFinish = view.findViewById<View>(R.id.btn_next_with_finish) as Button

        val title = "CyclerFragment " + mNumber

        mToolbar!!.title = title
        initToolbarNav(mToolbar!!)

        mTvName!!.text = title
        mBtnNext!!.setOnClickListener { start(CycleFragment.newInstance(mNumber + 1)) }
        mBtnNextWithFinish!!.setOnClickListener { startWithPop(CycleFragment.newInstance(mNumber + 1)) }
    }

    companion object {
        private val ARG_NUMBER = "arg_number"

        fun newInstance(number: Int): CycleFragment {
            val fragment = CycleFragment()
            val args = Bundle()
            args.putInt(ARG_NUMBER, number)
            fragment.arguments = args
            return fragment
        }
    }
}
