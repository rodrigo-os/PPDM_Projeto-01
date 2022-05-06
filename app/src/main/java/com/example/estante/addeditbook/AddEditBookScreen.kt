package com.example.estante.addeditbook

import android.service.controls.actions.FloatAction
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.estante.booklist.BookListViewModel
import com.example.estante.data.Book

@Composable
fun AddEditBookScreen(
    navController: NavController,
    addEditBookListViewModel: AddEditBookViewModel,
    onInsertBook: (Book) -> Unit,
    onUpdateBook: (Book) -> Unit,
    onRemoveBook: (Int) -> Unit,
    book: Book
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if(book.id == -1)
                    addEditBookListViewModel.insertBook(onInsertBook)
                else
                    addEditBookListViewModel.updateBook(book.id, onUpdateBook)

                navController.navigate("booklist") {
                    popUpTo("booklist") {
                        inclusive = true
                    }
                }
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Confirmar")

            }
        }
    ) {
        addEditBookListViewModel.name.value = book.name
        addEditBookListViewModel.genre.value = book.genre
        addEditBookListViewModel.price.value = book.price



        AddEditBookForm(
            addEditBookListViewModel,
            book.id,
            onRemoveBook,
        ){
            navController.navigate("booklist") {
                popUpTo("booklist") {
                    inclusive = true
                }
            }
        }
    }


}

@Composable
fun AddEditBookForm(
    addEditBookListViewModel: AddEditBookViewModel,
    id: Int,
    onRemoveBook: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    var name = addEditBookListViewModel.name.observeAsState()
    var genre = addEditBookListViewModel.genre.observeAsState()
    var price = addEditBookListViewModel.price.observeAsState()

    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                label = {
                    Text(text = "Nome")
                },
                value = "${name.value}",
                onValueChange = { newName ->
                    addEditBookListViewModel.name.value = newName
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                label = {
                    Text(text = "Gênero")
                },
                value = "${genre.value}",
                onValueChange = { newGenre ->
                    addEditBookListViewModel.genre.value = newGenre
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Preço")
                },
                value = "${price.value}",
                onValueChange = { newPrice ->
                    addEditBookListViewModel.price.value = newPrice
                }
            )
        }
        if(id != -1){
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    onRemoveBook(id)
                    navigateBack()
                }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Apagar")
            }
        }

    }
}

/*
@Preview
@Composable
fun AddEditBookFormPreview() {
    val addEditBookViewModel: AddEditBookViewModel = viewModel()
    AddEditBookForm(addEditBookViewModel)
}
 */