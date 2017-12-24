package me.yokeyword.sample.demo_wechat.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import me.yokeyword.sample.R
import me.yokeyword.sample.demo_wechat.entity.Chat
import me.yokeyword.sample.demo_wechat.listener.OnItemClickListener
import java.util.*

/**
 * Created by YoKeyword on 16/6/30.
 */
class ChatAdapter(context: Context) : RecyclerView.Adapter<ChatAdapter.VH>() {
    private val mInflater: LayoutInflater
    private val mItems = ArrayList<Chat>()

    private var mClickListener: OnItemClickListener? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

    fun setDatas(beans: List<Chat>) {
        mItems.clear()
        mItems.addAll(beans)
        notifyDataSetChanged()
    }

    fun refreshMsg(bean: Chat) {
        val index = mItems.indexOf(bean)
        if (index < 0) return

        notifyItemChanged(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = mInflater.inflate(R.layout.item_wechat_chat, parent, false)
        val holder = VH(view)
        holder.itemView.setOnClickListener { v ->
            if (mClickListener != null) {
                mClickListener!!.onItemClick(holder.adapterPosition, v, holder)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = mItems[position]

        holder.tvName.text = item.name
        holder.tvMsg.text = item.message
        holder.tvTime.setText(R.string.time)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mClickListener = listener
    }

    fun getMsg(position: Int): Chat {
        return mItems[position]
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public val imgAvatar: ImageView
        public val tvName: TextView
        public val tvMsg: TextView
        public val tvTime: TextView

        init {
            imgAvatar = itemView.findViewById<View>(R.id.img_avatar) as ImageView
            tvName = itemView.findViewById<View>(R.id.tv_name) as TextView
            tvMsg = itemView.findViewById<View>(R.id.tv_msg) as TextView
            tvTime = itemView.findViewById<View>(R.id.tv_time) as TextView
        }
    }
}
