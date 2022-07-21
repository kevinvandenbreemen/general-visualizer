package com.vandenbreemen.jgdv.compose

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.vandenbreemen.jgdv.dsl.Image
import com.vandenbreemen.jgdv.dsl.Rect

/**
 * Convert the given AWT color to a compose color
 */
private fun color(col: java.awt.Color): Color {
    return Color(col.red, col.green, col.blue, col.alpha)
}

@Composable
fun ImageView(image: Image) {

    Canvas(Modifier.fillMaxSize()) {

        drawRect(color(image.background), topLeft = Offset(0f,0f), size= Size(this.size.width, this.size.height))

        image.shapes.forEach { shape->
            when(shape) {
                is Rect -> {
                    drawRect(color(shape.color), topLeft = Offset(shape.x, shape.y), size = Size(shape.dx, shape.dy))
                }
            }
        }

    }

}

@Composable
@Preview
fun ImageViewPreview() {

    val image = Image().addShape(Rect(java.awt.Color.green, 10.0f, 10.0f, 10.0f, 10.0f))
    ImageView(image)

}