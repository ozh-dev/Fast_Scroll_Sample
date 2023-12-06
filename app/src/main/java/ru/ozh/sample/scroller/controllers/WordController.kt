package ru.ozh.sample.scroller.controllers

import android.view.ViewGroup
import android.widget.TextView
import ru.ozh.sample.scroller.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

/**
 * Котроллер для отображения слова
 */
class WordController : BindableItemController<String, WordController.Holder>() {

    override fun getItemId(data: String): Any {
        return data.hashCode()
    }

    override fun createViewHolder(parent: ViewGroup): Holder {
        return Holder(parent)
    }

    inner class Holder(parent: ViewGroup) : BindableViewHolder<String>(parent,
        R.layout.list_item_controller_word
    ) {

        private val textView = itemView.findViewById<TextView>(R.id.name_tv)

        override fun bind(data: String) {
            textView.text = data
        }
    }
}
