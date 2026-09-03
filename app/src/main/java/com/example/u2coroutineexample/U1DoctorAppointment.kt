package com.example.u2coroutineexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.u2coroutineexample.ui.theme.U2CoroutineExampleTheme

// 1. DATA MODEL
data class Doctor(
    val id: Int,
    val name: String,
    val address: String,
    val appointmentSlot: String
)

class U1DoctorAppointment : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            U2CoroutineExampleTheme {
                DoctorDashboard()
            }
        }
    }
}

@Composable
fun DoctorDashboard() {
    val doctors = remember {
        listOf(
            Doctor(1, "Dr. Aman Gupta", "City Hospital, Block A", "10:00 AM - 11:00 AM"),
            Doctor(2, "Dr. Sneha Varma", "Green Valley Clinic", "12:00 PM - 01:00 PM"),
            Doctor(3, "Dr. Vikram Singh", "Metro Healthcare", "03:00 PM - 04:00 PM"),
            Doctor(4, "Dr. Pooja Reddy", "Lifeline Hospital", "05:00 PM - 06:00 PM"),
            Doctor(5, "Dr. Karan Mehta", "Sunrise Clinic", "09:00 AM - 10:00 AM"),
            Doctor(6, "Dr. Ishani Sen", "Wellness Center", "02:00 PM - 03:00 PM")
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
                text = "Smart Healthcare",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Book Your Appointment",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 300.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(doctors, key = { it.id }) { doctor ->
                    DoctorCard(doctor = doctor)
                }
            }
        }
    }
}

@Composable
fun DoctorCard(doctor: Doctor) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var isAvailable by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }

    val cardColor by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.secondaryContainer 
                      else MaterialTheme.colorScheme.surfaceVariant,
        label = "Card Color Animation"
    )

    Card(
        modifier = Modifier
            .animateContentSize()
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = doctor.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Slot: ${doctor.appointmentSlot}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )

            if (expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(text = "Address: ${doctor.address}")
                    Text(text = "Specialist: Senior Consultant")
                    Text(
                        text = if (isAvailable) "Status: Available" else "Status: Booked",
                        color = if (isAvailable) Color(0xFF2E7D32) else Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    if (isAvailable) {
                        Button(
                            onClick = { showDialog = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                        ) {
                            Text("Book Now")
                        }
                    }
                }

                if (isAvailable && showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text("Confirm Appointment") },
                        text = { Text("Do you want to book an appointment with ${doctor.name} at ${doctor.appointmentSlot}?") },
                        confirmButton = {
                            TextButton(onClick = {
                                isAvailable = false
                                showDialog = false
                                Toast.makeText(context, "Appointment Booked", Toast.LENGTH_SHORT).show()
                            }) {
                                Text("Confirm")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showDialog = false
                                Toast.makeText(context, "Booking Cancelled", Toast.LENGTH_SHORT).show()
                            }) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (expanded) "Hide Details" else "View Details")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorPreview() {
    U2CoroutineExampleTheme {
        DoctorDashboard()
    }
}
