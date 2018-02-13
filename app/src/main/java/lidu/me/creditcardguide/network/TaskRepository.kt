package lidu.me.creditcardguide.network

import android.content.Context
import lidu.me.creditcardguide.R
import lidu.me.creditcardguide.ifFailed
import lidu.me.creditcardguide.ifSucceeded
import lidu.me.creditcardguide.model.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import ru.gildor.coroutines.retrofit.awaitResult
import ru.gildor.coroutines.retrofit.getOrNull

/**
 * Created by lidu on 2018/1/9.
 */
object TaskRepository : AnkoLogger {

    private const val HTTP_CODE_OK = "200"

    private val retrofitApi: CreditCardApi by lazy {
        CreditCardApi.create()
    }

    suspend fun getList(pageSize: String): CardListData? {
        retrofitApi.getList(pageSize, "15")
                .awaitResult()
                .ifSucceeded {
                    if (it.code == HTTP_CODE_OK) {
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
                    if (it.code == HTTP_CODE_OK) {
                        return it
                    }
                }
        return null
    }

    suspend fun getThreadList(fid: String, pageSize: String, pageNum: String): ThreadData? {
        retrofitApi.getThreadList(fid, pageSize, pageNum, "110100", "1")
                .awaitResult()
                .ifSucceeded {
                    if (it.code == HTTP_CODE_OK) {
                        return it
                    }
                }
        return null
    }

    suspend fun getThreadDetail(tid: String): ThreadDetailData? {
        retrofitApi.getThreadDetail(tid)
                .awaitResult()
                .ifSucceeded {
                    if (it.code == HTTP_CODE_OK) {
                        return it
                    }
                }
        return null
    }

    suspend fun getPostList(pageSize: String, pageCount: String, isLandload: String, tid: String): ThreadPostsData? {
        retrofitApi.getPostList(pageSize, pageCount, isLandload, tid)
                .awaitResult()
                .ifSucceeded {
                    if (it.code == HTTP_CODE_OK) {
                        return it
                    }
                }
        return null
    }

}