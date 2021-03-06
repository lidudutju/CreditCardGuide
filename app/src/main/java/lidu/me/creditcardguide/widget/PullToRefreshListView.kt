package lidu.me.creditcardguide.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ListView

/**
 * Created by lidu on 2018/2/12.
 */
class PullToRefreshListView(
        ctx: Context,
        attrs: AttributeSet?,
        defStyle: Int
) : PullToRefreshAdapterViewBase<ListView>(ctx, attrs, defStyle) {

    constructor(ctx: Context, attrs: AttributeSet?) : this(ctx, attrs, 0)

    constructor(ctx: Context) : this(ctx, null)

    override fun createRefreshableView(ctx: Context): ListView = ListView(ctx)

    override fun addOnRefreshListener(listener: OnRefreshListener<ListView>) {
        onRefreshListener = listener
    }
}