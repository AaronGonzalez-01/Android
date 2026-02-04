package com.example.shoppingapplication.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shoppingapplication.data.local.ShoppingItem
import com.example.shoppingapplication.viewmodel.ShoppingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    viewModel: ShoppingViewModel,
    onAddClick: () -> Unit
) {
    val items by viewModel.items.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de la compra") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->
        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Lista vacía 🛒")
            }
        } else {
            // Fade in/out de la lista completa
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items) { item ->
                        ShoppingItemRow(
                            item = item,
                            onToggle = { viewModel.toggleBought(item) },
                            onDelete = { viewModel.deleteItem(item) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingItemRow(
    item: ShoppingItem,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    // Color animado según esté comprado o no
    val statusColor by animateColorAsState(
        targetValue = if (item.isBought) Color(0xFF2E7D32) else MaterialTheme.colorScheme.primary,
        label = "statusColor"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = item.name)
                Text(
                    text = if (item.isBought) "Comprado" else "Pendiente",
                    style = MaterialTheme.typography.bodySmall,
                    color = statusColor
                )
            }
            Spacer(modifier = Modifier.width(16.dp)) // separación extra del texto a los botones
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onToggle) {
                    Text("✔")
                }
                Button(onClick = onDelete) {
                    Text("🗑")
                }
            }
        }
    }
}
