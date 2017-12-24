package me.yokeyword.sample.demo_flow.base

import android.content.Context
import android.support.v7.widget.Toolbar
import android.view.View

import me.yokeyword.sample.R


/**
 * Created by YoKeyword on 16/2/3.
 */
open class BaseMainFragment : MySupportFragment() {

    protected var mOpenDraweListener: OnFragmentOpenDrawerListener? = null

    @JvmOverloads protected fun initToolbarNav(toolbar: Toolbar, isHome: Boolean = false) {
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp)
        toolbar.setNavigationOnClickListener {
            if (mOpenDraweListener != null) {
                mOpenDraweListener!!.onOpenDrawer()
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentOpenDrawerListener) {
            mOpenDraweListener = context
        } else {
            //            throw new RuntimeException(context.toString()
            //                    + " must implement OnFragmentOpenDrawerListener");
        }
    }

    override fun onDetach() {
        super.onDetach()
        mOpenDraweListener = null
    }

    interface OnFragmentOpenDrawerListener {
        fun onOpenDrawer()
    }
}
