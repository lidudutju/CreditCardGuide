package lidu.me.kotlindemo.model

/**
 * Created by lidu on 2018/2/1.
 */
data class ThreadDetailData(val code: String,
                            val data: ThreadDetailModel)

data class ThreadDetailModel(val tid: String,
                             val subject: String,
                             val author: String,
                             val groupTitle: String,
                             val signature: String,
                             val replies: String,
                             val views: String,
                             val shareDesc: String,
                             val portrait: String,
                             val authorId: String)