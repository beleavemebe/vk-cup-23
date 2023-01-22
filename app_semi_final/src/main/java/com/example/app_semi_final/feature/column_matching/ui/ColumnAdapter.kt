package com.example.app_semi_final.feature.column_matching.ui

import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ColumnAdapter(
    private val touchHelper: ItemTouchHelper,
) : ListAdapter<String, ColumnAdapter.ColumnItemViewHolder>(StringDiffUtilCallback()) {
    var isMatchCompleted by mutableStateOf(true)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ColumnItemViewHolder {
        return ColumnItemViewHolder(ComposeView(parent.context))
    }

    override fun onBindViewHolder(holder: ColumnItemViewHolder, position: Int) {
        val text = getItem(position)
        holder.bind(text)
    }

    inner class ColumnItemViewHolder(
        private val view: ComposeView,
    ) : RecyclerView.ViewHolder(view) {
        fun bind(text: String) {
            view.setContent {
                DraggableColumnItem(
                    text = text,
                    isMatchedCorrectly = isMatchCompleted,
                    startDragging = {
                        if (!isMatchCompleted) {
                            touchHelper.startDrag(this)
                        }
                    }
                )
            }
        }
    }

    class StringDiffUtilCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    class SwapCallback(
        private val swap: (from: Int, to: Int) -> Unit,
    ) : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
        override fun isLongPressDragEnabled() = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder,
        ): Boolean {
            val from = viewHolder.absoluteAdapterPosition
            val to = target.absoluteAdapterPosition
            swap(from, to)
            return true
        }
    }
}