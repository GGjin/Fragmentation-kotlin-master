package me.yokeyword.sample.demo_flow

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.FragmentAnimator
import me.yokeyword.sample.R
import me.yokeyword.sample.demo_flow.base.BaseMainFragment
import me.yokeyword.sample.demo_flow.base.MySupportActivity
import me.yokeyword.sample.demo_flow.ui.fragment.account.LoginFragment
import me.yokeyword.sample.demo_flow.ui.fragment.discover.DiscoverFragment
import me.yokeyword.sample.demo_flow.ui.fragment.home.HomeFragment
import me.yokeyword.sample.demo_flow.ui.fragment.shop.ShopFragment

/**
 * 流程式demo  tip: 多使用右上角的"查看栈视图"
 * Created by YoKeyword on 16/1/29.
 */
class MainActivity : MySupportActivity(), NavigationView.OnNavigationItemSelectedListener, BaseMainFragment.OnFragmentOpenDrawerListener, LoginFragment.OnLoginSuccessListener {
    private var TOUCH_TIME: Long = 0

    private var mDrawer: DrawerLayout? = null
    private var mNavigationView: NavigationView? = null
    private var mTvName: TextView? = null   // NavigationView上的名字
    private var mImgNav: ImageView? = null  // NavigationView上的头像

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = findFragment<HomeFragment>(HomeFragment::class.java)
        if (fragment == null) {
            loadRootFragment(R.id.fl_container, HomeFragment.newInstance())
        }
        initView()
    }

    /**
     * 设置动画，也可以使用setFragmentAnimator()设置
     */
    override fun onCreateFragmentAnimator(): FragmentAnimator {
        // 设置默认Fragment动画  默认竖向(和安卓5.0以上的动画相同)
        return super.onCreateFragmentAnimator()
        // 设置横向(和安卓4.x动画相同)
        //        return new DefaultHorizontalAnimator();
        // 设置自定义动画
        //        return new FragmentAnimator(enter,exit,popEnter,popExit);
    }

    private fun initView() {
        mDrawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        //        mDrawer.setDrawerListener(toggle);
        toggle.syncState()

        mNavigationView = findViewById<View>(R.id.nav_view) as NavigationView
        mNavigationView!!.setNavigationItemSelectedListener(this)
        mNavigationView!!.setCheckedItem(R.id.nav_home)

        val llNavHeader = mNavigationView!!.getHeaderView(0) as LinearLayout
        mTvName = llNavHeader.findViewById<View>(R.id.tv_name) as TextView
        mImgNav = llNavHeader.findViewById<View>(R.id.img_nav) as ImageView
        llNavHeader.setOnClickListener {
            mDrawer!!.closeDrawer(GravityCompat.START)
            mDrawer!!.postDelayed({ goLogin() }, 250)
        }
    }

    override fun onBackPressedSupport() {
        if (mDrawer!!.isDrawerOpen(GravityCompat.START)) {
            mDrawer!!.closeDrawer(GravityCompat.START)
        } else {
            val topFragment = topFragment

            // 主页的Fragment
            if (topFragment is BaseMainFragment) {
                mNavigationView!!.setCheckedItem(R.id.nav_home)
            }

            if (supportFragmentManager.backStackEntryCount > 1) {
                pop()
            } else {
                if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                    finish()
                } else {
                    TOUCH_TIME = System.currentTimeMillis()
                    Toast.makeText(this, R.string.press_again_exit, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * 打开抽屉
     */
    override fun onOpenDrawer() {
        if (!mDrawer!!.isDrawerOpen(GravityCompat.START)) {
            mDrawer!!.openDrawer(GravityCompat.START)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mDrawer!!.closeDrawer(GravityCompat.START)

        mDrawer!!.postDelayed({
            val id = item.itemId

            val topFragment = topFragment

            if (id == R.id.nav_home) {

                val fragment = findFragment<HomeFragment>(HomeFragment::class.java)
                val newBundle = Bundle()
                newBundle.putString("from", "From:" + topFragment.javaClass.getSimpleName())
                fragment.putNewBundle(newBundle)

                start(fragment, SupportFragment.SINGLETASK)
            } else if (id == R.id.nav_discover) {
                val fragment = findFragment<DiscoverFragment>(DiscoverFragment::class.java)
                if (fragment == null) {
                    popTo(HomeFragment::class.java, false, { start(DiscoverFragment.newInstance()) })
                } else {
                    // 如果已经在栈内,则以SingleTask模式start
                    start(fragment, SupportFragment.SINGLETASK)
                }
            } else if (id == R.id.nav_msg) {
                val fragment = findFragment<ShopFragment>(ShopFragment::class.java)
                if (fragment == null) {
                    popTo(HomeFragment::class.java, false, {
                        start(ShopFragment.newInstance())
                        // 设置pop动画为： popExit， 配合start()，视觉上动画会很合理（类似startWithPop()的动画）
                    }, fragmentAnimator.popExit)
                } else {
                    // 如果已经在栈内,则以SingleTask模式start,也可以用popTo
                    //                        start(fragment, SupportFragment.SINGLETASK);
                    popTo(ShopFragment::class.java, false)
                }
            } else if (id == R.id.nav_login) {
                goLogin()
            } else if (id == R.id.nav_swipe_back) {
                startActivity(Intent(this@MainActivity, SwipeBackSampleActivity::class.java))
            }
        }, 300)

        return true
    }

    private fun goLogin() {
        start(LoginFragment.newInstance())
    }

    override fun onLoginSuccess(account: String) {
        mTvName!!.text = account
        mImgNav!!.setImageResource(R.drawable.ic_account_circle_white_48dp)
        Toast.makeText(this, R.string.sign_in_success, Toast.LENGTH_SHORT).show()
    }

    companion object {
        val TAG = MainActivity::class.java!!.getSimpleName()

        // 再点一次退出程序时间设置
        private val WAIT_TIME = 2000L
    }
}
