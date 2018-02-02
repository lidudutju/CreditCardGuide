package lidu.me.creditcardguide.adapter

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import lidu.me.creditcardguide.CommonUI.simpleDraweeView
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.imageUrl
import lidu.me.creditcardguide.model.PostListItemModel
import lidu.me.creditcardguide.ui.AnkoViewHolder
import org.jetbrains.anko.*
import kotlin.properties.Delegates

/**
 * Created by lidu on 2018/2/1.
 */
class PostListAdapter(ctx: Context, data: List<PostListItemModel>) :
        AnkoListAdapter<PostListItemModel, PostListAdapter.PostListViewHolder>(ctx, data, { PostListViewHolder(ctx) }) {

    class PostListViewHolder(val ctx: Context) : AnkoViewHolder<PostListItemModel>(ctx) {
        private var authorFace: SimpleDraweeView by Delegates.notNull()
        private var authorName: TextView by Delegates.notNull()
        private var groupTitle: TextView by Delegates.notNull()
        private var position: TextView by Delegates.notNull()
        private var content: TextView by Delegates.notNull()

        override fun createView(ui: AnkoContext<Context>): View {
            return with(ui) {
                verticalLayout {
                    setPadding(dip(15), 0, dip(15), dip(10))

                    relativeLayout {
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL

                            authorFace = simpleDraweeView {

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

                            groupTitle = textView {
                                gravity = Gravity.CENTER_VERTICAL
                                textColor = resources.getColor(R.color.black_33)
                            }.lparams {
                                leftMargin = dip(8)
                            }

                        }.lparams {
                            alignParentLeft()
                        }

                        position = textView {
                            textSize = 12f
                        }.lparams {
                            alignParentRight()
                        }
                    }.lparams {
                        topMargin = dip(10)
                        bottomMargin = dip(10)
                    }

                    content = textView {
                        textSize = 18f
                        textColor = resources.getColor(R.color.black)
                    }

                }
            }
        }

        override fun bindData(data: PostListItemModel) {
            authorFace.imageUrl = data.portrait
            authorName.text = data.author
            groupTitle.text = data.groupTitle
            position.text = data.position
            content.text = data.content
        }

    }
}