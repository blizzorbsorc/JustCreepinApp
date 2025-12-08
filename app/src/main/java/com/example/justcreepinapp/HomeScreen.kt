package com.example.justcreepinapp

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.justcreepinapp.ui.theme.AppTheme
import com.example.justcreepinapp.ui.theme.DeepPurple
import com.example.justcreepinapp.ui.theme.SoftPink

// Home Screen
@Composable
fun HomeScreen(viewModel: AppViewModel, onHolidayClick: () -> Unit) {
    // Gradient background matching splash screen
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF6650a4), // Purple40 - deep purple
            Color(0xFFD0BCFF), // Purple80 - light purple
            Color(0xFFEFB8C8)  // Pink80 - soft pink
        ),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = AppTheme.gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Navigation Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo on the left
                Box(
                    modifier = Modifier.size(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo2),
                        contentDescription = stringResource(R.string.app_logo),
                        modifier = Modifier.size(100.dp)
                    )
                }

                // User icon on the right
                IconButton(
                    onClick = { /* TODO: Handle user profile click */ },
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                            CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = stringResource(R.string.user_profile),
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            // Title Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 8.dp)
            ) {

                Text(
                    text = stringResource(R.string.just_creepin),
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = 1.sp
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.discover_holiday_magic),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.95f),
                    letterSpacing = 0.5.sp
                )
            }

            // Middle Section - Holiday Buttons
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // Halloween Card Button
                HolidayCard(
                    title = stringResource(R.string.button_halloween),
                    subtitle = stringResource(R.string.find_candy_spooky_decorations),
                    emoji = "🎃",
                    //highlightColor = DeepPurple,
                    //backgroundColor = DeepPurple,
                    backgroundColor = SoftPink,
                    onClick = {
                        viewModel.loadLocations("Halloween")
                        onHolidayClick()
                    }
                )

                Spacer(Modifier.height(20.dp))

                // Christmas Card Button
                HolidayCard(
                    title = stringResource(R.string.button_christmas),
                    subtitle = stringResource(R.string.explore_festive_lights_displays),
                    emoji = "🎄",
                    backgroundColor = SoftPink,
                    onClick = {
                        viewModel.loadLocations("Christmas")
                        onHolidayClick()
                    }
                )
            }

            // Bottom Section - Features
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FeatureItem(
                        icon = Icons.Default.Place,
                        text = stringResource(R.string.map_view)
                    )
                    FeatureItem(
                        icon = Icons.Default.Share,
                        text = stringResource(R.string.community)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.share_discover_the_best_holiday_spots),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    //color = Color.White.copy(alpha = 0.85f),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// Holiday Card
@Composable
fun HolidayCard(
    title: String,
    subtitle: String,
    emoji: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = emoji,
                    fontSize = 48.sp
                )
                Spacer(Modifier.width(16.dp))
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = title,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = backgroundColor,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = backgroundColor.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

// Feature Item
@Composable
fun FeatureItem(icon: ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.25f), CircleShape),
                //.background(Color.White.copy(alpha = 0.25f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                //tint = Color.White,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            //color = Color.White.copy(alpha = 0.9f)
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f)
        )
    }
}