package com.vandenbreemen.jgdv.dsl

/**
 * Anything drawable must be a shape
 */
interface  Shape

data class Rect(val x: Float, val y: Float, val dx: Float, val dy: Float): Shape
