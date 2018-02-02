package lidu.me.kotlindemo.model

/**
 * Created by lidu on 2018/1/30.
 */
data class ForumData(val code: String, val data: ForumModel)


data class ForumModel(val list: List<ForumItemModel>)

data class ForumItemModel(val name: String,
                          val threads: String,
                          val posts: String,
                          val fid: String,
                          val icon: String,
                          val is_favorite: String)