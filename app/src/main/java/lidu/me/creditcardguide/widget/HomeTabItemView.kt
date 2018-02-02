package lidu.me.creditcardguide.widget

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import lidu.me.creditcardguide.CommonUI.simpleDraweeView
import lidu.me.creditcardguide.R
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/1/30.
 */
class HomeTabItemView(private val ctx: Context,
                      private val iconRes: Int,
                      private val selectIconRes: Int,
                      private val textRes: Int) : AnkoComponent<ViewGroup> {
    private lateinit var icon: SimpleDraweeView
    private lateinit var textView: TextView

    public fun setSelectState(isSelect: Boolean) {
        if (isSelect) {
            icon.imageResource = selectIconRes
            textView.textColor = ctx.resources.getColor(R.color.customRed)
        } else {
            icon.imageResource = iconRes
            textView.textColor = ctx.resources.getColor(R.color.black_33)
        }
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
                simpleDraweeView {
                    icon = this
                    imageResource = iconRes
                    id = R.id.home_tab_icon
                }.lparams {
                    width = dip(20)
                    height = dip(20)
                }

                textView {
                    textView = this
                    text = resources.getString(textRes)
                    id = R.id.home_tab_text
                    textSize = 14f
                    textColor = resources.getColor(R.color.black_33)
                }.lparams {
                    leftMargin = dip(10)
                }
            }
        }
    }
}