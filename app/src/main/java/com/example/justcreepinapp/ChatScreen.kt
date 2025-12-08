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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ChatMessage(
    val id: String,
    val username: String,
    val message: String,
    val timestamp: Long,
    val isCurrentUser: Boolean = false
)

@Composable
fun ChatScreen(onBackClick: () -> Unit) {
    var messageText by remember { mutableStateOf("") }
    val youText = stringResource(R.string.chat_you) // Get string outside onClick
    var messages by remember {
        mutableStateOf(listOf(
            ChatMessage(
                id = "1",
                username = "Sarah M.",
                message = "Just saw an amazing Halloween display on Maple Street! 🎃",
                timestamp = System.currentTimeMillis() - 3600000,
                isCurrentUser = false
            ),
            ChatMessage(
                id = "2",
                username = "Mike R.",
                message = "Thanks for sharing! I'll check it out tonight",
                timestamp = System.currentTimeMillis() - 3000000,
                isCurrentUser = false
            ),
            ChatMessage(
                id = "3",
                username = "You",
                message = "Anyone know good spots for candy this year?",
                timestamp = System.currentTimeMillis() - 1800000,
                isCurrentUser = true
            )
        ))
    }

    val listState = rememberLazyListState()

    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                        contentDescription = stringResource(R.string.back),
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.chat_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = stringResource(R.string.chat_messages_count, messages.size),
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            // Messages list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messages) { message ->
                    MessageBubble(message)
                }
                item {
                    Spacer(Modifier.height(8.dp))
                }
            }

            // Message input
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.95f))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text(stringResource(R.string.chat_type_message)) },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        focusedBorderColor = Color(0xFF6650a4),
                        unfocusedBorderColor = Color.Transparent
                    ),
                    maxLines = 3
                )

                IconButton(
                    onClick = {
                        if (messageText.isNotBlank()) {
                            messages = messages + ChatMessage(
                                id = System.currentTimeMillis().toString(),
                                username = youText,
                                message = messageText,
                                timestamp = System.currentTimeMillis(),
                                isCurrentUser = true
                            )
                            messageText = ""
                        }
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .background(Color(0xFF6650a4), RoundedCornerShape(50))
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = stringResource(R.string.send),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    val alignment = if (message.isCurrentUser) Alignment.End else Alignment.Start
    val bubbleColor = if (message.isCurrentUser)
        Color(0xFF6650a4)
    else
        Color.White.copy(alpha = 0.95f)
    val textColor = if (message.isCurrentUser) Color.White else Color(0xFF1C1B1F)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Card(
            modifier = Modifier.widthIn(max = 280.dp),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isCurrentUser) 16.dp else 4.dp,
                bottomEnd = if (message.isCurrentUser) 4.dp else 16.dp
            ),
            colors = CardDefaults.cardColors(containerColor = bubbleColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                if (!message.isCurrentUser) {
                    Text(
                        text = message.username,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6650a4)
                    )
                    Spacer(Modifier.height(4.dp))
                }
                Text(
                    text = message.message,
                    fontSize = 14.sp,
                    color = textColor
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = formatTimestamp(message.timestamp),
                    fontSize = 10.sp,
                    color = textColor.copy(alpha = 0.6f)
                )
            }
        }
    }
}

fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60000 -> "Just now"
        diff < 3600000 -> "${diff / 60000}m ago"
        diff < 86400000 -> "${diff / 3600000}h ago"
        else -> SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()).format(Date(timestamp))
    }
}