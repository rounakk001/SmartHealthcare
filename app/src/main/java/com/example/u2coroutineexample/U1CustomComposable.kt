package com.example.u2coroutineexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.u2coroutineexample.ui.theme.U2CoroutineExampleTheme

// 1. DATA MODEL
data class Student(
    val id: Int,
    val name: String,
    val course: String,
    val semester: String,
    val cgpa: Double
)

class U1CustomComposable : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            U2CoroutineExampleTheme {
                StudentDashboard()
            }
        }
    }
}

// 2. MAIN SCREEN
@Composable
fun StudentDashboard() {
    val students = remember {
        listOf(
            Student(
                id = 1,
                name = "Rahul Sharma",
                course = "B.Tech CSE",
                semester = "5th Semester",
                cgpa = 8.7
            ),
            Student(
                id = 2,
                name = "Priya Singh",
                course = "B.Tech CSE",
                semester = "5th Semester",
                cgpa = 9.1
            ),
            Student(
                id = 3,
                name = "Amit Kumar",
                course = "B.Tech IT",
                semester = "5th Semester",
                cgpa = 8.5
            ),
            Student(
                id = 4,
                name = "Neha Gupta",
                course = "B.Tech CSE",
                semester = "5th Semester",
                cgpa = 9.3
            ),
            Student(
                id = 5,
                name = "Arjun Verma",
                course = "B.Tech CSE",
                semester = "5th Semester",
                cgpa = 8.8
            ),
            Student(
                id = 6,
                name = "Simran Kaur",
                course = "B.Tech IT",
                semester = "5th Semester",
                cgpa = 9.0
            )
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Student Dashboard",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "B.Tech Student Records",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
            // RESPONSIVE UI
            LazyVerticalGrid(
                columns = GridCells.Adaptive(
                    minSize = 280.dp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                contentPadding = PaddingValues(
                    bottom = 16.dp
                ),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Fixed: Correct import for 'items' added above
                items(
                    items = students,
                    key = { student ->
                        student.id
                    }
                ) { student ->
                    // CUSTOM COMPOSABLE
                    StudentCard(
                        student = student
                    )
                }
            }
        }
    }
}

@Composable
fun StudentCard(
    student: Student
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val cardColor by animateColorAsState(
        targetValue =
            if (expanded) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
        label = "Student Card Color"
    )

    Card(
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = student.name,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = student.course,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = student.semester,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = "CGPA: ${student.cgpa}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp)
            )

            if (expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Text(text = "Status: Active Student")
                    Text(
                        text = "Department: Computer Science",
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "Performance: Good",
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text(
                    text = if (expanded) "Hide Details" else "View Details"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    U2CoroutineExampleTheme {
        StudentDashboard()
    }
}
