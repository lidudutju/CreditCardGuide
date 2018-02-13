package lidu.me.creditcardguide.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import lidu.me.creditcardguide.ui.AnkoViewHolder

/**
 * Created by lidu on 2018/1/31.
 */
open class AnkoListAdapter<in VM : Any, out VH : AnkoViewHolder<VM>>(
        private val context: Context,
        private var data: List<VM>,
        private val factory: (ctx: Context) -> VH
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return if (convertView == null) {
            val viewHolder = factory(context)
            val view = viewHolder.view
            view.tag = viewHolder
            viewHolder.bindData(data[position])
            view
        } else {
            (convertView.tag as VH).bindData(data[position])
            convertView
        }
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return data.size
    }

    public fun updateData(newData: List<VM>) {
        data = newData
        notifyDataSetChanged()
    }
}