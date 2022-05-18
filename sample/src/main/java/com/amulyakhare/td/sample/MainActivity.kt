package com.amulyakhare.td.sample

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.core.content.res.ResourcesCompat
import com.amulyakhare.td.R
import com.amulyakhare.td.sample.sample.DataItem
import com.amulyakhare.td.sample.sample.DataSource

class MainActivity : Activity(), OnItemClickListener {
    private lateinit var mDataSource: DataSource
    private lateinit var mListView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mListView = findViewById(R.id.listView)
        mDataSource = DataSource(this)
        mListView.adapter = SampleAdapter()
        mListView.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        val item = mListView.getItemAtPosition(position) as DataItem

        // if navigation is supported, open the next activity
        if (item.navigationInfo != DataSource.NO_NAVIGATION) {
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra(TYPE, item.navigationInfo)
            startActivity(intent)
        }
    }

    private inner class SampleAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return mDataSource.count
        }

        override fun getItem(position: Int): DataItem {
            return mDataSource.getItem(position)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val holder: ViewHolder = (convertView?.tag as? ViewHolder) ?: ViewHolder(
                    view = View.inflate(this@MainActivity, R.layout.list_item_layout, null),
            )
            holder.view.tag = holder
            val item = getItem(position)
            val drawable = item.drawable
            holder.imageView.setImageDrawable(drawable)
            holder.textView.text = item.label

            // if navigation is supported, show the ">" navigation icon

            holder.textView.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    if (item.navigationInfo != DataSource.NO_NAVIGATION) ResourcesCompat.getDrawable(resources, R.drawable.ic_action_next_item, theme)
                    else null,
                    null,
            )

            // fix for animation not playing for some below 4.4 devices
            if (drawable is AnimationDrawable) {
                holder.imageView.post {
                    drawable.stop()
                    drawable.start()
                }
            }
            return holder.view
        }
    }

    private class ViewHolder(val view: View) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textView: TextView = view.findViewById(R.id.textView)

    }

    companion object {
        const val TYPE = "TYPE"
    }
}