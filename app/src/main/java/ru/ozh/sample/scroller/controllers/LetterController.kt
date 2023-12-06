package ru.ozh.sample.scroller.controllers

import android.view.ViewGroup
import android.widget.TextView
import ru.ozh.sample.scroller.R
import ru.ozh.sample.scroller.recyclerview.ScrollBadgeViewHolder
import ru.ozh.sample.scroller.recyclerview.StickyHolder
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

/**
 * Котроллер для отображения буквы
 */
class LetterController() : BindableItemController<Char, LetterController.Holder>() {

    override fun getItemId(data: Char): Any {
        return data.hashCode()
    }

    override fun createViewHolder(parent: ViewGroup): Holder {
        return Holder(parent)
    }

    inner class Holder(
            parent: ViewGroup
    ) : BindableViewHolder<Char>(parent, R.layout.list_item_controller_letter),
        ScrollBadgeViewHolder, StickyHolder {

        private val letterTv = itemView.findViewById<TextView>(R.id.letter_tv)

        private var label = ""

        override fun bind(data: Char) {
            label = data.toString()
            letterTv.text = data.toString()
        }

        override val badge: String
            get() = label
    }
}
