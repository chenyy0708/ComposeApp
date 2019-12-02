package com.minic.compose.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.frames.ModelList
import androidx.ui.core.setContent
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.material.MaterialTheme
import com.minic.compose.ui.compose.home.ArticleItems
import com.minic.compose.ui.compose.home.HomeBanner
import com.minic.kt.data.WARepository
import com.minic.kt.data.model.gank.home.ArticleData
import com.minic.kt.data.model.gank.home.BannerData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(HomeArticles.mArticles)
        }
        GlobalScope.launch {
            val articleTops = WARepository.articleTop().await()
            val articles = WARepository.article(1).await()
            val banner = WARepository.banners().await()
            withContext(Dispatchers.Main) {
                articleTops.data.forEach { it.isTopping = true }
                HomeArticles.mArticles.addAll(articleTops.data)
                HomeArticles.mArticles.addAll(articles.data.datas)
                HomeArticles.mBanner.addAll(banner.data)
            }
        }
    }
}

@Model
object HomeArticles {
    // 文章
    val mArticles = ModelList<ArticleData>()
    // 首页Banner
    val mBanner = ModelList<BannerData>()
}

@Composable
fun MyApp(articles: MutableList<ArticleData>) {
    MaterialTheme {
        VerticalScroller {
            Column {
                HomeBanner(banner = HomeArticles.mBanner)
                ArticleItems(articles)
            }
        }
    }
}






