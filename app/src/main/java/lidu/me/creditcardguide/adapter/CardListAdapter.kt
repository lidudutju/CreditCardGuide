package lidu.me.creditcardguide.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import lidu.me.creditcardguide.CommonUI.simpleDraweeView
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.imageUrl
import lidu.me.creditcardguide.model.CardListItemModel
import lidu.me.creditcardguide.ui.AnkoViewHolder
import lidu.me.creditcardguide.ui.CardDetailActivity
import org.jetbrains.anko.*
import kotlin.properties.Delegates

/**
 * Created by lidu on 2018/1/9.
 *
 */
class CardListAdapter(context: Context, data: List<CardListItemModel>) :
        AnkoListAdapter<CardListItemModel, CardListAdapter.CardListViewHolder>(context, data, { CardListViewHolder(it) }) {

    class CardListViewHolder(val ctx: Context) : AnkoViewHolder<CardListItemModel>(ctx) {

        private var name: TextView by Delegates.notNull()
        private var desc: TextView by Delegates.notNull()
        private var image: SimpleDraweeView by Delegates.notNull()
        private var level: TextView by Delegates.notNull()
        private lateinit var container: LinearLayout

        private lateinit var cardId: String

        override fun bindData(data: CardListItemModel) {
            cardId = data.cardId

            name.text = data.cardName
            desc.text = data.cardDesc
            image.imageUrl = data.cardLogo
            level.text = data.cardLevel
            container.setOnClickListener {
                ctx.startActivity<CardDetailActivity>(CardDetailActivity.INTENT_KEY_CARD_ID to cardId)
            }

        }

        override fun createView(ui: AnkoContext<Context>): View {
            return with(ui) {
                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    container = this

                    image = simpleDraweeView {
                    }.lparams(width = dip(80 * 1.65f),
                            height = dip(80)) {
                        margin = dip(10)
                    }

                    verticalLayout {

                        name = textView {
                            textSize = 16f
                            textColor = Color.BLACK
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }

                        desc = textView {
                            textSize = 13f
                            textColor = Color.BLACK
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }

                        level = textView {
                            textSize = 13f
                            textColor = resources.getColor(R.color.customRed)
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }

                    }.lparams(width = 0, height = wrapContent) {
                        weight = 1f
                        margin = dip(10)
                    }
                }
            }
        }

    }
}