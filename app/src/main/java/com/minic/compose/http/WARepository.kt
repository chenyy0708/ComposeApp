package com.minic.kt.data

import com.minic.kt.data.api.WAndroidService
import com.minic.kt.data.model.BResponse
import com.minic.kt.data.model.gank.PagingData
import com.minic.kt.data.model.gank.home.*

/**
 * 描述: 网络请求
 * 作者: ChenYy
 * 日期: 2019/10/31 16:35
 */

object WARepository {

    private val apiService by lazy {
        RetrofitProvider.createService(WAndroidService::class.java)
    }

    suspend fun banners(): BResponse<MutableList<BannerData>> = apiService.banners()
    suspend fun article(page: Int): BResponse<Article> = apiService.article(page)
    suspend fun articleTop(): BResponse<MutableList<ArticleData>> = apiService.articleTop()
    suspend fun projectTree(): BResponse<MutableList<ProjectTree>> = apiService.projectTree()
    suspend fun projectList(page: Int, cid: Int): BResponse<PagingData<ArticleData>> = apiService.projectList(page, cid)
    suspend fun systemTree(): BResponse<MutableList<SystemTree>> = apiService.systemTree()
}