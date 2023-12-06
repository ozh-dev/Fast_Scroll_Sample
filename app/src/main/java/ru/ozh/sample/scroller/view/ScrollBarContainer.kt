package ru.ozh.sample.scroller.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.withStyledAttributes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.ozh.sample.scroller.R
import ru.ozh.sample.scroller.recyclerview.ScrollBadgeViewHolder
import kotlin.math.max
import kotlin.math.min
import ru.surfstudio.android.easyadapter.EasyAdapter

/**
 *   Скроллер
 *   Позволяет сделать фаст скрол с кастомным скролбаром и бейджиком
 */
class ScrollBarContainer(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs), View.OnScrollChangeListener {

    private var touchTargetWidth: Int = 0

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EasyAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var badgeTv: TextView? = null
    private lateinit var scrollBarView: View
    private var dragStarted = false

    init {
        context.withStyledAttributes(attrs, R.styleable.ScrollBarContainer) {
            touchTargetWidth = getDimensionPixelOffset(R.styleable.ScrollBarContainer_fsv_touch_zone_width, 0)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        badgeTv = findViewById(R.id.scroll_bar_container_badge)
        scrollBarView = findViewById(R.id.scroll_bar_container_scroll_bar)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (!dragStarted && width - touchTargetWidth - event.x > 0) {
            super.onTouchEvent(event)
        } else when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                dragStarted = true
                scrollBarView.isSelected = true
                moveScrollBarAndBadge(event.y)
                scrollRecyclerViewTo(event.y)
                true
            }
            MotionEvent.ACTION_MOVE -> {
                moveScrollBarAndBadge(event.y)
                scrollRecyclerViewTo(event.y)
                updateBadgeText()
                badgeTv?.visibility = View.VISIBLE
                true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                dragStarted = false
                badgeTv?.visibility = View.INVISIBLE
                scrollBarView.isSelected = false
                true
            }
            else -> super.onTouchEvent(event)
        }
    }

    override fun onScrollChange(v: View, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        moveScrollBarAndBadgeOnScrollChange()
    }

    fun attach(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        adapter = recyclerView.adapter as EasyAdapter
        layoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.setOnScrollChangeListener(this)
        visibility = View.VISIBLE
    }

    private fun scrollRecyclerViewTo(y: Float) {
        val itemCount = adapter.itemCount
        val scrolledPosition = calculateScrollProgress(y) * itemCount.toFloat()
        val targetPos = getValueInRange(0, itemCount - 1, scrolledPosition.toInt())
        layoutManager.scrollToPositionWithOffset(targetPos, 0)
    }

    private fun updateBadgeText() {
        val firstCompletelyVisible = layoutManager.findFirstCompletelyVisibleItemPosition()
        val vh = recyclerView.findViewHolderForLayoutPosition(firstCompletelyVisible)
        if (vh is ScrollBadgeViewHolder) {
            badgeTv?.text = (vh as ScrollBadgeViewHolder).badge
        }
    }

    /**
     * @return [0..1]
     */
    private fun calculateScrollProgress(y: Float): Float {
        return when {
            scrollBarView.y == 0f -> {
                0f
            }
            scrollBarView.y + scrollBarView.height >= height -> {
                1f
            }
            else -> {
                y / height.toFloat()
            }
        }
    }

    private fun moveScrollBarAndBadgeOnScrollChange() {
        if (!scrollBarView.isSelected) {
            val verticalScrollOffset = recyclerView.computeVerticalScrollOffset()
            val verticalScrollRange = recyclerView.computeVerticalScrollRange()
            val proportion = verticalScrollOffset.toFloat() / (verticalScrollRange.toFloat() - height)
            moveScrollBarAndBadge(height * proportion)
        }
    }

    private fun moveScrollBarAndBadge(y: Float) {
        val scrollBarHeight = scrollBarView.height
        val containerHeight = badgeTv?.height ?: 0
        scrollBarView.y =
                getValueInRange(0, height - scrollBarHeight, (y - scrollBarHeight / 2).toInt()).toFloat()
        badgeTv?.y =
                getValueInRange(0, height - containerHeight - scrollBarHeight / 2, (y - containerHeight).toInt()).toFloat()
    }

    private fun getValueInRange(min: Int, max: Int, value: Int): Int {
        val minimum = max(min, value)
        return min(minimum, max)
    }
}
