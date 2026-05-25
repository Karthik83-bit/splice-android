package com.project.splice.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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

data class SpliceNavItem(
    val label: String,
    val icon: Int,
    val selectedIcon: Int
)

@Composable
fun SpliceBottomNav(
    selected: Int,
    onSelect: (Int) -> Unit,
    items: List<SpliceNavItem>,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF1C1C1C),
    selectedColor: Color = Color.White,
    unselectedColor: Color = Color(0xFF555555),
    indicatorColor: Color = Color(0xFF2A2A2A)
) {
    Surface(
        color = backgroundColor,
        tonalElevation = 0.dp,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(64.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selected == index
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSelect(index) }
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(indicatorColor),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                painter = painterResource(item.selectedIcon),
                                contentDescription = item.label,
                                tint = selectedColor,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    } else {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = item.label,
                            tint = unselectedColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = item.label,
                        color = if (isSelected) selectedColor else unselectedColor,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        lineHeight = 12.sp,
                    )
                }
            }
        }
    }
}
