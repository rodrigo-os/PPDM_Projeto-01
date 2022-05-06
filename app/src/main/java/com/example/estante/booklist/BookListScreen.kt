package com.example.estante.booklist

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.estante.data.Book

@Composable
fun BookListScreen(
    navController: NavController,
    bookListViewModel: BookListViewModel,
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addeditbook")
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Book")

            }
        }
    ) {
        val bookList by bookListViewModel.bookList.observeAsState(listOf())
        val filter by bookListViewModel.filterBy.observeAsState("")

        Column() {
            SearchBook(
                filter,
                bookListViewModel::updateFilter
            )
            BookList(
                books = bookList,
                navController = navController
            )
        }

    }
}

@Composable
fun SearchBook(
    filter: String,
    onFilterChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        label = {
            Row() {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                Text(text = "Search")
            }
        },
        value = filter,
        onValueChange = onFilterChange
    )
}


@Composable
fun BookList(
    books: List<Book>,
    navController: NavController
) {
    LazyColumn() {
        items(books) { book ->
            BookEntry(book = book) {
                navController.navigate("addeditbook?id=${book.id}")
            }

        }
    }
}

@Composable
fun BookEntry(
    book: Book,
    onEdit: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(2.dp)
            .clickable {
                expanded = !expanded
            }
            .animateContentSize(
                spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column() {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(6.dp)
                        .clip(CircleShape)
                        .size(60.dp)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${book.name[0].uppercase()}",
                        style = MaterialTheme.typography.h3
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .weight(1f),
                    text = book.name,
                    style = MaterialTheme.typography.h6
                        .copy(fontWeight = FontWeight.Bold)
                )
                if (expanded) {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(31.dp)
                            .clickable {
                                onEdit()
                            },

                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar"
                    )
                }

            }
            if (expanded) {
                Text(
                    modifier = Modifier.padding(start = 6.dp),
                    text = book.genre,
                    style = MaterialTheme.typography.subtitle2.copy(
                        color = Color.LightGray
                    )
                )
                Text(
                    modifier = Modifier.padding(start = 6.dp, bottom = 6.dp, end = 6.dp),
                    text = book.price,
                    style = MaterialTheme.typography.subtitle2.copy(
                        color = Color.LightGray
                    )
                )
            }

        }

    }

}

/*
@Preview
@Composable
fun BookListScreenPreview() {
    val viewModel: BookListViewModel = viewModel()
    BookListScreen(rememberNavController(), viewModel)
}


@Preview
@Composable
fun BookListPreview() {
    val viewModel: BookListViewModel = viewModel()
    val bookList by viewModel.bookList.observeAsState()
    BookList(books = bookList ?: listOf())
}

@Preview
@Composable
fun BookEntryPreview() {
    BookEntry(
        Book(
            0,
            "Demolidor - A queda de Murdock",
            "Quadrinho",
            "80.90"
        )
    )
}

 */