package com.project.splice.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.splice.R

@Composable
fun ActivityRow(
    title: String,
    subtitle: String,
    amount: String,
    label: String,
    modifier: Modifier = Modifier,
    isNegative: Boolean = false,
    icon: Int = R.drawable.ic_wallet,
    iconBg: Color = Color(0xFF2A2A2A),
    titleColor: Color = Color.White,
    subtitleColor: Color = Color(0xFF8A8A8A),
    positiveColor: Color = Color(0xFF4CAF50),
    negativeColor: Color = Color(0xFFFF5252)
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Icon / Avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(iconBg),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = subtitleColor,
                modifier = Modifier.size(22.dp),
            )
        }

        Spacer(Modifier.width(14.dp))

        // Title + Subtitle
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = titleColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = subtitle,
                color = subtitleColor,
                fontSize = 12.sp
            )
        }

        Spacer(Modifier.width(12.dp))

        // Amount + Label
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = amount,
                color = when {
                    amount.startsWith("+") -> positiveColor
                    isNegative -> negativeColor
                    else -> titleColor
                },
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = label,
                color = subtitleColor,
                fontSize = 11.sp,
                textAlign = TextAlign.End
            )
        }
    }
}
