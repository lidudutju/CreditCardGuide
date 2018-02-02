package lidu.me.creditcardguide.network

import lidu.me.creditcardguide.ifSucceeded
import lidu.me.creditcardguide.model.*
import org.jetbrains.anko.AnkoLogger
import ru.gildor.coroutines.retrofit.awaitResult
import ru.gildor.coroutines.retrofit.getOrNull

/**
 * Created by lidu on 2018/1/9.
 */
object TaskRepository : AnkoLogger {

    private val retrofitApi: CreditCardApi by lazy {
        CreditCardApi.create()
    }

    suspend fun getList(pageSize: String): CardListData? {
        retrofitApi.getList(pageSize, "15")
                .awaitResult()
                .ifSucceeded {
                    if (it.code.equals("200")) {
                        return it
                    }
                }
        return null
    }

    suspend fun getCardDetail(cardId: String?): CardDetailData? {
        return retrofitApi.getCardDetail(cardId)
                .awaitResult()
                .getOrNull()
    }

    suspend fun getForumList(): ForumData? {
        retrofitApi.getForumList()
                .awaitResult()
                .ifSucceeded {
                    if (it.code.equals("200")) {
                        return it
                    }
                }
        return null
    }

    suspend fun getThreadList(fid: String, pageSize: String, pageNum: String): ThreadData? {
        retrofitApi.getThreadList(fid, pageSize, pageNum, "110100", "1")
                .awaitResult()
                .ifSucceeded {
                    if (it.code.equals("200")) {
                        return it
                    }
                }
        return null
    }

    suspend fun getThreadDetail(tid: String): ThreadDetailData? {
        retrofitApi.getThreadDetail(tid)
                .awaitResult()
                .ifSucceeded {
                    if (it.code.equals("200")) {
                        return it
                    }
                }
        return null
    }

    suspend fun getPostList(pageSize: String, pageCount: String, isLandloar: String, tid: String): ThreadPostsData? {
        retrofitApi.getPostList(pageSize, pageCount, isLandloar, tid)
                .awaitResult()
                .ifSucceeded {
                    if (it.code.equals("200")) {
                        return it
                    }
                }
        return null
    }
}