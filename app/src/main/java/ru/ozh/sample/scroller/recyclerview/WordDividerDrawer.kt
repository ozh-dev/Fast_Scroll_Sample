package ru.ozh.sample.scroller.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import ru.ozh.sample.scroller.controllers.WordController

class WordDividerDrawer(private val context: Context) : RecyclerView.ItemDecoration() {

    private val dividerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val padding = context.resources.displayMetrics.density * 16

    init {
        dividerPaint.color = Color.LTGRAY
    }

    override fun onDrawOver(canvas: Canvas, recyclerView: RecyclerView, state: RecyclerView.State) {
        recyclerView.children.forEach { view ->
            val viewHolder = recyclerView.getChildViewHolder(view)

            if (viewHolder is WordController.Holder) {
                val startX = recyclerView.paddingLeft + padding
                val startY = view.bottom + view.translationY
                val stopX = recyclerView.width - recyclerView.paddingRight - padding
                val stopY = startY

                canvas.drawLine(startX, startY, stopX, stopY, dividerPaint)
            }
        }
    }
}