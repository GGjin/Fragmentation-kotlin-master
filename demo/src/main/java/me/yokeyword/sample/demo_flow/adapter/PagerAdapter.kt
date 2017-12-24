package me.yokeyword.sample.demo_flow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.yokeyword.sample.R
import me.yokeyword.sample.demo_flow.listener.OnItemClickListener
import java.util.*

/**
 * 发现Discover里的子Fragment  Adapter
 * Created by YoKeyword on 16/2/1.
 */
class PagerAdapter(context: Context) : RecyclerView.Adapter<PagerAdapter.MyViewHolder>() {
    private val mItems = ArrayList<String>()
    private val mInflater: LayoutInflater

    private var mClickListener: OnItemClickListener? = null

    init {
        this.mInflater = LayoutInflater.from(context)
    }

    fun setDatas(items: List<String>) {
        mItems.clear()
        mItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = mInflater.inflate(R.layout.item_pager, parent, false)
        val holder = MyViewHolder(view)
        holder.itemView.setOnClickListener { v ->
            val position = holder.adapterPosition
            if (mClickListener != null) {
                mClickListener!!.onItemClick(position, v)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = mItems[position]
        holder.tvTitle.text = item
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView

        init {
            tvTitle = itemView.findViewById<View>(R.id.tv_title) as TextView
        }
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.mClickListener = itemClickListener
    }
}
