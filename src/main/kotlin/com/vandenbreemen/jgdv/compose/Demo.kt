package com.vandenbreemen.jgdv.compose

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vandenbreemen.jgdv.dsl.Image
import com.vandenbreemen.jgdv.dsl.Rect
import com.vandenbreemen.jgdv.dsl.Shape
import com.vandenbreemen.jgdv.dsl.ShapeCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.awt.Color
import kotlin.random.Random


/**
 * Demo Application
 */
internal fun main() = application{

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
        addShape(Rect(Color.gray, idx.toFloat(), idx.toFloat(), 100f, 100f))
        val random = Random(System.nanoTime())
        for(i in 1 .. 100) {
            addShape(Rect(Color.red, i*10f, random.nextFloat()*100, 10f, 10f))
        }
    }
}