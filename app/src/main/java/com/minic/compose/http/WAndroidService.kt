package com.minic.kt.data.api

import com.minic.kt.data.model.BResponse
import com.minic.kt.data.model.gank.home.Article
import com.minic.kt.data.model.gank.home.ArticleData
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 描述: Retrofit Service
 * 作者: ChenYy
 * 日期: 2019/10/31 16:35
 */

interface WAndroidService {
    /**
     * 文章列表
     */
    @GET("article/list/{page}/json")
     fun article(@Path("page") page: Int): Deferred<BResponse<Article>>

    /**
     * 置顶文章
     */
    @GET("article/top/json")
     fun articleTop(): Deferred<BResponse<MutableList<ArticleData>>>

}