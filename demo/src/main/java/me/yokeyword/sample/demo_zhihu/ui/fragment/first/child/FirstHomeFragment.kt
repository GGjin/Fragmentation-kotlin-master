package me.yokeyword.sample.demo_zhihu.ui.fragment.first.child

import android.os.Build
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.transition.Fade
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import me.yokeyword.eventbusactivityscope.EventBusActivityScope
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.sample.R
import me.yokeyword.sample.demo_zhihu.MainActivity
import me.yokeyword.sample.demo_zhihu.adapter.FirstHomeAdapter
import me.yokeyword.sample.demo_zhihu.entity.Article
import me.yokeyword.sample.demo_zhihu.event.TabSelectedEvent
import me.yokeyword.sample.demo_zhihu.helper.DetailTransition
import me.yokeyword.sample.demo_zhihu.listener.OnItemClickListener
import org.greenrobot.eventbus.Subscribe
import java.util.*

/**
 * Created by YoKeyword on 16/6/5.
 */
class FirstHomeFragment : SupportFragment(), SwipeRefreshLayout.OnRefreshListener {
    private var mToolbar: Toolbar? = null
    private var mRecy: RecyclerView? = null
    private var mRefreshLayout: SwipeRefreshLayout? = null
    private var mFab: FloatingActionButton? = null

    private var mAdapter: FirstHomeAdapter? = null

    private var mInAtTop = true
    private var mScrollTotal: Int = 0

    private val mTitles = arrayOf("Use imagery to express a distinctive voice and exemplify creative excellence.", "An image that tells a story is infinitely more interesting and informative.", "The most powerful iconic images consist of a few meaningful elements, with minimal distractions.", "Properly contextualized concepts convey your message and brand more effectively.", "Have an iconic point of focus in your imagery. Focus ranges from a single entity to an overarching composition.")

    private val mImgRes = intArrayOf(R.drawable.bg_first, R.drawable.bg_second, R.drawable.bg_third, R.drawable.bg_fourth, R.drawable.bg_fifth)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.zhihu_fragment_first_home, container, false)
        EventBusActivityScope.getDefault(_mActivity).register(this)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        mToolbar = view.findViewById<View>(R.id.toolbar) as Toolbar
        mRecy = view.findViewById<View>(R.id.recy) as RecyclerView
        mRefreshLayout = view.findViewById<View>(R.id.refresh_layout) as SwipeRefreshLayout
        mFab = view.findViewById<View>(R.id.fab) as FloatingActionButton

        mToolbar!!.setTitle(R.string.home)

        mRefreshLayout!!.setColorSchemeResources(R.color.colorPrimary)
        mRefreshLayout!!.setOnRefreshListener(this)

        mAdapter = FirstHomeAdapter(_mActivity)
        val manager = LinearLayoutManager(_mActivity)
        mRecy!!.layoutManager = manager
        mRecy!!.adapter = mAdapter


        mAdapter!!.setOnItemClickListener(object :OnItemClickListener{
            override fun onItemClick(position: Int, view: View, vh: RecyclerView.ViewHolder) {
                val fragment = FirstDetailFragment.newInstance(mAdapter!!.getItem(position as Int))

                // 这里是使用SharedElement的用例
                // LOLLIPOP(5.0)系统的 SharedElement支持有 系统BUG， 这里判断大于 > LOLLIPOP
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    exitTransition = Fade()
                    fragment.enterTransition = Fade()
                    fragment.sharedElementReturnTransition = DetailTransition()
                    fragment.sharedElementEnterTransition = DetailTransition()

                    // 25.1.0以下的support包,Material过渡动画只有在进栈时有,返回时没有;
                    // 25.1.0+的support包，SharedElement正常
                    extraTransaction()
                            .addSharedElement((vh as FirstHomeAdapter.VH).img, getString(R.string.image_transition))
                            .addSharedElement(vh.tvTitle, "tv")
                            .start(fragment)
                } else {
                    start(fragment)
                }
            }

        })

        // Init Datas
        val articleList = ArrayList<Article>()
        for (i in 0..7) {
            val index = i % 5
            val article = Article(mTitles[index], mImgRes[index])
            articleList.add(article)
        }
        mAdapter!!.setDatas(articleList)

        mRecy!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                mScrollTotal += dy
                if (mScrollTotal <= 0) {
                    mInAtTop = true
                } else {
                    mInAtTop = false
                }
                if (dy > 5) {
                    mFab!!.hide()
                } else if (dy < -5) {
                    mFab!!.show()
                }
            }
        })

        mFab!!.setOnClickListener { Toast.makeText(_mActivity, "Action", Toast.LENGTH_SHORT).show() }
    }

    override fun onRefresh() {
        mRefreshLayout!!.postDelayed({ mRefreshLayout!!.isRefreshing = false }, 2000)
    }

    private fun scrollToTop() {
        mRecy!!.smoothScrollToPosition(0)
    }

    /**
     * 选择tab事件
     */
    @Subscribe
    fun onTabSelectedEvent(event: TabSelectedEvent) {
        if (event.position != MainActivity.FIRST) return

        if (mInAtTop) {
            mRefreshLayout!!.isRefreshing = true
            onRefresh()
        } else {
            scrollToTop()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBusActivityScope.getDefault(_mActivity).unregister(this)
    }

    companion object {


        fun newInstance(): FirstHomeFragment {

            val args = Bundle()

            val fragment = FirstHomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
