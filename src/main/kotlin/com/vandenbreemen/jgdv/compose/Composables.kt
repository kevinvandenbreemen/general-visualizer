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

@Composable
fun ImageView(image: Image) {

    Canvas(Modifier.fillMaxSize()) {

        image.shapes.forEach { shape->
            when(shape) {
                is Rect -> {

                    val color = shape.color.let { col->
                        Color(col.red, col.green, col.blue, col.alpha)
                    }

                    drawRect(color, topLeft = Offset(shape.x, shape.y), size = Size(shape.dx, shape.dy))
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