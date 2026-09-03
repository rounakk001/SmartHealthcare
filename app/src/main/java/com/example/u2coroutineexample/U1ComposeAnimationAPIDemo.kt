package com.example.u2coroutineexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.u2coroutineexample.ui.theme.U2CoroutineExampleTheme

class U1ComposeAnimationAPIDemo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            U2CoroutineExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    StudentProfile()
                }
            }
        }
    }
}

@Composable
fun StudentProfile() {
    var expanded by remember {mutableStateOf(false)}
    val buttonColor by animateColorAsState(
        targetValue =
            if (expanded)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.secondary,
        label = "Button Color"
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .animateContentSize()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Anuj Sharma",
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "BTech.-4th Year"
                )
                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("📧 Email: sharma@gmail.com")
                        Text("📱 Phone: 9876543210")
                        Text("📊 Attendance: 82%")
                    }
                }
                Button(
                    onClick = {
                        expanded = !expanded
                    },
                    modifier = Modifier.padding(top = 16.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = buttonColor
                    )
                ) {
                    Text(
                        text =
                            if (expanded)
                                "Hide Details"
                            else
                                "View Details"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview4() {
    U2CoroutineExampleTheme {
        StudentProfile()
    }
}