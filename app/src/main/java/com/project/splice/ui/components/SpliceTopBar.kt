package com.project.splice.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.splice.R

@Composable
fun SpliceTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    titleColor: Color = Color.White,
    iconColor: Color = Color.White,
    profileIconBg: Color = Color(0xFF2A2A2A)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (onBackClick != null) {
            IconButton(onClick = onBackClick, modifier = Modifier.size(36.dp)) {
                Icon(
                    painter = painterResource(R.drawable.ic_wallet), // Replace with actual back icon if available
                    contentDescription = "Back",
                    tint = titleColor
                )
            }
            Spacer(Modifier.width(8.dp))
        }

        Text(
            text = title,
            color = titleColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
        )

        IconButton(onClick = onNotificationClick) {
            Icon(
                painter = painterResource(R.drawable.ic_wallet), // Replace with actual notification icon
                contentDescription = "Notifications",
                tint = iconColor,
                modifier = Modifier.size(24.dp),
            )
        }

        Spacer(Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(profileIconBg)
                .border(1.dp, Color(0xFF3A3A3A), CircleShape)
                .padding(4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Profile",
                tint = Color(0xFF8A8A8A),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
