package me.yokeyword.sample.demo_zhihu.ui.fragment.first.child

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import me.yokeyword.sample.R
import me.yokeyword.sample.demo_zhihu.base.BaseBackFragment
import me.yokeyword.sample.demo_zhihu.entity.Article
import me.yokeyword.sample.demo_zhihu.ui.fragment.CycleFragment

/**
 * Created by YoKeyword on 16/6/5.
 */
class FirstDetailFragment : BaseBackFragment() {

    private var mArticle: Article? = null

    private var mToolbar: Toolbar? = null
    private var mImgDetail: ImageView? = null
    private var mTvTitle: TextView? = null
    private var mFab: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mArticle = arguments!!.getParcelable(ARG_ITEM)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.zhihu_fragment_first_detail, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        mToolbar = view.findViewById<View>(R.id.toolbar) as Toolbar
        mImgDetail = view.findViewById<View>(R.id.img_detail) as ImageView
        mTvTitle = view.findViewById<View>(R.id.tv_content) as TextView
        mFab = view.findViewById<View>(R.id.fab) as FloatingActionButton

        mToolbar!!.title = ""
        initToolbarNav(mToolbar!!)
        mImgDetail!!.setImageResource(mArticle!!.imgRes)
        mTvTitle!!.text = mArticle!!.title

        mFab!!.setOnClickListener { start(CycleFragment.newInstance(1)) }
    }

    companion object {
        private val ARG_ITEM = "arg_item"

        fun newInstance(article: Article): FirstDetailFragment {

            val args = Bundle()
            args.putParcelable(ARG_ITEM, article)
            val fragment = FirstDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
