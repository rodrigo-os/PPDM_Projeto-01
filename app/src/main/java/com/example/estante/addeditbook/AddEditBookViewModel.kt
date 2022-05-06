package com.example.estante.addeditbook

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.estante.data.Book

class AddEditBookViewModel : ViewModel() {
    val id: MutableLiveData<Int> = MutableLiveData(4)
    val name: MutableLiveData<String> = MutableLiveData("")
    val genre: MutableLiveData<String> = MutableLiveData("")
    val price: MutableLiveData<String> = MutableLiveData("")

    fun insertBook(
        onInsertBook: (Book) -> Unit
    ) {
        val newBook = Book(
            id.value ?: return,
            name.value ?: return,
            genre.value ?: return,
            price.value ?: return
        )
        onInsertBook(newBook)
        var tempId: Int = id.value ?: return
        tempId++
        id.value = tempId

        name.value = ""
        genre.value = ""
        price.value = ""
    }

    fun updateBook(
        id: Int,
        onUpdateBook: (Book) -> Unit
    ){
        val book = Book(
            id,
            name.value ?: return,
            genre.value ?: return,
            price.value ?: return,
        )
        onUpdateBook(book)
    }
}