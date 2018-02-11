package lidu.me.creditcardguide.widget

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import lidu.me.creditcardguide.CommonUI.simpleDraweeView
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.imageUrl
import lidu.me.creditcardguide.model.ThreadDetailModel
import lidu.me.creditcardguide.ui.AnkoViewHolder
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

    private lateinit var firstImage: SimpleDraweeView
    private lateinit var secondImage: SimpleDraweeView
    private lateinit var thirdImage: SimpleDraweeView

    override fun createView(ui: AnkoContext<Context>): View {
        return with(ui) {
            scrollView {
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

                    simpleDraweeView {
                        firstImage = this
                        visibility = View.GONE
                    }

                    simpleDraweeView {
                        secondImage = this
                        visibility = View.GONE
                    }

                    simpleDraweeView {
                        thirdImage = this
                        visibility = View.GONE
                    }

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
    }

    override fun bindData(data: ThreadDetailModel) {
        threadTitle.text = data.subject
        authorPortrait.imageUrl = data.portrait
        authorName.text = data.author
        groupLevel.text = data.groupTitle
        viewsNum.text = "已阅读: " + data.views
        threadDesc.text = data.shareDesc
        if (data.replies == "0") {
            replyNum.visibility = View.GONE
        } else {
            replyNum.visibility = View.VISIBLE
            replyNum.text = "评论(" + data.replies + ")"
        }
    }

    public fun setImageList(list: ArrayList<String>) {
        if (list.isEmpty()) {
            firstImage.visibility = View.GONE
            secondImage.visibility = View.GONE
            thirdImage.visibility = View.GONE
            return
        }

        when (list.size) {
            3 -> {
                firstImage.visibility = View.VISIBLE
                secondImage.visibility = View.VISIBLE
                thirdImage.visibility = View.VISIBLE

                firstImage.imageUrl = list[0]
                secondImage.imageUrl = list[1]
                thirdImage.imageUrl = list[2]

            }
            2 -> {
                firstImage.visibility = View.VISIBLE
                secondImage.visibility = View.VISIBLE
                thirdImage.visibility = View.GONE

                firstImage.imageUrl = list[0]
                secondImage.imageUrl = list[1]

            }
            1 -> {
                firstImage.visibility = View.VISIBLE
                secondImage.visibility = View.GONE
                thirdImage.visibility = View.GONE

                firstImage.imageUrl = list[0]
            }
        }
    }
}