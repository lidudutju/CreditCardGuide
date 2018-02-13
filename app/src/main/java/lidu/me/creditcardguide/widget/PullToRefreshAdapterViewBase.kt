package lidu.me.creditcardguide.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.AbsListView

/**
 * Created by lidu on 2018/2/12.
 */
abstract class PullToRefreshAdapterViewBase<T : AbsListView>(
        ctx: Context,
        attrs: AttributeSet?,
        defStyle: Int
) : PullToRefreshBase<T>(ctx, attrs, defStyle) {

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context) : this(ctx, null)


    override fun isReadyForPullStart(): Boolean {
        val adapter = refreshableView.adapter

        if (adapter == null || adapter.isEmpty) {
            return true
        } else {
            if (refreshableView.firstVisiblePosition == 0) {
                val child = refreshableView.getChildAt(0)
                if (child != null) {
                    return child.top >= refreshableView.top
                }
            }
        }
        return false
    }

    override fun isReadyForPullEnd(): Boolean {
        val adapter = refreshableView.adapter

        if (adapter == null || adapter.isEmpty) {
            return true
        } else {
            if (refreshableView.lastVisiblePosition >= adapter.count - 1) {
                val childIndex = refreshableView.lastVisiblePosition - refreshableView.firstVisiblePosition
                val child = refreshableView.getChildAt(childIndex)
                if (child != null) {
                    return child.bottom <= refreshableView.bottom
                }
            }
        }
        return false
    }

}