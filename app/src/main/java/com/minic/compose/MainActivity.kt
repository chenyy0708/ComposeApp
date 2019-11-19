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
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Surface
import androidx.ui.text.style.TextOverflow
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
            }
        }
    }
}

@Composable
fun ArticleItems(articles: MutableList<ArticleData>) {
    articles.forEach {
        CreateArticleItem(it)
        ArticleDivider()
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
            Padding(padding = 15.dp) {
                FlexColumn {
                    inflexible {
                        // 头像、作者、发布时间、公众号
                        FlexRow(crossAxisAlignment = CrossAxisAlignment.Center) {
                            // 头像
                            inflexible {
                                createImage(
                                    type = PicassoLoader.ROUND,
                                    url = App.INSTANCE.resources.getStringArray(R.array.author_imgs).random()
                                )?.let { image ->
                                    Container(width = 25.dp, height = 25.dp) {
                                        DrawImage(image)
                                    }
                                }
                            }
                            // Margin间距
                            inflexible {
                                WidthSpacer(width = 8.dp)
                            }
                            // 作者
                            expanded(flex = 1f) {
                                Text(articleData.author)
                            }
                            // 发布时间
                            inflexible {
                                Text(articleData.niceDate)
                            }
                        }
                    }
                    // 垂直间距
                    inflexible {
                        HeightSpacer(height = 10.dp)
                    }
                    inflexible {
                        FlexRow(
                            mainAxisSize = LayoutSize.Expand
                        ) {
                            expanded(flex = 1f) {
                                Surface(color = Color.Red) {
                                    Column(
                                        modifier = ExpandedHeight,
                                        crossAxisSize = LayoutSize.Expand,
                                        mainAxisSize = LayoutSize.Expand
                                    ) {
                                        // 标题
                                        Padding(right = 10.dp) {
                                            Text(
                                                text = articleData.title,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                        // 公众号
                                        Text(text = articleData.superChapterName + articleData.author)
                                    }
                                }
                            }
                            // 右侧文章缩略图
                            inflexible {
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





