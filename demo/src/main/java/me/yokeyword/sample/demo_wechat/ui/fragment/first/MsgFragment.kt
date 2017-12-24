package me.yokeyword.sample.demo_wechat.ui.fragment.first

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText

import me.yokeyword.sample.R
import me.yokeyword.sample.demo_wechat.adapter.MsgAdapter
import me.yokeyword.sample.demo_wechat.base.BaseBackFragment
import me.yokeyword.sample.demo_wechat.entity.Chat
import me.yokeyword.sample.demo_wechat.entity.Msg

/**
 * Created by YoKeyword on 16/6/30.
 */
class MsgFragment : BaseBackFragment() {

    private var mToolbar: Toolbar? = null
    private var mRecy: RecyclerView? = null
    private var mEtSend: EditText? = null
    private var mBtnSend: Button? = null

    private var mChat: Chat? = null
    private var mAdapter: MsgAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mChat = arguments!!.getParcelable(ARG_MSG)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.wechat_fragment_tab_first_msg, container, false)
        initView(view)
        return attachToSwipeBack(view)
    }

    private fun initView(view: View) {
        mToolbar = view.findViewById<View>(R.id.toolbar) as Toolbar
        mBtnSend = view.findViewById<View>(R.id.btn_send) as Button
        mEtSend = view.findViewById<View>(R.id.et_send) as EditText
        mRecy = view.findViewById<View>(R.id.recy) as RecyclerView

        mToolbar!!.title = mChat!!.name
        initToolbarNav(mToolbar!!)
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        // 入场动画结束后执行  优化,防动画卡顿

        _mActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        mRecy!!.layoutManager = LinearLayoutManager(_mActivity)
        mRecy!!.setHasFixedSize(true)
        mAdapter = MsgAdapter(_mActivity)
        mRecy!!.adapter = mAdapter

        mBtnSend!!.setOnClickListener(View.OnClickListener {
            val str = mEtSend!!.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(str)) return@OnClickListener

            mAdapter!!.addMsg(Msg(str))
            mEtSend!!.setText("")
            mRecy!!.scrollToPosition(mAdapter!!.itemCount - 1)
        })

        mAdapter!!.addMsg(Msg(mChat!!.message))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mActivity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        hideSoftInput()
    }

    companion object {
        private val ARG_MSG = "arg_msg"

        fun newInstance(msg: Chat): MsgFragment {

            val args = Bundle()
            args.putParcelable(ARG_MSG, msg)
            val fragment = MsgFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
