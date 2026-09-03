package com.example.u2coroutineexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.u2coroutineexample.ui.theme.U2CoroutineExampleTheme

class U1SideEffectPractical : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            U2CoroutineExampleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SideEffectDemo()
                }
            }
        }
    }
}

@Composable
fun SideEffectDemo() {
    var count by remember { mutableIntStateOf(0) }
    // Runs after every successful recomposition
    SideEffect {
        Log.d("SideEffectDemo", "Screen recomposed. Count = $count")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Count = $count",
            style = MaterialTheme.typography.headlineMedium
        )
        Row {
            // Increment button
            Button(onClick = { count++ }) {
                Text("+")
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Decrement button
            Button(onClick = { count-- }) {
                Text("-")
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Reset button
            Button(onClick = { count = 0 }) {
                Text("Reset")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview10() {
    U2CoroutineExampleTheme {
        SideEffectDemo()
    }
}