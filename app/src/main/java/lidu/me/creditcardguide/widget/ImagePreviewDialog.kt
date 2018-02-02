package lidu.me.creditcardguide.widget

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.facebook.drawee.view.SimpleDraweeView
import lidu.me.creditcardguide.CommonUI.simpleDraweeView
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.imageUrl
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/2/1.
 */
class ImagePreviewDialog(val ctx: Context, val url: String) : Dialog(ctx), AnkoComponent<Context> {

    private var rootView: View
    private lateinit var previewImage: SimpleDraweeView

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            frameLayout {
                backgroundColor = resources.getColor(R.color.customWhite)

                simpleDraweeView {
                    previewImage = this@simpleDraweeView
                    previewImage.imageUrl = url
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }
            }
        }
    }

    init {
        rootView = createView(AnkoContext.Companion.create(ctx))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(rootView)
        setWindowStyle()
    }

    private fun setWindowStyle() {
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}