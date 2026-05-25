package com.project.splice.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GroupRow(
    name: String,
    balance: String,
    dotColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    nameColor: Color = Color.White,
    positiveColor: Color = Color(0xFF4CAF50),
    negativeColor: Color = Color(0xFFFF5252),
    neutralColor: Color = Color(0xFF8A8A8A)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Colored dot
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(dotColor),
        )
        Spacer(Modifier.width(14.dp))
        Text(
            text = name,
            color = nameColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = balance,
            color = when {
                balance.startsWith("+") -> positiveColor
                balance.startsWith("-") -> negativeColor
                else -> neutralColor
            },
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}
