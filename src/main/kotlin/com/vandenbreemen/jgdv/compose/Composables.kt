package com.vandenbreemen.jgdv.compose

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.window.Window
import com.vandenbreemen.jgdv.dsl.Image
import com.vandenbreemen.jgdv.dsl.Rect
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.ui.window.application
import com.vandenbreemen.jgdv.dsl.Shape
import com.vandenbreemen.jgdv.dsl.ShapeCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

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

                println("clickedPoint = (${offset.x}, ${offset.y})")

                var shapeClicked = false
                image.shapes.forEach { shape->
                    if(shape.contains(offset.x, offset.y)) {
                        shapeClicked = true
                        println("image=$image")
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

/*
clickedPoint = (5.0, 20.0)
Shape Rect(color=java.awt.Color[r=128,g=128,b=128], x=0.0, y=10.0, dx=10.0, dy=10.0) clicked
 */

@Composable
fun AnimatedImageDisplay(imageToDisplay: StateFlow<Image>, shapeCallback: ShapeCallback? = null) {
    imageToDisplay.collectAsState(Dispatchers.Main).value.let { image->
        ImageView(image, shapeCallback)
    }
}


/**
 * Demo Application
 */
fun main() = application{

    val flow = MutableStateFlow(makeImage(0))


    CoroutineScope(Dispatchers.Default).launch {

        for(i in 0..4) {
            delay(100)

            flow.emit(makeImage(i*100))
        }

    }

    Window(onCloseRequest = ::exitApplication, title = "Displaying animated grid of squares demo") {

        AnimatedImageDisplay(flow, object: ShapeCallback {
            override fun onShapeClicked(shape: Shape) {
                println("Shape $shape clicked")
            }

            override fun onPointClicked(x: Float, y: Float) {
                println("Point $x, $y clicked")
            }
        })

    }
}

private fun makeImage(idx: Int): Image {
    return Image().apply {
        addShape(Rect(java.awt.Color.gray, idx.toFloat(), idx.toFloat(), 100f, 100f))
        val random = Random(System.nanoTime())
        for(i in 1 .. 100) {
            //addShape(Rect(java.awt.Color.red, i*10f, random.nextFloat()*100, 10f, 10f))
        }
    }
}


@Composable
@Preview
fun ImageViewPreview() {

    val image = Image().addShape(Rect(java.awt.Color.green, 10.0f, 10.0f, 10.0f, 10.0f))
    ImageView(image)

}