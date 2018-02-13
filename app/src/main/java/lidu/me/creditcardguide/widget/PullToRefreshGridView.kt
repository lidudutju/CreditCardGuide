package lidu.me.creditcardguide.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView

/**
 * Created by lidu on 2018/2/13.
 */
class PullToRefreshGridView(
        ctx: Context,
        attrs: AttributeSet?,
        defStyle: Int
) : PullToRefreshAdapterViewBase<GridView>(ctx, attrs, defStyle) {

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context) : this(ctx, null)

    override fun addOnRefreshListener(listener: OnRefreshListener<GridView>) {
        onRefreshListener = listener
    }

    override fun createRefreshableView(ctx: Context): GridView = GridView(ctx)

}