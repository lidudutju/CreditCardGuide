package lidu.me.creditcardguide.widget

import android.content.Context
import android.view.View

/**
 * Created by lidu on 2018/2/12.
 */
interface IPullToRefresh<T : View> {

    fun createRefreshableView(ctx: Context): T

    fun isRefreshing(): Boolean

    fun onRefreshComplete()

    fun setMode(mode: PullToRefreshBase.Mode)

    fun addOnRefreshListener(listener: PullToRefreshBase.OnRefreshListener<T>)
}