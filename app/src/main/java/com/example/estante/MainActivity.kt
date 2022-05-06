package com.example.estante

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.defaultDecayAnimationSpec
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.estante.addeditbook.AddEditBookScreen
import com.example.estante.addeditbook.AddEditBookViewModel
import com.example.estante.booklist.BookListScreen
import com.example.estante.booklist.BookListViewModel
import com.example.estante.ui.theme.EstanteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bookListViewModel: BookListViewModel by viewModels()
        val addEditBookListViewModel: AddEditBookViewModel by viewModels()

        setContent {
            EstanteTheme {
                MyApp(
                    bookListViewModel,
                    addEditBookListViewModel
                )
            }
        }
    }
}

@Composable
fun MyApp(
    bookListViewModel: BookListViewModel,
    addEditBookListViewModel: AddEditBookViewModel
) {
    val navController = rememberNavController()
    Scaffold() {
        NavHost(navController = navController, startDestination = "booklist") {
            composable("booklist") {
                BookListScreen(navController, bookListViewModel)
            }
            composable(
                route = "addeditbook?id={id}",
                arguments = listOf(navArgument("id"){
                    defaultValue = -1
                    type = NavType.IntType
                })
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("id") ?: -1
                val book = bookListViewModel.getBook(id)
                AddEditBookScreen(
                    navController,
                    addEditBookListViewModel,
                    bookListViewModel::insertBook,
                    bookListViewModel::updateBook,
                    bookListViewModel::removeBook,
                    book
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EstanteTheme {

    }
}