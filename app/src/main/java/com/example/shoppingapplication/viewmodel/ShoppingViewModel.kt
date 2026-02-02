package com.example.shoppingapplication.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapplication.data.local.ShoppingDao
import com.example.shoppingapplication.data.local.ShoppingItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShoppingViewModel(private val dao: ShoppingDao) : ViewModel() {

    val items = dao.getAllItems()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addItem(name: String) = viewModelScope.launch {
        dao.insert(ShoppingItem(name = name))
    }

    fun toggleBought(item: ShoppingItem) = viewModelScope.launch {
        dao.update(item.copy(isBought = !item.isBought))
    }

    fun deleteItem(item: ShoppingItem) = viewModelScope.launch {
        dao.delete(item)
    }
}