package com.vandenbreemen.jgdv.dsl

import java.awt.Color
import java.util.Collections

/**
 * Image to be displayed on the screen
 */
class Image {

    private var backgroundColor: Color = Color.black
    val background: Color get() = backgroundColor

    private val _shapes: MutableList<Shape> = mutableListOf()
    val shapes: List<Shape> get() = Collections.unmodifiableList(_shapes)

    fun addShape(shape: Shape): Image {
        _shapes.add(shape)
        return this
    }

    fun setBackgroundColor(color: Color) {
        this.backgroundColor = color
    }

}