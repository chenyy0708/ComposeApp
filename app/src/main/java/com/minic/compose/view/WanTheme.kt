package com.minic.compose.view

import androidx.compose.Composable
import androidx.ui.material.MaterialTheme


/**
 * 描述: APP内统一主题
 * 作者: ChenYy
 * 日期: 2019/10/31 17:06
 */

fun WanTheme(children: @Composable() () -> Unit) {
    MaterialTheme {
        children()
    }
}

