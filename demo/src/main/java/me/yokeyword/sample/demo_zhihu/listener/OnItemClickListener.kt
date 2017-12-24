package me.yokeyword.sample.demo_zhihu.listener

import android.support.v7.widget.RecyclerView
import android.view.View

interface OnItemClickListener {
    fun onItemClick(position: Int, view: View, vh: RecyclerView.ViewHolder)
}