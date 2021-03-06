package com.commit451.aloy

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Allows you to create a [RecyclerView.Adapter] without having to extend [RecyclerView.Adapter]
 */
open class AloyAdapter<T, VH : RecyclerView.ViewHolder>(
        private val onCreateViewHolder: (parent: ViewGroup, viewType: Int) -> VH,
        private val onBindViewHolder: (viewHolder: VH, position: Int, item: T) -> Unit) : RecyclerView.Adapter<VH>() {

    val items = mutableListOf<T>()

    var onGetItemViewType: ((position: Int) -> Int)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = onCreateViewHolder.invoke(parent, viewType)

    override fun onBindViewHolder(viewHolder: VH, position: Int) {
        val item = items[position]
        onBindViewHolder.invoke(viewHolder, position, item)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        val onGetItemViewType = onGetItemViewType
        if (onGetItemViewType != null) {
            return onGetItemViewType.invoke(position)
        } else {
            return super.getItemViewType(position)
        }
    }

    fun add(item: T, index: Int = items.size) {
        items.add(index, item)
        notifyItemInserted(index)
    }

    fun addAll(collection: Collection<T>, index: Int = items.size) {
        items.addAll(index, collection)
        notifyItemRangeInserted(index, items.size)
    }

    fun remove(item: T) {
        val index = items.indexOfFirst { it == item }
        items.removeAt(index)
        notifyItemRemoved(index)
    }

    fun update(item: T) {
        val index = items.indexOfFirst { it == item }
        notifyItemChanged(index)
    }

    fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }
}
