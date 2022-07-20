package com.vandenbreemen.jgdv.dsl

import java.util.Collections

/**
 * Image to be displayed on the screen
 */
class Image {

    private val _shapes: MutableList<Shape> = mutableListOf()
    val shapes: List<Shape> get() = Collections.unmodifiableList(_shapes)

    fun addShape(shape: Shape): Image {
        _shapes.add(shape)
        return this
    }

}