package com.example.shoppingapplication.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shoppingapplication.data.local.ShoppingDao

class ShoppingViewModelFactory(
    private val dao: ShoppingDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShoppingViewModel(dao) as T
    }
}