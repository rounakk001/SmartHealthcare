package com.example.u2coroutineexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                    CoroutineDemoScreen()
                }
            }
        }
    }
}

class CoroutineDemoViewModel : ViewModel() {
    var status by mutableStateOf("Idle")
        private set

    var currentDispatcher by mutableStateOf("Dispatchers.Main")
        private set

    var itemsList by mutableStateOf<List<String>>(emptyList())
        private set

    var isLoading by mutableStateOf(value = false)
        private set

    private var loadDataJob: Job? = null

    fun loadData() {
        // Cancel any existing job before starting a new one
        loadDataJob?.cancel()

        loadDataJob = viewModelScope.launch(Dispatchers.Main) {
            isLoading = true
            status = "Loading..."
            itemsList = emptyList()
            currentDispatcher = "${coroutineContext[CoroutineDispatcher]}"

            try {
                // Switch to Dispatchers.IO for background data fetching
                val data = withContext(Dispatchers.IO) {
                    currentDispatcher = "${coroutineContext[CoroutineDispatcher]}"
                    delay(2000L) // Simulate fetching data
                    listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
                }

                // Switch to Dispatchers.Main to update UI
                withContext(Dispatchers.Main) {
                    currentDispatcher = "${coroutineContext[CoroutineDispatcher]}"
                    itemsList = data
                    status = "Completed"
                    isLoading = false
                }
            } catch (e: CancellationException) {
                status = "Cancelled"
                currentDispatcher = "Cancelled"
                isLoading = false
                throw e
            } catch (e: Exception) {
                status = "Error: ${e.localizedMessage}"
                isLoading = false
            }
        }
    }

    fun cancelLoading() {
        if (loadDataJob?.isActive == true) {
            loadDataJob?.cancel()
            status = "Cancelled"
            currentDispatcher = "Cancelled"
            isLoading = false
        }
    }
}

@Composable
fun CoroutineDemoScreen(
    viewModel: CoroutineDemoViewModel = viewModel()
) {
    val status = viewModel.status
    val dispatcherName = viewModel.currentDispatcher
    val itemsList = viewModel.itemsList
    val isLoading = viewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Title Header Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Coroutine Demo",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Basics, Scope & Context in Jetpack Compose",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }

        // Data Loader Details Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Data Loader:",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Status: ",
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = status,
                        color = when (status) {
                            "Completed" -> Color(0xFF2E7D32)
                            "Loading..." -> Color(0xFF0288D1)
                            "Cancelled" -> Color(0xFFD32F2F)
                            else -> MaterialTheme.colorScheme.onSurface
                        },
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Current Dispatcher / Context:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = dispatcherName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Action Buttons Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { viewModel.loadData() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32)
                )
            ) {
                Text("Load Data")
            }

            Button(
                onClick = { viewModel.cancelLoading() },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                )
            ) {
                Text("Cancel")
            }
        }

        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }

        // Items Section
        Text(
            text = "Items:",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (itemsList.isEmpty() && !isLoading) {
            Text(
                text = if (status == "Cancelled") "Operation was cancelled." else "No items loaded yet. Click 'Load Data'.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(itemsList) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

