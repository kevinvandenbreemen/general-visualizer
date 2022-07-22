package com.vandenbreemen.jgdv.compose

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import com.vandenbreemen.jgdv.dsl.Image
import com.vandenbreemen.jgdv.dsl.Rect
import com.vandenbreemen.jgdv.dsl.ShapeCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow

/**
 * Convert the given AWT color to a compose color
 */
private fun color(col: java.awt.Color): Color {
    return Color(col.red, col.green, col.blue, col.alpha)
}

@Composable
fun ImageView(image: Image, shapeCallback: ShapeCallback? = null) {

    Canvas(Modifier.fillMaxSize().pointerInput("PointerInput_${image.hashCode()}") {
        shapeCallback?.let {callback->

            detectTapGestures(onTap = {offset->

                var shapeClicked = false
                image.shapes.forEach { shape->
                    if(shape.contains(offset.x, offset.y)) {
                        shapeClicked = true
                        callback.onShapeClicked(shape)
                    }
                }

                if(!shapeClicked) {
                    callback.onPointClicked(offset.x, offset.y)
                }
            })
        }

    }) {

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
fun AnimatedImageDisplay(imageToDisplay: StateFlow<Image>, shapeCallback: ShapeCallback? = null) {
    imageToDisplay.collectAsState(Dispatchers.Main).value.let { image->
        ImageView(image, shapeCallback)
    }
}

@Composable
@Preview
fun ImageViewPreview() {

    val image = Image().addShape(Rect(java.awt.Color.green, 10.0f, 10.0f, 10.0f, 10.0f))
    ImageView(image)

}