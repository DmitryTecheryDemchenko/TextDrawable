package com.amulyakhare.textdrawable

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape

class Builder internal constructor() : TextDrawable.IConfigBuilder, TextDrawable.IShapeBuilder, TextDrawable.IBuilder {
    internal var text = ""
    internal var color: Int
    internal var borderThickness: Int
    internal var width: Int
    internal var height: Int
    internal var font: Typeface
    internal var shape: RectShape
    internal var textColor: Int
    internal var fontSize: Int
    internal var isBold: Boolean
    internal var toUpperCase: Boolean
    internal var radius = 0f

    override fun width(width: Int): TextDrawable.IConfigBuilder = apply {
        this.width = width
    }

    override fun height(height: Int): TextDrawable.IConfigBuilder = apply {
        this.height = height
    }

    override fun textColor(color: Int): TextDrawable.IConfigBuilder = apply {
        textColor = color
    }

    override fun withBorder(thickness: Int): TextDrawable.IConfigBuilder = apply {
        borderThickness = thickness
    }

    override fun useFont(font: Typeface): TextDrawable.IConfigBuilder = apply {
        this.font = font
    }

    override fun fontSize(size: Int): TextDrawable.IConfigBuilder = apply {
        fontSize = size
    }

    override fun bold(): TextDrawable.IConfigBuilder = apply {
        isBold = true
    }

    override fun toUpperCase(): TextDrawable.IConfigBuilder = apply {
        toUpperCase = true
    }

    override fun beginConfig(): TextDrawable.IConfigBuilder = this

    override fun endConfig(): TextDrawable.IShapeBuilder = this

    override fun rect(): TextDrawable.IBuilder = apply {
        shape = RectShape()
    }

    override fun round(): TextDrawable.IBuilder = apply {
        shape = OvalShape()
    }

    override fun roundRect(radius: Int): TextDrawable.IBuilder = apply {
        this.radius = radius.toFloat()
        val radii = FloatArray(8) { this.radius }
        shape = RoundRectShape(radii, null, null)
    }

    override fun buildRect(text: String, color: Int): TextDrawable {
        rect()
        return build(text, color)
    }

    override fun buildRoundRect(text: String, color: Int, radius: Int): TextDrawable {
        roundRect(radius)
        return build(text, color)
    }

    override fun buildRound(text: String, color: Int): TextDrawable {
        round()
        return build(text, color)
    }

    override fun build(text: String, color: Int): TextDrawable {
        this.color = color
        this.text = text
        return TextDrawable(state = TextDrawableState(this))
    }

    init {
        color = Color.GRAY
        textColor = Color.WHITE
        borderThickness = 0
        width = -1
        height = -1
        shape = RectShape()
        font = Typeface.create("sans-serif-light", Typeface.NORMAL)
        fontSize = -1
        isBold = false
        toUpperCase = false
    }
}