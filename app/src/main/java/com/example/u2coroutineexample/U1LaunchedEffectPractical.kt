package com.example.u2coroutineexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.u2coroutineexample.ui.theme.U2CoroutineExampleTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class U1LaunchedEffectPractical : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            U2CoroutineExampleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LaunchedEffectExample()

                }
            }
        }
    }
}

@Composable
fun LaunchedEffectExample() {
    var isLoaded by remember { mutableStateOf(false) }
    // Runs when the composable enters the composition
    LaunchedEffect(isLoaded) {
        if (!isLoaded) {
            delay(3000L.milliseconds)
            isLoaded = true
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isLoaded) {
                "Welcome to Jetpack Compose!"
            } else {
                "Loading..."
            },
            style = MaterialTheme.typography.headlineMedium
        )
        Button(
            onClick = { isLoaded = false }
        ) {
            Text("Reset")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview8() {
    U2CoroutineExampleTheme {
        LaunchedEffectExample()
    }
}