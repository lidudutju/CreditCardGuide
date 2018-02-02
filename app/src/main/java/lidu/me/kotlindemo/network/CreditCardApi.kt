package lidu.me.kotlindemo.network

import com.google.gson.GsonBuilder
import lidu.me.kotlindemo.model.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by lidu on 2018/1/9.
 *
 */
interface CreditCardApi {

    @POST("/app/credit/card/list")
    fun getList(@Query("pageSize") pageSize: String,
                @Query("pageNum") pageNum: String): Call<CardListData>

    @POST("/app/credit/card/details")
    fun getCardDetail(@Query("cardId") cardId: String?): Call<CardDetailData>

    @GET("/bbs/v2/bbs/index.php/forum?fid=46&page_num=1&type=4&page_count=500")
    fun getForumList(): Call<ForumData>

    @POST("/app/bbs/posts/list")
    fun getThreadList(@Query("fid") fid: String,
                      @Query("pageSize") pageSize: String,
                      @Query("pageNum") pageNum: String,
                      @Query("cityId") cityId: String,
                      @Query("type") type: String): Call<ThreadData>

    @GET("/bbs/v2/bbs/index.php/post/thread")
    fun getThreadDetail(@Query("tid") tid: String): Call<ThreadDetailData>

    @GET("/bbs/v2/bbs/index.php/post/post")
    fun getPostList(@Query("pageSize") pageSize: String,
                    @Query("pageCount") pageNum: String,
                    @Query("isLandlord") isLandlord: String,
                    @Query("tid") tid: String): Call<ThreadPostsData>

    companion object {
        const val END_POINT: String = "http://api.51credit.com"
        const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

        fun create(): CreditCardApi {
            val gson = GsonBuilder()
                    .setDateFormat(DATE_FORMAT)
                    .create()

            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequst = chain.request()
                        .newBuilder()
                        .addHeader("X-Tingyun-Id", "4P9XWGbPZ8E;c=2;r=1452560304")
                        .addHeader("X-Tingyun-Lib-Type-N-ST", "0;1513177863797")
                        .addHeader("os", "!")
                        .addHeader("buid", "KDulEAE9pvmsxbn2VUHJe8JfPiXzEXqx")
                        .addHeader("cityid", "110100")
                        .addHeader("User-Agent", "Dalvik/2.1.0 (Linux/aarch64/3.10.73-g2a6401aeb452; Android/8.0.0; Nexus 5X Build/OPR6.170623.023)  Mobile WakBrowser/1.8 kashen/5.5.9 (yingyongbao)")
                        .addHeader("token", "8c76f5b0aef365138e3a7fd0dbd95651")
                        .addHeader("site", "kashen")
                        .addHeader("user_id", "KDulEAE9pvncV5YYRZvVwg==")
                        .addHeader("uuid", "a-35436007001650-02:00:00:00:00:00-639737f00ab1e746")
                        .addHeader("device", "Nexus 5X")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("Charset", "UTF-8")
                        .addHeader("Accept-Encoding", "gzip")
                        .build()
                chain.proceed(newRequst)
            }.build()

            val retrofit = Retrofit.Builder()
                    .client(client)
                    .baseUrl(END_POINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit.create(CreditCardApi::class.java)
        }

    }

}