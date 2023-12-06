package ru.ozh.sample.scroller.recyclerview

/**
 * Используется вместе с [ScrollBarContainer]
 *
 * [RecyclerView.ViewHolder] Должен реализовывать этот интерфейс для отображение бейджа в [ScrollBarContainer]
 */
interface ScrollBadgeViewHolder {
    val badge: String?
}
