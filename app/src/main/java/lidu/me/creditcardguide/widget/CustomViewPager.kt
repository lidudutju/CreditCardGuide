package lidu.me.creditcardguide.widget

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.view.MotionEvent

/**
 * Created by lidu on 2018/2/1.
 */
class CustomViewPager(ctx: Context) : ViewPager(ctx) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}