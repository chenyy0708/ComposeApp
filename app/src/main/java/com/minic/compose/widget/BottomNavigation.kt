package com.minic.compose.widget

import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.*
import androidx.ui.foundation.SimpleImage
import androidx.ui.foundation.selection.MutuallyExclusiveSetItem
import androidx.ui.graphics.Color
import androidx.ui.graphics.Image
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Surface
import androidx.ui.material.themeColor
import androidx.ui.text.TextStyle


/**
 * 描述: 自定义底部BottomNavigationView
 * 作者: ChenYy
 * 日期: 2019/12/3 09:35
 */

@Composable
fun <T> BottomNavigationView(
    items: List<T>,
    selectedIndex: Int,
    tab: @Composable() (Int, T, selectedIndex: Int) -> Unit
) {
    NavigationData.selectedIndex = selectedIndex
    Surface(color = Color.White) {
        val tabs = @Composable {
            items.forEachIndexed { index, item ->
                tab(index, item, NavigationData.selectedIndex)
            }
        }

        WithExpandedWidth { width ->
            FixedTabRow(width, items.size, tabs)
        }
    }
}

@Composable
private fun FixedTabRow(
    width: IntPx,
    tabCount: Int,
    tabs: @Composable() () -> Unit
) {
    Stack {
        aligned(Alignment.Center) {
            FlexRow {
                expanded(1f) {
                    tabs()
                }
            }
        }
        aligned(Alignment.TopCenter) {
            Opacity(0.08f) {
                Divider()
            }
        }

    }
}

@Composable
private fun WithExpandedWidth(child: @Composable() (width: IntPx) -> Unit) {
    var widthState by +state { IntPx.Zero }
    Layout({ child(widthState) }) { measurables, constraints ->
        val width = constraints.maxWidth
        if (widthState != width) widthState = width
        val placeable = measurables.first().measure(constraints)
        val height = placeable.height

        layout(width, height) {
            placeable.place(IntPx.Zero, IntPx.Zero)
        }
    }
}

@Model
object NavigationData {
    var selectedIndex: Int = 0
}

private val IconDiameter = 24.dp

data class TabIcon(
    val selectedIcon: Image,
    val unSelectedIcon: Image
)

@Composable
fun NavigationTab(
    text: String,
    icon: TabIcon,
    selected: Boolean,
    onSelected: () -> Unit,
    position: Int
) {
    val isSelect = position == NavigationData.selectedIndex

    Ripple(bounded = true) {
        MutuallyExclusiveSetItem(selected = selected, onClick = {
            NavigationData.selectedIndex = position
            onSelected()
        }) {
            Padding(top = 6.dp, bottom = 4.dp) {
                Column(crossAxisAlignment = CrossAxisAlignment.Center) {
                    Container(width = IconDiameter, height = IconDiameter) {
                        SimpleImage(if (isSelect) icon.selectedIcon else icon.unSelectedIcon)
                    }
                    Text(
                        text = text
                        , style = TextStyle(
                            color = if (isSelect) +themeColor { primary } else Color.Black,
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }
    }
}