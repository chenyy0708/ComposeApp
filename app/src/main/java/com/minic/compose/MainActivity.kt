package com.minic.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.frames.ModelList
import androidx.ui.core.Opacity
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import com.minic.compose.expansion.createImage
import com.minic.compose.expansion.logD
import com.minic.compose.utils.PicassoLoader
import com.minic.kt.data.WARepository
import com.minic.kt.data.model.gank.home.ArticleData
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
            withContext(Dispatchers.Main) {
                HomeArticles.mArticles.addAll(articleTops.data)
                HomeArticles.mArticles.addAll(articles.data.datas)
            }
        }
    }
}

@Model
object HomeArticles {
    // 文章
    val mArticles = ModelList<ArticleData>()
}

@Composable
fun MyApp(articles: MutableList<ArticleData>) {
    MaterialTheme {
        VerticalScroller {
            Column {
                ArticleItems(articles)
                ArticleDivider()
            }
        }
    }
}

@Composable
fun ArticleItems(articles: MutableList<ArticleData>) {
    articles.forEach {
        Text(it.title)
        CreateArticleItem(it)
    }
}

/**
 * 文章Item
 */
@Composable
fun CreateArticleItem(articleData: ArticleData) {
    Ripple(bounded = true) {
        // 水波纹
        Clickable(onClick = { logD("点击文章") }) {
            Padding(padding = 16.dp) {
                FlexRow {
                    flexible(1f) {
                        Column(mainAxisSize = LayoutSize.Expand) {
                            createImage(
                                type = PicassoLoader.ROUND,
                                url = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2018939532,1617516463&fm=26&gp=0.jpg"
                            )?.let { image ->
                                Container(width = 30.dp, height = 30.dp) {
                                    DrawImage(image)
                                }
                            }
                        }
                    }
                    inflexible {
                        Padding(right = 16.dp) {
                            createImage(url = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2018939532,1617516463&fm=26&gp=0.jpg")?.let { image ->
                                Container(width = 80.dp, height = 80.dp) {
                                    DrawImage(image)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 分割线
 */
@Composable
private fun ArticleDivider() {
    Padding(left = 14.dp, right = 14.dp) {
        Opacity(0.08f) {
            Divider()
        }
    }
}





