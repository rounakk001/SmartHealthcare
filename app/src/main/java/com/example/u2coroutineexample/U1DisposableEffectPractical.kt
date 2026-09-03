package com.example.u2coroutineexample

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.u2coroutineexample.ui.theme.U2CoroutineExampleTheme

class U1DisposableEffectPractical : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            U2CoroutineExampleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DisposableEffectApp()
                }
            }
        }
    }
}

@Composable
fun DisposableEffectApp() {
    var showScreen by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showScreen = !showScreen }) {
            Text(if (showScreen) "Remove Screen" else "Show Screen")
        }
        if (showScreen) {
            MyScreen()
        }
    }
}
@Composable
fun MyScreen() {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        // Setup block
        Toast.makeText(context, "Screen Opened", Toast.LENGTH_SHORT).show()
        // Cleanup block
        onDispose {
            Toast.makeText(context, "Screen Closed", Toast.LENGTH_SHORT).show()
        }
    }

    Text(
        text = "Hello!This screen is using DisposableEffect.",
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall

    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview7() {
    U2CoroutineExampleTheme {
        DisposableEffectApp()
    }
}