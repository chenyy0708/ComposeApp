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
                articleTops.data.forEach { it.isTopping = true }
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






