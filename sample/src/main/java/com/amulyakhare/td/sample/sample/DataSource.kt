package com.amulyakhare.td.sample.sample

import android.content.Context
import android.graphics.drawable.Drawable

/**
 * @author amulya
 * @datetime 17 Oct 2014, 3:49 PM
 */
class DataSource(context: Context) {
    private val mDataSource: ArrayList<DataItem>
    private val mProvider: DrawableProvider

    fun getItem(position: Int): DataItem {
        return mDataSource[position]
    }

    val count: Int
        get() = mDataSource.size

    private fun itemFromType(type: Int): DataItem {
        return when (type) {
            DrawableProvider.SAMPLE_RECT -> DataItem(
                    label = "Rectangle with Text",
                    drawable = mProvider.getRect("A"),
                    navigationInfo = type,
            )
            DrawableProvider.SAMPLE_ROUND_RECT -> DataItem(
                    label = "Round Corner with Text",
                    drawable = mProvider.getRoundRect("B"),
                    navigationInfo = type,
            )
            DrawableProvider.SAMPLE_ROUND -> DataItem(
                    label = "Round with Text",
                    drawable = mProvider.getRound("C"),
                    navigationInfo = type,
            )
            DrawableProvider.SAMPLE_RECT_BORDER -> DataItem(
                    label = "Rectangle with Border",
                    drawable = mProvider.getRectWithBorder("D"),
                    navigationInfo = type,

                    )
            DrawableProvider.SAMPLE_ROUND_RECT_BORDER -> DataItem(
                    label = "Round Corner with Border",
                    drawable = mProvider.getRoundRectWithBorder("E"),
                    navigationInfo = type,

                    )
            DrawableProvider.SAMPLE_ROUND_BORDER -> DataItem(
                    label = "Round with Border",
                    drawable = mProvider.getRoundWithBorder("F"),
                    navigationInfo = type,

                    )
            DrawableProvider.SAMPLE_MULTIPLE_LETTERS -> DataItem(
                    label = "Support multiple letters",
                    drawable = mProvider.rectWithMultiLetter,
                    navigationInfo = NO_NAVIGATION,
            )
            DrawableProvider.SAMPLE_FONT -> DataItem(
                    label = "Support variable font styles",
                    drawable = mProvider.roundWithCustomFont,
                    navigationInfo = NO_NAVIGATION
            )
            DrawableProvider.SAMPLE_SIZE -> DataItem(
                    label = "Support for custom size",
                    drawable = mProvider.rectWithCustomSize,
                    navigationInfo = NO_NAVIGATION,
            )
            DrawableProvider.SAMPLE_ANIMATION -> DataItem(
                    label = "Support for animations",
                    drawable = mProvider.rectWithAnimation,
                    navigationInfo = NO_NAVIGATION,
            )
            DrawableProvider.SAMPLE_MISC -> DataItem(
                    label = "Miscellaneous",
                    drawable = mProvider.getRect("\u03c0"),
                    navigationInfo = NO_NAVIGATION,
            )
            else -> DataItem(
                    null, null, NO_NAVIGATION,
            )
        }
    }

    companion object {
        const val NO_NAVIGATION = -1
    }

    init {
        mProvider = DrawableProvider(context)
        mDataSource = ArrayList()
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_RECT))
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ROUND_RECT))
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ROUND))
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_RECT_BORDER))
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ROUND_RECT_BORDER))
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ROUND_BORDER))
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_MULTIPLE_LETTERS))
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_FONT))
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_SIZE))
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_ANIMATION))
        mDataSource.add(itemFromType(DrawableProvider.SAMPLE_MISC))
    }
}