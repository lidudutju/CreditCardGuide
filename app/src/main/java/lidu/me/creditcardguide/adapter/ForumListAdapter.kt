package lidu.me.creditcardguide.adapter

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import lidu.me.creditcardguide.CommonUI.simpleDraweeView
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.Utils
import lidu.me.creditcardguide.imageUrl
import lidu.me.creditcardguide.model.ForumItemModel
import lidu.me.creditcardguide.ui.AnkoViewHolder
import lidu.me.creditcardguide.ui.ThreadListActivity
import org.jetbrains.anko.*
import kotlin.properties.Delegates

/**
 * Created by lidu on 2018/1/30.
 */
class ForumListAdapter(ctx: Context, data: List<ForumItemModel>) :
        AnkoListAdapter<ForumItemModel, ForumListAdapter.ForumListViewHolder>(ctx, data, { ForumListViewHolder(it) }) {

    class ForumListViewHolder(private val ctx: Context) : AnkoViewHolder<ForumItemModel>(ctx) {

        private var name: TextView by Delegates.notNull()
        private var image: SimpleDraweeView by Delegates.notNull()
        private var threads: TextView by Delegates.notNull()

        override fun bindData(data: ForumItemModel) {
            name.text = data.name
            image.imageUrl = data.icon
            threads.text = "主题: " + data.threads

            view.setOnClickListener {
                ctx.startActivity<ThreadListActivity>(ThreadListActivity.INTENT_KEY_FID to data.fid)
            }
        }

        override fun createView(ui: AnkoContext<Context>): View = with(ui) {
            return frameLayout {
                backgroundColor = resources.getColor(R.color.default_divider)

                verticalLayout {
                    backgroundColor = resources.getColor(R.color.customWhite)
                    image = simpleDraweeView {
                        gravity = Gravity.CENTER
                    }.lparams {
                        margin = dip(10)
                        width = dip(45)
                        height = dip(45)
                    }

                    name = textView {
                        textSize = 16f
                        textColor = resources.getColor(R.color.black_33)
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }


                    threads = textView {
                        textSize = 12f
                        textColor = resources.getColor(R.color.customRed)
                        gravity = Gravity.CENTER
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
                }.lparams {
                    width = matchParent
                    height = Utils.getScreenWidth(ctx) / 3
                    rightPadding = 1
                    bottomPadding = 1
                }
            }
        }

    }
}