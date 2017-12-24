package me.yokeyword.sample.demo_wechat.base

import android.widget.Toast

import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.sample.R

/**
 * 懒加载
 * Created by YoKeyword on 16/6/5.
 */
abstract class BaseMainFragment : SupportFragment() {
    private var TOUCH_TIME: Long = 0

    /**
     * 处理回退事件
     *
     * @return
     */
    override fun onBackPressedSupport(): Boolean {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish()
        } else {
            TOUCH_TIME = System.currentTimeMillis()
            Toast.makeText(_mActivity, R.string.press_again_exit, Toast.LENGTH_SHORT).show()
        }
        return true
    }

    companion object {
        // 再点一次退出程序时间设置
        private val WAIT_TIME = 2000L
    }
}
