package lidu.me.creditcardguide

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Animatable
import android.net.Uri
import android.view.View
import android.view.ViewManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.image.ImageInfo
import lidu.me.creditcardguide.widget.*
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.custom.ankoView
import java.lang.ref.SoftReference

/**
 * Created by lidu on 2018/1/30.
 */
object CommonUI {
    inline fun ViewManager.homeTabView(theme: Int = 0, callback: HomeTabView.HomeTabSelectCallback,
                                       iconRes: List<Int>, selectIconRes: List<Int>,
                                       textRes: List<Int>, init: HomeTabView.() -> Unit): HomeTabView {
        return ankoView({ HomeTabView(it, callback, iconRes, selectIconRes, textRes) }, theme, { init() })
    }

    inline fun ViewManager.customViewPager(theme: Int = 0, init: CustomViewPager.() -> Unit): CustomViewPager {
        return ankoView({ CustomViewPager(it) }, theme, { init() })
    }

    inline fun ViewManager.pullToRefreshListView(theme: Int = 0, init: PullToRefreshListView.() -> Unit): PullToRefreshListView {
        return ankoView({ PullToRefreshListView(it) }, theme, { init() })
    }

    inline fun ViewManager.pullToRefreshGridView(theme: Int = 0, init: PullToRefreshGridView.() -> Unit): PullToRefreshGridView {
        return ankoView({ PullToRefreshGridView(it) }, theme, { init() })
    }

    inline fun ViewManager.titleLayout(theme: Int = 0) = titleLayout(theme) {}
    inline fun ViewManager.titleLayout(title: String) = titleLayout(0, title) {}
    inline fun ViewManager.titleLayout(theme: Int = 0, title: String = "",
                                       init: (demoView: WhiteTitleBar) -> Unit): View {
        val demoView = WhiteTitleBar(title)
        return ankoView({ demoView.createView(AnkoContext.create(it)) }, theme, { init(demoView) })
    }

    inline fun ViewManager.simpleDraweeView(theme: Int = 0): SimpleDraweeView = simpleDraweeView(theme) {}

    inline fun ViewManager.simpleDraweeView(theme: Int = 0, init: SimpleDraweeView.() -> Unit) = ankoView({ SimpleDraweeView(it) }, theme) { init() }

    inline fun Context.simpleDraweeView(theme: Int = 0): SimpleDraweeView = simpleDraweeView(theme) {}
    inline fun Context.simpleDraweeView(theme: Int = 0, init: SimpleDraweeView.() -> Unit) = ankoView({ SimpleDraweeView(it) }, theme) { init() }

    inline fun Activity.simpleDraweeView(theme: Int = 0): SimpleDraweeView = simpleDraweeView(theme) {}
    inline fun Activity.simpleDraweeView(theme: Int = 0, init: SimpleDraweeView.() -> Unit) = ankoView({ SimpleDraweeView(it) }, theme) { init() }

}


var SimpleDraweeView.imageUrl: String?
    get() = ""
    set(value) {
        val reference = SoftReference<SimpleDraweeView>(this)
        val listener = object : BaseControllerListener<ImageInfo>() {
            override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                super.onFinalImageSet(id, imageInfo, animatable)
                if (imageInfo!!.height != 0 && reference.get() != null) {
                    reference.get()?.aspectRatio = imageInfo.width.toFloat() / imageInfo.height.toFloat()
                }
            }
        }
        val controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(value))
                .setOldController(controller)
                .setControllerListener(listener)
                .build()

        setController(controller)
    }
