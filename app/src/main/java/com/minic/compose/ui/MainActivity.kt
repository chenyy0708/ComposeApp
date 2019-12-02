package com.minic.compose.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.frames.ModelList
import androidx.ui.core.setContent
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.imageFromResource
import androidx.ui.layout.Column
import androidx.ui.layout.FlexColumn
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Tab
import androidx.ui.material.TabRow
import com.minic.compose.App
import com.minic.compose.R
import com.minic.compose.expansion.logD
import com.minic.compose.model.TabData
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
    val mTabData = ModelList<TabData>().apply {
        add(TabData("首页", imageFromResource(App.INSTANCE.resources, R.drawable.header)))
        add(TabData("导航", imageFromResource(App.INSTANCE.resources, R.drawable.header)))
        add(TabData("体系", imageFromResource(App.INSTANCE.resources, R.drawable.header)))
        add(TabData("我的", imageFromResource(App.INSTANCE.resources, R.drawable.header)))
    }
}


@Composable
fun MyApp(articles: MutableList<ArticleData>) {
    MaterialTheme {
        FlexColumn {
            flexible(1.0f) {
                VerticalScroller {
                    Column {
                        HomeBanner(banner = HomeArticles.mBanner)
                        ArticleItems(articles)
                    }
                }
            }
            inflexible {
                TabRow(
                    items = HomeArticles.mTabData,
                    scrollable = false,
                    selectedIndex = 0,
                    indicatorContainer = {}
                ) { position, data ->
                    Tab(selected = true, onSelected = {
                        logD(msg = "点击事件")
                    }, text = data.tabName, icon = data.tabIcon)
                }
            }
        }
    }
}






