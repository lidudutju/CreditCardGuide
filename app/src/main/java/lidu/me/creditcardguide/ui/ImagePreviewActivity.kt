package lidu.me.creditcardguide.ui

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import lidu.me.creditcardguide.CommonUI.simpleDraweeView
import lidu.me.creditcardguide.CommonUI.titleLayout
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.imageUrl
import lidu.me.creditcardguide.widget.WhiteTitleBar
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/2/13.
 */
class ImagePreviewActivity : BaseActivity() {

    private lateinit var url: String
    private lateinit var titleBar: WhiteTitleBar
    private lateinit var title: View

    override fun onCreate(savedInstanceState: Bundle?) {
        url = intent.getStringExtra(INTENT_KEY_IMAGE_URL) ?: ""
        super.onCreate(savedInstanceState)

        titleBar.showBackButton(View.OnClickListener { finish() })
    }

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            verticalLayout {
                backgroundColor = resources.getColor(R.color.customWhite)

                title = titleLayout(title = "预览") {
                    titleBar = it
                }.lparams {
                    leftPadding = dip(15)
                }

                simpleDraweeView {
                    imageUrl = url
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    gravity = Gravity.CENTER
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }
    }

    companion object {
        const val INTENT_KEY_IMAGE_URL = "url"
    }

}