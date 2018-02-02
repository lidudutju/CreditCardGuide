package lidu.me.creditcardguide.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import lidu.me.creditcardguide.CommonUI.simpleDraweeView
import lidu.me.creditcardguide.CommonUI.titleLayout
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.imageUrl
import lidu.me.creditcardguide.model.CardDetailModel
import lidu.me.creditcardguide.network.TaskRepository
import lidu.me.creditcardguide.widget.WhiteTitleBar
import org.jetbrains.anko.*

/**
 * Created by lidu on 2018/1/11.
 */
class CardDetailActivity : BaseActivity() {

    private var cardId: String? = null

    private lateinit var cardImage: SimpleDraweeView
    private lateinit var cardName: TextView
    private lateinit var cardLevel: TextView
    private lateinit var cardCurrency: TextView
    private lateinit var cardOrg: TextView
    private lateinit var titleBar: WhiteTitleBar

    private lateinit var firstRight: TextView
    private lateinit var secondRight: TextView
    private lateinit var thirdRight: TextView

    private lateinit var firstDetailTitle: TextView
    private lateinit var firstDetailContent: TextView
    private lateinit var secondDetailTitle: TextView
    private lateinit var secondDetailContent: TextView
    private lateinit var thirdDetailTitle: TextView
    private lateinit var thirdDetailContent: TextView


    private lateinit var model: CardDetailModel

