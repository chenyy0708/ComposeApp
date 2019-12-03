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
import com.minic.compose.App
import com.minic.compose.R
import com.minic.compose.model.TabData
import com.minic.compose.ui.compose.home.ArticleItems
import com.minic.compose.ui.compose.home.HomeBanner
import com.minic.compose.widget.BottomNavigationView
import com.minic.compose.widget.NavigationTab
import com.minic.compose.widget.TabIcon
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
        add(
            TabData(
                "首页", TabIcon(
                    imageFromResource(App.INSTANCE.resources, R.drawable.icon_home_pager_selected),
                    imageFromResource(
                        App.INSTANCE.resources,
                        R.drawable.icon_home_pager_not_selected
                    )
                )
            )
        )
        add(
            TabData(
                "项目", TabIcon(
                    imageFromResource(App.INSTANCE.resources, R.drawable.icon_project_selected),
                    imageFromResource(App.INSTANCE.resources, R.drawable.icon_project_not_selected)
                )
            )
        )
        add(
            TabData(
                "公众号", TabIcon(
                    imageFromResource(
                        App.INSTANCE.resources,
                        R.drawable.icon_knowledge_hierarchy_selected
                    ),
                    imageFromResource(
                        App.INSTANCE.resources,
                        R.drawable.icon_knowledge_hierarchy_not_selected
                    )
                )
            )
        )
        add(
            TabData(
                "体系", TabIcon(
                    imageFromResource(App.INSTANCE.resources, R.drawable.icon_navigation_selected),
                    imageFromResource(
                        App.INSTANCE.resources,
                        R.drawable.icon_navigation_not_selected
                    )
                )
            )
        )
        add(
            TabData(
                "我的", TabIcon(
                    imageFromResource(App.INSTANCE.resources, R.drawable.icon_me_selected),
                    imageFromResource(App.INSTANCE.resources, R.drawable.icon_me_not_selected)
                )
            )
        )
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
                BottomNavigationView(
                    items = HomeArticles.mTabData,
                    selectedIndex = 0
                ) { position, data, selectedIndex ->
                    NavigationTab(selected = selectedIndex == position, onSelected = {
                        // 处理页面切换
                    }, text = data.tabName, icon = data.tabIcon, position = position)
                }
            }
        }
    }
}






