package lidu.me.kotlindemo.model

/**
 * Created by lidu on 2018/2/1.
 */
data class ThreadPostsData(val code: String,
                           val data: ThreadPostsModel)

data class ThreadPostsModel(val allSize: String,
                            val replies: String,
                            val currentSize: String,
                            val list: List<PostListItemModel>)

data class PostListItemModel(val tid: String,
                             val fid: String,
                             val author: String,
                             val content: String,
                             val position: String,
                             val groupTitle: String,
                             val groupTitleColor: String,
                             val replyPost: ReplyPostModel,
                             val signature: String,
                             val isLandlord: String,
                             val portrait: String,
                             val authorId: String)

data class ReplyPostModel(val author: String,
                          val dateline: String,
                          val content: String)