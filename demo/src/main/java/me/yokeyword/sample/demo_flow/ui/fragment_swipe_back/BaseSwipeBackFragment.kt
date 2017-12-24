package me.yokeyword.sample.demo_flow.ui.fragment_swipe_back

import android.support.v7.widget.Toolbar
import android.view.View

import me.yokeyword.fragmentation_swipeback.SwipeBackFragment
import me.yokeyword.sample.R

/**
 * Created by YoKeyword on 16/4/21.
 */
open class BaseSwipeBackFragment : SwipeBackFragment() {

    internal fun _initToolbar(toolbar: Toolbar) {
        toolbar.title = "SwipeBackActivity's Fragment"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { _mActivity.onBackPressed() }
    }
}
