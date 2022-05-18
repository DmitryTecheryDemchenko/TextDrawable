package com.amulyakhare.textdrawable

import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import com.amulyakhare.textdrawable.TextDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RoundRectShape
import java.util.*

/**
 * @author amulya
 * @datetime 14 Oct 2014, 3:53 PM
 */
class TextDrawable internal constructor(
        private val state: TextDrawableState,
) : ShapeDrawable(state.shape) {

    private val textPaint: Paint by lazy(LazyThreadSafetyMode.NONE) { state.textPaint }
    private val borderPaint: Paint by lazy(LazyThreadSafetyMode.NONE) { state.borderPaint }
    private val text: String by lazy(LazyThreadSafetyMode.NONE) { state.text }
    private val color: Int by lazy(LazyThreadSafetyMode.NONE) { state.color }
    private val shape: RectShape by lazy(LazyThreadSafetyMode.NONE) { state.shape }
    private val height: Int by lazy(LazyThreadSafetyMode.NONE) { state.height }
    private val width: Int by lazy(LazyThreadSafetyMode.NONE) { state.width }
    private val fontSize: Int by lazy(LazyThreadSafetyMode.NONE) { state.fontSize }
    private val radius: Float by lazy(LazyThreadSafetyMode.NONE) { state.radius }
    private val borderThickness: Int by lazy(LazyThreadSafetyMode.NONE) { state.borderThickness }

    companion object {
        @JvmStatic
        fun builder(): IShapeBuilder {
            return Builder()
        }
    }

    init {
        paint.color = color
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val r = bounds


        // draw border
        if (borderThickness > 0) {
            drawBorder(canvas)
        }
        val count = canvas.save()
        canvas.translate(r.left.toFloat(), r.top.toFloat())

        // draw text
        val width = if (width < 0) r.width() else width
        val height = if (height < 0) r.height() else height
        val fontSize = if (fontSize < 0) Math.min(width, height) / 2 else fontSize
        textPaint.textSize = fontSize.toFloat()
        canvas.drawText(text, (width / 2).toFloat(), height / 2 - (textPaint.descent() + textPaint.ascent()) / 2, textPaint)
        canvas.restoreToCount(count)
    }

    private fun drawBorder(canvas: Canvas) {
        val rect = RectF(bounds)
        rect.inset((borderThickness / 2).toFloat(), (borderThickness / 2).toFloat())
        if (shape is OvalShape) {
            canvas.drawOval(rect, borderPaint)
        } else if (shape is RoundRectShape) {
            canvas.drawRoundRect(rect, radius, radius, borderPaint)
        } else {
            canvas.drawRect(rect, borderPaint)
        }
    }

    override fun setAlpha(alpha: Int) {
        textPaint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        textPaint.colorFilter = cf
    }

    @Deprecated("Not used in drawable optimization")
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun getIntrinsicWidth(): Int {
        return width
    }

    override fun getIntrinsicHeight(): Int {
        return height
    }

    override fun getConstantState(): ConstantState? {
        return state.apply {
            changingConfigurationMask = this@TextDrawable.changingConfigurations
        }
    }

    // BUILDERS


    interface IConfigBuilder {
        fun width(width: Int): IConfigBuilder
        fun height(height: Int): IConfigBuilder
        fun textColor(color: Int): IConfigBuilder
        fun withBorder(thickness: Int): IConfigBuilder
        fun useFont(font: Typeface): IConfigBuilder
        fun fontSize(size: Int): IConfigBuilder
        fun bold(): IConfigBuilder
        fun toUpperCase(): IConfigBuilder
        fun endConfig(): IShapeBuilder
    }

    interface IBuilder {
        fun build(text: String, color: Int): TextDrawable
    }

    interface IShapeBuilder {
        fun beginConfig(): IConfigBuilder
        fun rect(): IBuilder
        fun round(): IBuilder
        fun roundRect(radius: Int): IBuilder
        fun buildRect(text: String, color: Int): TextDrawable
        fun buildRoundRect(text: String, color: Int, radius: Int): TextDrawable
        fun buildRound(text: String, color: Int): TextDrawable
    }
}