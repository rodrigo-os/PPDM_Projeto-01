package com.example.estante.booklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.estante.data.Book

class BookListViewModel : ViewModel() {
    private val _bookList: MutableLiveData<List<Book>> = MutableLiveData(
        listOf(
            Book(
                0,
                "Demolidor - A queda de Murdock",
                "História em quadrinhos",
                "80.90"
            ),
            Book(
                1,
                "Thor - O Carniceiro dos Deuses",
                "História em quadrinhos",
                "48.00"
            ),
            Book(
                2,
                "O Iluminado",
                "Terror psicológico",
                "40.50"
            ),
            Book(
                3,
                "Absolute Sandman - Volume 1",
                "História em quadrinhos",
                "187.09"
            ),
        )
    )

    private val _filterBy: MutableLiveData<String> = MutableLiveData("")

    val filterBy: LiveData<String>
        get() = _filterBy

    val bookList: LiveData<List<Book>>
        get() {
            return if (_filterBy.value == "")
                _bookList
            else {
                val list: List<Book> = _bookList.value?.filter { book ->
                    book.name.contains(_filterBy.value ?: "")
                } ?: listOf()
                MutableLiveData(list)
            }
        }

    fun updateFilter(newFilter: String) {
        _filterBy.value = newFilter
    }

    fun insertBook(book: Book) {
        val list: MutableList<Book> = _bookList.value?.toMutableList() ?: return
        list.add(book)
        _bookList.value = list
    }

    fun updateBook(updatedBook: Book){
        var pos = -1
        _bookList.value?.forEachIndexed { index, book ->
            if(updatedBook.id == book.id)
                pos = index
        }
        val list: MutableList<Book> = _bookList.value?.toMutableList() ?: return
        list.removeAt(pos)
        list.add(pos, updatedBook)
        _bookList.value = list
    }

    fun removeBook(id: Int){
        var pos = -1
        _bookList.value?.forEachIndexed { index, book ->
            if(id == book.id)
                pos = index
        }
        val list: MutableList<Book> = _bookList.value?.toMutableList() ?: return
        list.removeAt(pos)
        _bookList.value = list
    }


    fun getBook(id: Int): Book {
        _bookList.value?.forEach { book ->
            if (id == book.id)
                return book
        }
        return Book(
            -1,
            "",
            "",
            ""
        )
    }
}