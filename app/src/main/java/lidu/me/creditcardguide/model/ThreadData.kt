package lidu.me.creditcardguide.model

/**
 * Created by lidu on 2018/1/31.
 */
data class ThreadData(val code: String,
                      val data: ThreadModel)

data class ThreadModel(val count: String,
                       val list: List<ThreadItemModel>)

data class ThreadItemModel(val authorId: String,
                           val authorName: String,
                           val fname: String,
                           val face: String,
                           val intro: String,
                           val targetUrl: String,
                           val imgList: List<String>?,
                           val title: String)