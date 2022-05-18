package com.amulyakhare.td.sample

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.amulyakhare.td.R
import com.amulyakhare.td.sample.sample.DrawableProvider
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator

class ListActivity : Activity() {
    // list of data items
    private val mDataList = listOf(
            ListData("Iron Man"),
            ListData("Captain America"),
            ListData("James Bond"),
            ListData("Harry Potter"),
            ListData("Sherlock Holmes"),
            ListData("Black Widow"),
            ListData("Hawk Eye"),
            ListData("Iron Man"),
            ListData("Guava"),
            ListData("Tomato"),
            ListData("Pineapple"),
            ListData("Strawberry"),
            ListData("Watermelon"),
            ListData("Pears"),
            ListData("Kiwi"),
            ListData("Plums")
    )

    // declare the color generator and drawable builder
    private val mColorGenerator: ColorGenerator = ColorGenerator.MATERIAL
    private lateinit var mDrawableBuilder: TextDrawable.IBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val intent: Intent = intent
        val type: Int = intent.getIntExtra(MainActivity.TYPE, DrawableProvider.SAMPLE_RECT)
        mDrawableBuilder = when (type) {
            DrawableProvider.SAMPLE_RECT -> TextDrawable.builder()
                    .rect()
            DrawableProvider.SAMPLE_ROUND_RECT -> TextDrawable.builder()
                    .roundRect(10)
            DrawableProvider.SAMPLE_ROUND -> TextDrawable.builder()
                    .round()
            DrawableProvider.SAMPLE_RECT_BORDER -> TextDrawable.builder()
                    .beginConfig()
                    .withBorder(4)
                    .endConfig()
                    .rect()
            DrawableProvider.SAMPLE_ROUND_RECT_BORDER -> TextDrawable.builder()
                    .beginConfig()
                    .withBorder(4)
                    .endConfig()
                    .roundRect(10)
            DrawableProvider.SAMPLE_ROUND_BORDER -> TextDrawable.builder()
                    .beginConfig()
                    .withBorder(4)
                    .endConfig()
                    .round()
            else -> throw Exception("Unknown type")
        }

        // init the list view and its adapter
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = SampleAdapter()
    }

    private inner class SampleAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return mDataList.size
        }

        override fun getItem(position: Int): ListData {
            return mDataList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val holder = (convertView?.tag as? ViewHolder) ?: ViewHolder(
                    view = View.inflate(this@ListActivity, R.layout.list_item_layout, null)
            )
            holder.view.tag = holder
            val item = getItem(position)

            // provide support for selected state
            updateCheckedState(holder, item)
            holder.imageView.setOnClickListener { // when the image is clicked, update the selected state
                val data = getItem(position)
                data.isChecked = !data.isChecked
                updateCheckedState(holder, data)
            }
            holder.textView.text = item.data
            return holder.view
        }

        private fun updateCheckedState(holder: ViewHolder, item: ListData) {
            if (item.isChecked) {
                holder.imageView.setImageDrawable(mDrawableBuilder.build(" ", -0x9e9e9f))
                holder.view.setBackgroundColor(HIGHLIGHT_COLOR)
                holder.checkIcon.visibility = View.VISIBLE
            } else {
                val drawable: TextDrawable = mDrawableBuilder.build(item.data[0].toString(), mColorGenerator.getColor(item.data))
                holder.imageView.setImageDrawable(drawable)
                holder.view.setBackgroundColor(Color.TRANSPARENT)
                holder.checkIcon.visibility = View.GONE
            }
        }
    }

    private class ViewHolder(val view: View) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textView: TextView = view.findViewById(R.id.textView)
        val checkIcon: ImageView = view.findViewById(R.id.check_icon)

    }

    private data class ListData(
            val data: String,
            var isChecked: Boolean = false,
    )

    companion object {
        private const val HIGHLIGHT_COLOR = -0x66641901
    }
}