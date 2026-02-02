package com.example.shoppingapplication
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.shoppingapplication.data.local.AppDatabase
import com.example.shoppingapplication.ui.screens.ShoppingListScreen
import com.example.shoppingapplication.viewmodel.ShoppingViewModel
import com.example.shoppingapplication.viewmodel.ShoppingViewModelFactory

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "shopping_db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: ShoppingViewModel = viewModel(
                factory = ShoppingViewModelFactory(database.shoppingDao())
            )

            ShoppingListScreen(viewModel)
        }
    }
}