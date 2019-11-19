package com.minic.kt.data

import com.minic.kt.data.api.WAndroidService
import com.minic.kt.data.model.BResponse
import com.minic.kt.data.model.gank.home.Article
import com.minic.kt.data.model.gank.home.ArticleData
import kotlinx.coroutines.Deferred

/**
 * 描述: 网络请求
 * 作者: ChenYy
 * 日期: 2019/10/31 16:35
 */

object WARepository {
    private val apiService by lazy {
        RetrofitProvider.createService(WAndroidService::class.java)
    }

    fun article(page: Int): Deferred<BResponse<Article>> = apiService.article(page)
    fun articleTop(): Deferred<BResponse<MutableList<ArticleData>>> = apiService.articleTop()
}