    private lateinit var rightsList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cardId = intent.getStringExtra(INTENT_KEY_CARD_ID)
        titleBar.showBackButton(View.OnClickListener { finish() })
        requestData()
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        return scrollView {
            backgroundColor = Color.WHITE

            verticalLayout {
                titleLayout(title = "信用卡详情") {
                    titleBar = it
                }

                cardImage = simpleDraweeView {
                    hierarchy.actualImageScaleType = ScalingUtils.ScaleType.CENTER_CROP
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    leftMargin = dip(25)
                    rightMargin = dip(40)
                    topMargin = dip(20)
                    bottomMargin = dip(20)
                }

                cardName = textView {
                    textColor = resources.getColor(R.color.black_33)
                    textSize = 18f
                }.lparams {
                    width = matchParent
                    height = wrapContent
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL

                    cardLevel = textView {
                        background = resources.getDrawable(R.drawable.custom_yellow_bg)
                        setPadding(dip(5), dip(3), dip(5), dip(3))
                    }

                    cardCurrency = textView {
                        background = resources.getDrawable(R.drawable.custom_yellow_bg)
                        setPadding(dip(5), dip(3), dip(5), dip(3))
                    }.lparams {
                        leftMargin = dip(10)
                    }

                    cardOrg = textView {
                        background = resources.getDrawable(R.drawable.custom_yellow_bg)
                        setPadding(dip(5), dip(3), dip(5), dip(3))
                    }.lparams {
                        leftMargin = dip(10)
                    }
                }.lparams {
                    topMargin = dip(15)
                }

                textView("优惠权益") {
                    textSize = 16f
                }.lparams {
                    width = matchParent
                    topMargin = dip(20)
                }


                firstRight = textView {
                    textSize = 14f
                    textColor = resources.getColor(R.color.black_33)
                    val drawableLeft = resources.getDrawable(R.drawable.ic_rights)
                    drawableLeft.setBounds(0, 0, dip(15), dip(15))
                    setCompoundDrawables(drawableLeft, null, null, null)
                    compoundDrawablePadding = dip(5)
                }.lparams {
                    topMargin = dip(10)
                }

                secondRight = textView {
                    textSize = 14f
                    textColor = resources.getColor(R.color.black_33)
                    val drawableLeft = resources.getDrawable(R.drawable.ic_rights)
                    drawableLeft.setBounds(0, 0, dip(15), dip(15))
                    setCompoundDrawables(drawableLeft, null, null, null)
                    compoundDrawablePadding = dip(5)
                }.lparams {
                    topMargin = dip(10)
                }

                thirdRight = textView {
                    textSize = 14f
                    textColor = resources.getColor(R.color.black_33)
                    val drawableLeft = resources.getDrawable(R.drawable.ic_rights)
                    drawableLeft.setBounds(0, 0, dip(15), dip(15))
                    setCompoundDrawables(drawableLeft, null, null, null)
                    compoundDrawablePadding = dip(5)
                }.lparams {
                    topMargin = dip(10)
                    width = matchParent
                }


                textView("详情") {
                    textSize = 16f
                }.lparams {
                    width = matchParent
                    topMargin = dip(20)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL

                    firstDetailTitle = textView {
                        textSize = 14f
                    }.lparams {
                        width = dip(20)
                    }

                    firstDetailContent = textView {
                        textSize = 14f
                        textColor = resources.getColor(R.color.black_33)
                    }.lparams {
                        leftMargin = dip(20)
                    }
                }.lparams {
                    topMargin = dip(10)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL

                    secondDetailTitle = textView {
                        textSize = 14f
                    }

                    secondDetailContent = textView {
                        textSize = 14f
                        textColor = resources.getColor(R.color.black_33)
                    }.lparams {
                        leftMargin = dip(20)
                    }
                }.lparams {
                    topMargin = dip(10)
                }

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL

                    thirdDetailTitle = textView {
                        textSize = 14f
                    }

                    thirdDetailContent = textView {
                        textSize = 14f
                        textColor = resources.getColor(R.color.black_33)
                    }.lparams {
                        leftMargin = dip(20)
                    }
                }.lparams {
                    topMargin = dip(10)
                }

            }.lparams {
                leftMargin = dip(15)
                width = matchParent
                height = wrapContent
            }
        }
    }

    private fun requestData() {
        launch(UI) {
            val data = TaskRepository.getCardDetail(cardId)
            if (data?.code.equals("200")) {
                data?.data?.let {
                    model = it
                }
                bindData()
            }
        }

    }

    private fun bindData() {
        cardName.text = model.cardName
        cardImage.imageUrl = model.cardLogo
        cardLevel.text = model.cardLevel
        cardCurrency.text = model.currency
        cardOrg.text = model.org

        rightsList = model.right.split(Regex(";"), 3)

        when (rightsList.size) {
            3 -> {
                firstRight.text = rightsList[0]
                secondRight.text = rightsList[1]
                thirdRight.text = rightsList[2]
            }
            2 -> {
                firstRight.text = rightsList[0]
                secondRight.text = rightsList[1]
            }
            1 -> {
                firstRight.text = rightsList[0]
            }
            else -> {
                firstRight.visibility = View.GONE
                secondRight.visibility = View.GONE
                thirdRight.visibility = View.GONE

            }
        }

        setCardDetailInfo()

    }

    private fun setCardDetailInfo() {
        when (model.descInfo.size) {
            3 -> {
                firstDetailTitle.text = model.descInfo[0].name
                firstDetailContent.text = model.descInfo[0].value?.trim()
                secondDetailTitle.text = model.descInfo[1].name
                secondDetailContent.text = model.descInfo[1].value?.trim()
                thirdDetailTitle.text = model.descInfo[2].name
                thirdDetailContent.text = model.descInfo[2].value?.trim()
            }
            2 -> {
                firstDetailTitle.text = model.descInfo[0].name
                firstDetailContent.text = model.descInfo[0].value?.trim()
                secondDetailTitle.text = model.descInfo[1].name
                secondDetailContent.text = model.descInfo[1].value?.trim()
            }
            1 -> {
                firstDetailTitle.text = model.descInfo[0].name
                firstDetailContent.text = model.descInfo[0].value?.trim()
            }
            else -> {
                firstRight.visibility = View.GONE
                secondRight.visibility = View.GONE
                thirdRight.visibility = View.GONE

            }
        }
    }

    companion object {
        const val INTENT_KEY_CARD_ID = "card_id"
    }


}