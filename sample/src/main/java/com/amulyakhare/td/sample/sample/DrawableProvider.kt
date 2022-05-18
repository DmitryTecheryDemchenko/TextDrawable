package com.amulyakhare.td.sample.sample

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.LayerDrawable
import android.util.TypedValue
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.TextDrawable.Companion.builder
import com.amulyakhare.textdrawable.util.ColorGenerator

/**
 * @author amulya
 * @datetime 17 Oct 2014, 4:02 PM
 */
class DrawableProvider(context: Context) {
    private val mGenerator: ColorGenerator = ColorGenerator.DEFAULT
    private val mContext: Context

    fun getRect(text: String): TextDrawable {
        return builder()
                .buildRect(text, mGenerator.getColor(text))
    }

    fun getRound(text: String): TextDrawable {
        return builder()
                .buildRound(text, mGenerator.getColor(text))
    }

    fun getRoundRect(text: String): TextDrawable {
        return builder()
                .buildRoundRect(text, mGenerator.getColor(text), toPx(10))
    }

    fun getRectWithBorder(text: String): TextDrawable {
        return builder()
                .beginConfig()
                .withBorder(toPx(2))
                .endConfig()
                .buildRect(text, mGenerator.getColor(text))
    }

    fun getRoundWithBorder(text: String): TextDrawable {
        return builder()
                .beginConfig()
                .withBorder(toPx(2))
                .endConfig()
                .buildRound(text, mGenerator.getColor(text))
    }

    fun getRoundRectWithBorder(text: String): TextDrawable {
        return builder()
                .beginConfig()
                .withBorder(toPx(2))
                .endConfig()
                .buildRoundRect(text, mGenerator.getColor(text), toPx(10))
    }

    val rectWithMultiLetter: TextDrawable
        get() {
            val text = "AK"
            return builder()
                    .beginConfig()
                    .fontSize(toPx(20))
                    .toUpperCase()
                    .endConfig()
                    .buildRect(text, mGenerator.getColor(text))
        }

    /*toPx(5)*/
    val roundWithCustomFont: TextDrawable
        get() {
            val text = "Bold"
            return builder()
                    .beginConfig()
                    .useFont(Typeface.DEFAULT)
                    .fontSize(toPx(15))
                    .textColor(-0xa7aa7)
                    .bold()
                    .endConfig()
                    .buildRect(text, Color.DKGRAY /*toPx(5)*/)
        }
    val rectWithCustomSize: Drawable
        get() {
            val leftText = "I"
            val rightText = "J"
            val builder = builder()
                    .beginConfig()
                    .width(toPx(29))
                    .withBorder(toPx(2))
                    .endConfig()
                    .rect()
            val left = builder
                    .build(leftText, mGenerator.getColor(leftText))
            val right = builder
                    .build(rightText, mGenerator.getColor(rightText))
            val layerList = arrayOf<Drawable>(
                    InsetDrawable(left, 0, 0, toPx(31), 0),
                    InsetDrawable(right, toPx(31), 0, 0, 0)
            )
            return LayerDrawable(layerList)
        }
    val rectWithAnimation: Drawable
        get() {
            val builder = builder()
                    .rect()
            val animationDrawable = AnimationDrawable()
            for (i in 10 downTo 1) {
                val frame = builder.build(i.toString(), mGenerator.randomColor)
                animationDrawable.addFrame(frame, 1200)
            }
            animationDrawable.isOneShot = false
            animationDrawable.start()
            return animationDrawable
        }

    private fun toPx(dp: Int): Int {
        val resources = mContext.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()
    }

    companion object {
        const val SAMPLE_RECT = 1
        const val SAMPLE_ROUND_RECT = 2
        const val SAMPLE_ROUND = 3
        const val SAMPLE_RECT_BORDER = 4
        const val SAMPLE_ROUND_RECT_BORDER = 5
        const val SAMPLE_ROUND_BORDER = 6
        const val SAMPLE_MULTIPLE_LETTERS = 7
        const val SAMPLE_FONT = 8
        const val SAMPLE_SIZE = 9
        const val SAMPLE_ANIMATION = 10
        const val SAMPLE_MISC = 11
    }

    init {
        mContext = context
    }
}