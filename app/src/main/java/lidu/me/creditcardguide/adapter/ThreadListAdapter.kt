package lidu.me.creditcardguide.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import lidu.me.creditcardguide.CommonUI.simpleDraweeView
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.Utils
import lidu.me.creditcardguide.imageUrl
import lidu.me.creditcardguide.model.ThreadItemModel
import lidu.me.creditcardguide.ui.AnkoViewHolder
import lidu.me.creditcardguide.ui.ImagePreviewActivity
import lidu.me.creditcardguide.ui.ThreadDetailActivity
import org.jetbrains.anko.*
import kotlin.properties.Delegates

/**
 * Created by lidu on 2018/1/31.
 */
class ThreadListAdapter(ctx: Context, data: List<ThreadItemModel>) :
        AnkoListAdapter<ThreadItemModel, ThreadListAdapter.ThreadListViewHolder>(ctx, data, { ThreadListViewHolder(it) }) {

    class ThreadListViewHolder(val ctx: Context) : AnkoViewHolder<ThreadItemModel>(ctx) {

        private var authorFace: SimpleDraweeView by Delegates.notNull()
        private var authorName: TextView by Delegates.notNull()
        private var bankName: TextView by Delegates.notNull()
        private var threadTitle: TextView by Delegates.notNull()
        private var threadDesc: TextView by Delegates.notNull()
        private var firstImage: SimpleDraweeView by Delegates.notNull()
        private var secondImage: SimpleDraweeView by Delegates.notNull()
        private var thirdImage: SimpleDraweeView by Delegates.notNull()
        private var threadImageContainer: LinearLayout by Delegates.notNull()
        private var timeStamp: TextView by Delegates.notNull()
        private var commentedNum: TextView by Delegates.notNull()

        override fun createView(ui: AnkoContext<Context>): View = with(ui) {
            return verticalLayout {
                setPadding(dip(15), dip(15), dip(15), dip(15))

                relativeLayout {
                    authorFace = simpleDraweeView {
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams {
                        height = matchParent
                        width = dip(30)
                        topPadding = dip(5)
                        bottomPadding = dip(5)
                        alignParentLeft()
                    }

                    authorName = textView {
                        gravity = Gravity.CENTER_VERTICAL
                        textColor = resources.getColor(R.color.customYellow)
                    }.lparams {
                        leftMargin = dip(40)
                    }

                    bankName = textView {
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams {
                        alignParentRight()
                    }

                }.lparams {
                    width = matchParent
                    height = dip(40)
                }

                threadTitle = textView {
                    textColor = resources.getColor(R.color.black_33)
                    textSize = 18f
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                threadDesc = textView {
                    textSize = 16f
                    maxLines = 2
                    setLineSpacing(0f, 1.2f)
                    ellipsize = TextUtils.TruncateAt.END
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    topMargin = dip(10)
                    bottomMargin = dip(10)
                }

                threadImageContainer = linearLayout {
                    orientation = LinearLayout.HORIZONTAL

                    firstImage = simpleDraweeView {

                    }.lparams {
                        height = (Utils.getScreenWidth(ctx) - dip(4 * 15)) / 3
                        width = height
                        rightMargin = dip(15)
                    }

                    secondImage = simpleDraweeView {

                    }.lparams {
                        height = (Utils.getScreenWidth(ctx) - dip(4 * 15)) / 3
                        width = height
                        rightMargin = dip(15)
                    }

                    thirdImage = simpleDraweeView {

                    }.lparams {
                        height = (Utils.getScreenWidth(ctx) - dip(4 * 15)) / 3
                        width = height
                    }

                }

                relativeLayout {
                    timeStamp = textView {

                    }.lparams {
                        alignParentLeft()
                    }

                    commentedNum = textView {

                    }.lparams {
                        alignParentRight()
                    }
                }

            }
        }

        override fun bindData(data: ThreadItemModel) {
            authorFace.imageUrl = data.face
            authorName.text = data.authorName
            bankName.text = data.fname
            threadTitle.text = data.title
            threadDesc.text = data.intro

            threadTitle.setOnClickListener {
                toThreadDetailActivity(data)
            }

            threadDesc.setOnClickListener {
                toThreadDetailActivity(data)
            }

            when (data.imgList?.size) {
                3 -> {
                    threadImageContainer.visibility = View.VISIBLE
                    firstImage.imageUrl = data.imgList[0]
                    secondImage.imageUrl = data.imgList[1]
                    thirdImage.imageUrl = data.imgList[2]

                    firstImage.setOnClickListener {
                        ctx.startActivity<ImagePreviewActivity>(
                                ImagePreviewActivity.INTENT_KEY_IMAGE_URL to data.imgList[0])
                    }

                    secondImage.setOnClickListener {
                        ctx.startActivity<ImagePreviewActivity>(
                                ImagePreviewActivity.INTENT_KEY_IMAGE_URL to data.imgList[1])
                    }

                    thirdImage.setOnClickListener {
                        ctx.startActivity<ImagePreviewActivity>(
                                ImagePreviewActivity.INTENT_KEY_IMAGE_URL to data.imgList[2])
                    }
                }
                2 -> {
                    threadImageContainer.visibility = View.VISIBLE
                    firstImage.imageUrl = data.imgList[0]
                    secondImage.imageUrl = data.imgList[1]

                    firstImage.setOnClickListener {
                        ctx.startActivity<ImagePreviewActivity>(
                                ImagePreviewActivity.INTENT_KEY_IMAGE_URL to data.imgList[0])
                    }

                    secondImage.setOnClickListener {
                        ctx.startActivity<ImagePreviewActivity>(
                                ImagePreviewActivity.INTENT_KEY_IMAGE_URL to data.imgList[1])
                    }
                }
                1 -> {
                    threadImageContainer.visibility = View.VISIBLE
                    firstImage.imageUrl = data.imgList[0]

                    firstImage.setOnClickListener {
                        ctx.startActivity<ImagePreviewActivity>(
                                ImagePreviewActivity.INTENT_KEY_IMAGE_URL to data.imgList[0])
                    }

                }
                else -> {
                    threadImageContainer.visibility = View.GONE
                }
            }
        }

        private fun toThreadDetailActivity(data: ThreadItemModel) {
            try {
                val tid: String = getTid(data.targetUrl)!!
                val intent = Intent(ctx, ThreadDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putStringArrayList(ThreadDetailActivity.INTENT_KEY_IMAGE_LIST, data.imgList)
                bundle.putString(ThreadDetailActivity.INTENT_KEY_TID, tid)
                intent.putExtra(ThreadDetailActivity.INTENT_KEY_BUNDLE, bundle)
                ctx.startActivity(intent)
            } catch (e: Exception) {

            }

        }

        private fun getTid(url: String): String? {
            val TID_REGEX = """wak://app/kashen/bbs/forum/normal/details[/]?.*[\?&]tid=([^#&]+).*""".toRegex()
            return TID_REGEX.matchEntire(url)?.groups?.get(1)?.value
        }
    }
}