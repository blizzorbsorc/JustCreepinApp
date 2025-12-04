package com.example.justcreepinapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(onBackClick: () -> Unit) {
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    // Gradient background matching home screen
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF6650a4),
            Color(0xFFD0BCFF),
            Color(0xFFEFB8C8)
        ),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            item {
                // Top bar with back button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "Your Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
                    )
                }

                // Profile picture
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(Color.White.copy(alpha = 0.95f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile Picture",
                            modifier = Modifier.size(100.dp),
                            tint = Color(0xFF6650a4)
                        )
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Tap to change photo",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }

                Spacer(Modifier.height(16.dp))

                // Full Name Field
                Text(
                    text = "Full Name",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = { Text("Enter your full name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = Color(0xFF6650a4),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        focusedPlaceholderColor = Color(0xFF625b71).copy(alpha = 0.6f),
                        unfocusedPlaceholderColor = Color(0xFF625b71).copy(alpha = 0.6f)
                    )
                )

                Spacer(Modifier.height(16.dp))

                // Username Field
                Text(
                    text = "Username",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    placeholder = { Text("@username") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = Color(0xFF6650a4),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        focusedPlaceholderColor = Color(0xFF625b71).copy(alpha = 0.6f),
                        unfocusedPlaceholderColor = Color(0xFF625b71).copy(alpha = 0.6f)
                    )
                )

                Spacer(Modifier.height(16.dp))

                // Email Field
                Text(
                    text = "Email",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("your.email@example.com") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = Color(0xFF6650a4),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        focusedPlaceholderColor = Color(0xFF625b71).copy(alpha = 0.6f),
                        unfocusedPlaceholderColor = Color(0xFF625b71).copy(alpha = 0.6f)
                    )
                )

                Spacer(Modifier.height(16.dp))

                // Location Field
                Text(
                    text = "Location",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    placeholder = { Text("City, State") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = Color(0xFF6650a4),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        focusedPlaceholderColor = Color(0xFF625b71).copy(alpha = 0.6f),
                        unfocusedPlaceholderColor = Color(0xFF625b71).copy(alpha = 0.6f)
                    )
                )

                Spacer(Modifier.height(16.dp))

                // Bio Field
                Text(
                    text = "About You",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    placeholder = { Text("Tell us about yourself...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    minLines = 4,
                    maxLines = 6,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.95f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.95f),
                        focusedBorderColor = Color(0xFF6650a4),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                        focusedPlaceholderColor = Color(0xFF625b71).copy(alpha = 0.6f),
                        unfocusedPlaceholderColor = Color(0xFF625b71).copy(alpha = 0.6f)
                    )
                )

                Spacer(Modifier.height(32.dp))

                // Save Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(8.dp)
                ) {
                    Button(
                        onClick = {
                            // TODO: Save profile data
                            onBackClick()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.95f)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Text(
                            text = "Save Profile",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6650a4)
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}