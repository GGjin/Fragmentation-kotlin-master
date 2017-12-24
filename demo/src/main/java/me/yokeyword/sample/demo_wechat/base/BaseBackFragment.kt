package me.yokeyword.sample.demo_wechat.base

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View

import me.yokeyword.fragmentation_swipeback.SwipeBackFragment
import me.yokeyword.sample.R

/**
 * Created by YoKeyword on 16/2/7.
 */
open class BaseBackFragment : SwipeBackFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setParallaxOffset(0.5f)
    }

    protected fun initToolbarNav(toolbar: Toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { _mActivity.onBackPressed() }
    }

    companion object {
        private val TAG = "Fragmentation"
    }
}
