package com.amulyakhare.textdrawable

import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.shapes.RectShape

class TextDrawableState(
        val textPaint: Paint,
        val borderPaint: Paint,
        val text: String,
        val color: Int,
        val shape: RectShape,
        val height: Int,
        val width: Int,
        val fontSize: Int,
        val radius: Float,
        val borderThickness: Int,
        var changingConfigurationMask: Int = 0,
) : Drawable.ConstantState() {

    companion object {
        private const val SHADE_FACTOR = 0.9f

        internal fun getDarkerShade(color: Int): Int {
            return Color.rgb((SHADE_FACTOR * Color.red(color)).toInt(), (SHADE_FACTOR * Color.green(color)).toInt(), (SHADE_FACTOR * Color.blue(color)).toInt())
        }
    }

    constructor(builder: Builder) : this(
            textPaint = Paint().apply {
                color = builder.textColor
                isAntiAlias = true
                isFakeBoldText = builder.isBold
                style = Paint.Style.FILL
                typeface = builder.font
                textAlign = Paint.Align.CENTER
                strokeWidth = builder.borderThickness.toFloat()
            },
            borderPaint = Paint().apply {
                color = getDarkerShade(color)
                style = Paint.Style.STROKE
                strokeWidth = builder.borderThickness.toFloat()
            },
            text = if (builder.toUpperCase) builder.text.uppercase() else builder.text,
            color = builder.color,
            shape = builder.shape,
            height = builder.height,
            width = builder.width,
            fontSize = builder.fontSize,
            radius = builder.radius,
            borderThickness = builder.borderThickness,
    )

    override fun newDrawable(): Drawable {
        return TextDrawable(this)
    }

    override fun getChangingConfigurations(): Int {
        return changingConfigurationMask
    }
}