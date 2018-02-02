package lidu.me.creditcardguide.ui

import android.content.Context
import android.view.Gravity
import android.view.View
import lidu.me.creditcardguide.CommonUI.titleLayout
import lidu.me.creditcardguide.R
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/1/31.
 */
class FavoriteListFragment : BaseFragment() {

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            verticalLayout {
                backgroundColor = resources.getColor(R.color.customWhite)

                titleLayout(title = "收藏")

                textView {
                    text = "这里空空如也~"
                    textSize = 18f
                    textColor = resources.getColor(R.color.black_33)
                    gravity = Gravity.CENTER
                }.lparams {
                    width = matchParent
                    height = matchParent
                    topMargin = dip(50)
                }

//                listView {
//                    visibility = View.VISIBLE
//                }.lparams(width = wrapContent, height = matchParent) {
//                    topMargin = dip(50)
//                }
            }
        }
    }

    companion object {
        const val TAG = "FavoriteListFragment"
    }

}