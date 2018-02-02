package lidu.me.creditcardguide.model

/**
 * Created by lidu on 2018/1/9.
 */
data class CardListData(val code: String,
                        val data: CardListModel)

data class CardListModel(val count: String,
                         val list: List<CardListItemModel>)

data class CardListItemModel(val cardLogo: String,
                             val cardName: String,
                             val cardDesc: String,
                             val cardLevel: String,
                             val cardId: String)