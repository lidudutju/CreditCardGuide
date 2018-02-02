package lidu.me.kotlindemo

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewManager
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import lidu.me.kotlindemo.adapter.ForumListAdapter
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.internals.AnkoInternals
import ru.gildor.coroutines.retrofit.ErrorResult
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.getOrNull

/**
 * Created by lidu on 2018/1/9.
 */
class Utils {

    fun ImageView.setImageURL(context: Context, url: String?) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(this)
    }


    companion object {

        private val BRAND_XIAOMI = "xiaomi"
        private val BRAND_MEIZU = "meizu"
        private val BRAND_HUAWEI = "HUAWEI"
        //从哪个版本的miui  flyme开始支持
        private val MIN_SUPPORT_VERSION = Build.VERSION_CODES.LOLLIPOP

        fun getScreenWidth(ctx: Context): Int {
            val metrics = ctx.resources.displayMetrics
            return metrics.widthPixels
        }

        fun tintSystemBar(activity: Activity, color: Int) {
            try {
                if (Build.VERSION.SDK_INT >= MIN_SUPPORT_VERSION) {
                    val window = activity.window

                    if (BRAND_XIAOMI.equals(android.os.Build.MANUFACTURER, ignoreCase = true)) {
                        // 小米5.0+进行背景和图标染色
                        NormalSetStatusBar(activity, color)
                    } else if (BRAND_MEIZU.equals(Build.MANUFACTURER, ignoreCase = true)) {
                        // 魅族5.0+进行背景和图标染色
                        NormalSetStatusBar(activity, color)
                    } else {
                        // 其他手机，从6.0+进行背景和图标染色
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            NormalSetStatusBar(activity, color)
                        }
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        private fun NormalSetStatusBar(activity: Activity, color: Int) {
            try {
                val window = activity.window
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = color
            } catch (e: Exception) {

            }

        }
    }

}

inline fun <T : Any> Result<T>.ifFailed(handler: () -> Unit): Result<T> {
    if (this is ErrorResult) handler()
    return this
}

inline fun <T : Any> Result<T>.ifSucceeded(handler: (data: T) -> Unit): Result<T> {
    (this as? Result.Ok)?.getOrNull()?.let { handler(it) }
    return this
}

inline fun <T : Any> Result<T>.ifError(handler: (code: Int) -> Unit): Result<T> {
    (this as? Result.Error)?.response?.code()?.let { handler(it) }
    return this
}

inline fun <T : Any> Result<T>.ifException(handler: (exception: Throwable) -> Unit): Result<T> {
    (this as? Result.Exception)?.exception?.let { handler(it) }
    return this
}

interface AnkoViewProvider {
    val view: View
}

inline fun <T> ViewManager.ankoComponent(factory: (ctx: Context) -> T, theme: Int, init: T.() -> Unit): View
        where T : AnkoComponent<Context>, T : AnkoViewProvider {
    val ctx = AnkoInternals.wrapContextIfNeeded(AnkoInternals.getContext(this), theme)
    val ui = factory(ctx)
    ui.init()
    AnkoInternals.addView(this, ui.view)
    return ui.view
}

inline fun ViewManager.forumListItemView(theme: Int = 0) = forumListItemView(theme) {}
inline fun ViewManager.forumListItemView(theme: Int = 0, init: ForumListAdapter.ForumListViewHolder.() -> Unit): View =
        ankoComponent({ ForumListAdapter.ForumListViewHolder(it) }, theme, init)