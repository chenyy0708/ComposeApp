package com.minic.compose

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.core.Text
import androidx.ui.core.setContent
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.material.MaterialTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(HomeArticles.mArticles)
        }
        HomeArticles.mArticles.add("1")
        HomeArticles.mArticles.add("2")
        HomeArticles.mArticles.add("3")
        HomeArticles.mArticles.add("4")
        HomeArticles.mArticles.add("5")
        HomeArticles.mArticles.add("6")
    }
}

@Model
object HomeArticles {
    val mArticles: MutableList<String> = mutableListOf()
}

@Composable
fun MyApp(datas: MutableList<String>) {
    MaterialTheme {
        VerticalScroller() {
            Column {
                articleTop(datas)
            }
        }
    }
}

@Composable
fun articleTop(datas: MutableList<String>) {
    datas.forEach {
        Text(it)
    }
}

