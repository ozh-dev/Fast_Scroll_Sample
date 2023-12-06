package ru.ozh.sample.scroller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.ozh.sample.scroller.controllers.LetterController
import ru.ozh.sample.scroller.controllers.WordController
import ru.ozh.sample.scroller.databinding.ActivityMainBinding
import ru.ozh.sample.scroller.recyclerview.StickyHeaderDecor
import ru.ozh.sample.scroller.recyclerview.WordDividerDrawer
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater,
            null,
            false
        )
    }

    private val letterController = LetterController()
    private val wordController = WordController()
    private val easyAdapter = EasyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding.listSwr) {
            setOnRefreshListener {
                postDelayed({
                    isRefreshing = false
                }, 1000)
            }
        }

        with(binding.listRv) {
            adapter = easyAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            binding.listFastScroller.attach(this)
            addItemDecoration(WordDividerDrawer(this@MainActivity))
            addItemDecoration(StickyHeaderDecor())
        }

        inflateList(stringList)
    }

    private fun inflateList(stringList: List<String>) {
        ItemList.create()
            .also { itemList ->
                // Формируем пары Буква -> Список слов на эту букву
                stringList.groupBy { it.first() }
                    .map { (letter, words) -> letter to words.take(8) }
                    .forEach { (letter, words) ->
                        itemList.add(letter, letterController)
                        itemList.addAll(words, wordController)
                    }
            }
            .also(easyAdapter::setItems)
    }
}




