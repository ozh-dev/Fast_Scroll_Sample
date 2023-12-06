package ru.ozh.sample.scroller.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.withStyledAttributes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.ozh.sample.scroller.R

/**
 * [SwipeRefreshLayout] позволяющий настроить зоны без скрола
 */
class BlindSwipeRefreshLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet
) : SwipeRefreshLayout(context, attributeSet) {

    private var blindLeftOffset: Int = 0
    private var blindTopOffset: Int = 0
    private var blindRightOffset: Int = 0
    private var blindBottomOffset: Int = 0

    init {
        context.withStyledAttributes(attributeSet, R.styleable.BlindSwipeRefreshLayout) {
            blindLeftOffset = getDimensionPixelOffset(R.styleable.BlindSwipeRefreshLayout_bswr_left_offset, 0)
            blindTopOffset = getDimensionPixelOffset(R.styleable.BlindSwipeRefreshLayout_bswr_top_offset, 0)
            blindRightOffset = getDimensionPixelOffset(R.styleable.BlindSwipeRefreshLayout_bswr_right_offset, 0)
            blindBottomOffset = getDimensionPixelOffset(R.styleable.BlindSwipeRefreshLayout_bswr_bottom_offset, 0)
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (!isInTouchableRect(event)) {
            return false
        }
        return super.onInterceptTouchEvent(event)
    }

    private fun isInTouchableRect(event: MotionEvent): Boolean {
        val (x, y) = event.x.toInt() to event.y.toInt()
        val horizontalTouchableZone = blindLeftOffset..(width - blindRightOffset)
        val verticalTouchableZone = blindTopOffset..(height - blindBottomOffset)
        return x in horizontalTouchableZone && y in verticalTouchableZone
    }
}
