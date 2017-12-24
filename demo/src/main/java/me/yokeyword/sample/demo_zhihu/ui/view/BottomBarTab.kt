package me.yokeyword.sample.demo_zhihu.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView

import me.yokeyword.sample.R

/**
 * Created by YoKeyword on 16/6/3.
 */
class BottomBarTab(context: Context, attrs: AttributeSet?, defStyleAttr: Int, icon: Int) : FrameLayout(context, attrs, defStyleAttr) {
    private var mIcon: ImageView? = null
    private var mContext: Context? = null
    var tabPosition = -1
        set(position) {
            field = position
            if (position == 0) {
                isSelected = true
            }
        }

    constructor(context: Context, @DrawableRes icon: Int) : this(context, null, icon) {}

    constructor(context: Context, attrs: AttributeSet?, icon: Int) : this(context, attrs, 0, icon) {}

    init {
        init(context, icon)
    }

    private fun init(context: Context, icon: Int) {
        mContext = context
        val typedArray = context.obtainStyledAttributes(intArrayOf(R.attr.selectableItemBackgroundBorderless))
        val drawable = typedArray.getDrawable(0)
        setBackgroundDrawable(drawable)
        typedArray.recycle()

        mIcon = ImageView(context)
        val size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27f, resources.displayMetrics).toInt()
        val params = FrameLayout.LayoutParams(size, size)
        params.gravity = Gravity.CENTER
        mIcon!!.setImageResource(icon)
        mIcon!!.layoutParams = params
        mIcon!!.setColorFilter(ContextCompat.getColor(context, R.color.tab_unselect))
        addView(mIcon)
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if (selected) {
            mIcon!!.setColorFilter(ContextCompat.getColor(mContext!!, R.color.colorPrimary))
        } else {
            mIcon!!.setColorFilter(ContextCompat.getColor(mContext!!, R.color.tab_unselect))
        }
    }
}
