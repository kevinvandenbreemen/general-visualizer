package com.vandenbreemen.jgdv.dsl

import java.awt.Color
import java.util.Collections

/**
 * Image to be displayed on the screen.  Note that an Image's pixel space is exactly the same as the
 * pixel space on the screen.  You will need to do mathematical transformations from your particular
 * vector space to the image yourself
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