package lidu.me.creditcardguide.widget

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import lidu.me.creditcardguide.R
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/2/2.
 */
//reuse title layout
class WhiteTitleBar(private val title: String) : AnkoComponent<Context> {

    private lateinit var titleTextView: TextView
    private lateinit var backButton: ImageView

    public fun setTitle(title: String) {
        titleTextView.text = title
    }

    public fun showBackButton(listener: View.OnClickListener) {
        backButton.visibility = View.VISIBLE
        backButton.imageResource = R.drawable.ic_back_arrow
        backButton.setOnClickListener(listener)
    }

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            verticalLayout {
                relativeLayout {
                    backgroundColor = resources.getColor(R.color.customWhite)

                    backButton = imageView {
                        visibility = View.GONE
                    }.lparams {
                        alignParentLeft()
                        centerVertically()
                        width = dip(24)
                        height = dip(24)
                    }

                    titleTextView = textView(title) {
                        textColor = resources.getColor(R.color.customRed)
                        textSize = 18f
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = wrapContent
                        height = dip(50)
                        centerInParent()
                    }
                }

                view {
                    backgroundColor = resources.getColor(R.color.default_divider)
                }.lparams {
                    width = matchParent
                    height = 1
                }
            }
        }
    }

}