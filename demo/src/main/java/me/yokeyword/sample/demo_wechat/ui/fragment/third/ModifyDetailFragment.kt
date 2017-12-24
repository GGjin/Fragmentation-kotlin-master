package me.yokeyword.sample.demo_wechat.ui.fragment.third

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import me.yokeyword.fragmentation.ISupportFragment

import me.yokeyword.sample.R
import me.yokeyword.sample.demo_wechat.base.BaseBackFragment
import me.yokeyword.sample.demo_wechat.ui.fragment.CycleFragment

/**
 * Created by YoKeyword on 16/2/7.
 */
class ModifyDetailFragment : BaseBackFragment() {

    private var mToolbar: Toolbar? = null
    private var mEtModiyTitle: EditText? = null
    private var mBtnModify: Button? = null
    private var mBtnNext: Button? = null

    private var mTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = arguments
        if (args != null) {
            mTitle = args.getString(ARG_TITLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_modify_detail, container, false)
        initView(view)
        return attachToSwipeBack(view)
    }

    private fun initView(view: View) {
        mToolbar = view.findViewById<View>(R.id.toolbar) as Toolbar
        mEtModiyTitle = view.findViewById<View>(R.id.et_modify_title) as EditText
        mBtnModify = view.findViewById<View>(R.id.btn_modify) as Button
        mBtnNext = view.findViewById<View>(R.id.btn_next) as Button

        mToolbar!!.setTitle(R.string.start_result_test)
        initToolbarNav(mToolbar!!)

        mEtModiyTitle!!.setText(mTitle)

        // 显示 软键盘
        //        showSoftInput(mEtModiyTitle);

        mBtnModify!!.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(DetailFragment.KEY_RESULT_TITLE, mEtModiyTitle!!.text.toString())
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)

            Toast.makeText(_mActivity, R.string.modify_success, Toast.LENGTH_SHORT).show()
        }
        mBtnNext!!.setOnClickListener { start(CycleFragment.newInstance(1)) }
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        hideSoftInput()
    }

    companion object {
        private val ARG_TITLE = "arg_title"

        fun newInstance(title: String): ModifyDetailFragment {
            val args = Bundle()
            val fragment = ModifyDetailFragment()
            args.putString(ARG_TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }
}
