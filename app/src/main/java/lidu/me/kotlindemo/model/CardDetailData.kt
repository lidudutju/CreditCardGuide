package lidu.me.kotlindemo.model

/**
 * Created by lidu on 2018/1/10.
 */
data class CardDetailData(val code: String,
                          val data: CardDetailModel)


data class CardDetailModel(val cardName: String?,
                           val cardLogo: String?,
                           val cardId: String?,
                           val backId: String?,
                           val right: String,
                           val cardLevel: String?,
                           val currency: String?,
                           val org: String?,
                           val descInfo: List<CardDescInfoModel>)

data class CardDescInfoModel(val name: String?,
                             val value: String?)