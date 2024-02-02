package cn.kurisu.hutaonote.activity

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

/**
 * 自定义组件
 * @className : MyText
 * @author : kurisu
 * @date : 2024-02-02 23:25
 */
@Composable
fun MyText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    firstBaselineToTop: Dp? = null,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text,
        modifier.firstBaseline2Top(firstBaselineToTop),
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow,
        softWrap,
        maxLines,
        minLines,
        onTextLayout,
        style
    )
}

@Composable
fun MyColumn(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Layout(content, modifier) { measurables, constraints ->
        val placeableList = measurables.map { measurable -> measurable.measure(constraints) }
        var pY = 0
        layout(constraints.maxWidth, constraints.maxHeight) {
            placeableList.forEach { placeable ->
                placeable.placeRelative(0, pY)
                pY += placeable.height
            }
        }
    }
}

fun Modifier.firstBaseline2Top(baseline2Top: Dp?) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val baseLine = placeable[FirstBaseline]
        var placeableY = (baseline2Top?.roundToPx() ?: 0) - baseLine
        if (placeableY < 0) {
            placeableY = 0
        }
        layout(placeable.width, placeable.height + placeableY) {
            placeable.placeRelative(0, placeableY)
        }
    }
)

