package lidu.me.creditcardguide.ui

import android.content.Context
import android.view.View
import lidu.me.creditcardguide.AnkoViewProvider
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext

/**
 * Created by lidu on 2018/1/31.
 */
abstract class AnkoViewHolder<in T>(ctx: Context) : AnkoViewProvider, AnkoComponent<Context> {

    abstract override fun createView(ui: AnkoContext<Context>): View

    abstract fun bindData(data: T)

    override val view: View by lazy {
        createView(AnkoContext.Companion.create(ctx))
    }
}