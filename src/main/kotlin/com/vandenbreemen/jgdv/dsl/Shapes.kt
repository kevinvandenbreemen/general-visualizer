package com.vandenbreemen.jgdv.dsl

import java.awt.Color

interface ShapeCallback {

    /**
     * Fired when the given shape has been clicked/tapped
     */
    fun onShapeClicked(shape: Shape){}

    /**
     * Fired when the given location on the screen has been clicked but a shape has not been
     */
    fun onPointClicked(x: Float, y: Float){}
}

/**
 * Anything drawable must be a shape
 */
interface Shape {
    fun contains(x: Float, y: Float): Boolean
}

data class Rect(val color: Color, val x: Float, val y: Float, val dx: Float, val dy: Float): Shape {
    override fun contains(x: Float, y: Float): Boolean {
        if(this.x <= x && this.y <= y) {
            return (this.x + dx) >= x && (this.y + dy) >= y
        }
        return false
    }
}
