package com.vandenbreemen.jgdv.dsl

import java.awt.Color

/**
 * Anything drawable must be a shape
 */
interface  Shape

data class Rect(val color: Color, val x: Float, val y: Float, val dx: Float, val dy: Float): Shape
