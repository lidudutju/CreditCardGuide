package lidu.me.kotlindemo.widget

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import lidu.me.kotlindemo.model.ThreadDetailModel
import lidu.me.kotlindemo.ui.AnkoViewHolder
import lidu.me.kotlindemo.CommonUI.simpleDraweeView
import lidu.me.kotlindemo.R
import lidu.me.kotlindemo.imageUrl
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/2/1.
 */
class ThreadHeaderViewHolder(val ctx: Context) : AnkoViewHolder<ThreadDetailModel>(ctx) {

    private lateinit var threadTitle: TextView
    private lateinit var authorName: TextView
    private lateinit var authorPortrait: SimpleDraweeView
    private lateinit var groupLevel: TextView
    private lateinit var viewsNum: TextView
    private lateinit var threadDesc: TextView
    private lateinit var replyNum: TextView


    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            verticalLayout {
                setPadding(dip(15), 0, dip(15), 0)
                backgroundColor = resources.getColor(R.color.customWhite)

                threadTitle = textView {
                    textSize = 20f
                    textColor = resources.getColor(R.color.black)
                }.lparams {
                    topMargin = dip(10)
                }

                relativeLayout {

                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL

                        authorPortrait = simpleDraweeView {

                        }.lparams {
                            width = dip(30)
                            height = dip(30)
                        }

                        authorName = textView {
                            gravity = Gravity.CENTER_VERTICAL
                            textColor = resources.getColor(R.color.customYellow)
                        }.lparams {
                            leftMargin = dip(5)
                        }

                        groupLevel = textView {
                            gravity = Gravity.CENTER_VERTICAL
                            textColor = resources.getColor(R.color.black_33)
                        }.lparams {
                            leftMargin = dip(8)
                        }

                    }.lparams {
                        alignParentLeft()
                    }

                    viewsNum = textView {
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams {
                        alignParentRight()
                    }
                }.lparams {
                    topMargin = dip(10)
                }

                threadDesc = textView {
                    textSize = 18f
                    setLineSpacing(0f, 1.2f)
                    textColor = resources.getColor(R.color.black_33)
                }.lparams {
                    topMargin = dip(15)
                    bottomMargin = dip(30)
                }

//                view {
//                    backgroundColor = resources.getColor(R.color.default_divider)
//                }.lparams {
//                    width = matchParent
//                    height = dip(8)
//                }

                replyNum = textView {
                    textSize = 16f
                    gravity = Gravity.CENTER_VERTICAL
                    textColor = resources.getColor(R.color.black)
                }.lparams {
                    height = dip(50)
                }

            }
        }
    }

    override fun bindData(data: ThreadDetailModel) {
        threadTitle.text = data.subject
        authorPortrait.imageUrl = data.portrait
        authorName.text = data.author
        groupLevel.text = data.groupTitle
        viewsNum.text = "已阅读: " + data.views
        threadDesc.text = data.shareDesc
        replyNum.text = "评论(" + data.replies + ")"
    }
}