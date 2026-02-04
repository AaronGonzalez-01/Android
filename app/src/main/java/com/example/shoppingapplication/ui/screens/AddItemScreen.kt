package com.example.shoppingapplication.ui.screens
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class AddItemFormState(
    val name: String = "",
    val nameError: String? = null
)

private fun validateName(name: String): String? {
    return when {
        name.isBlank() -> "El nombre no puede estar vacío"
        name.length < 3 -> "Mínimo 3 caracteres"
        else -> null
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    title: String,
    onSave: (String) -> Unit,
    onBack: () -> Unit
) {
    var formState by remember { mutableStateOf(AddItemFormState()) }

    val animatedPadding by animateDpAsState(
        targetValue = if (formState.name.isBlank()) 24.dp else 48.dp,
        animationSpec = tween(durationMillis = 500),
        label = "cardPadding"
    )

    val isValid = formState.nameError == null && formState.name.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("<")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(animatedPadding),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = formState.name,
                        onValueChange = { newValue ->
                            val error = validateName(newValue)
                            formState = formState.copy(
                                name = newValue,
                                nameError = error
                            )
                        },
                        label = { Text("Nombre del producto") },
                        isError = formState.nameError != null,
                        supportingText = {
                            formState.nameError?.let {
                                Text(text = it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            if (isValid) {
                                onSave(formState.name.trim())
                            }
                        },
                        enabled = isValid,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}

