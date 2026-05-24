package com.project.splice.auth


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person


import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.splice.R

// ─── Color Tokens ─────────────────────────────────────────────────────────────
private val BgPrimary       = Color(0xFF111111)
private val BgCard          = Color(0xFF1C1C1C)
private val BgCardLight     = Color(0xFFE8EDE0)   // cream "owed" card
private val BgChip          = Color(0xFF2A2A2A)
private val AccentRed       = Color(0xFFE05252)
private val AccentGreen     = Color(0xFF6EBF8B)
private val TextPrimary     = Color(0xFFFFFFFF)
private val TextSecondary   = Color(0xFF8A8A8A)
private val TextMuted       = Color(0xFF444444)
private val TextDark        = Color(0xFF111111)
private val SettledBg       = Color(0xFF2A3A2A)
private val SettledText     = Color(0xFF6EBF8B)
private val PendingText     = Color(0xFFE05252)
private val NavBg           = Color(0xFF1A1A1A)
private val PositiveGreen   = Color(0xFF4CAF50)

// ─── Data Models ──────────────────────────────────────────────────────────────
data class GroupMember(
    val name: String,
    val status: MemberStatus,
    val pendingAmount: String = "",
    val totalAmount: String,
    val paidPercent: String,
)

enum class MemberStatus { SETTLED, PENDING }

data class BottomNavItem(val label: String, val icon: Int, val selectedIcon: Int)

// ─── Screen ───────────────────────────────────────────────────────────────────
@Composable
fun EuropeTripDetailScreen(
    onBack: () -> Unit = {},
    onManageMembers: () -> Unit = {},
    onSettleUp: () -> Unit = {},
    onNavSelect: (Int) -> Unit = {},
) {
    var smartSimplification by remember { mutableStateOf(true) }
    var selectedNav by remember { mutableStateOf(1) }   // Groups tab active

    val members = listOf(
        GroupMember("Marco Rossi",   MemberStatus.SETTLED,  "",       "€4,210.00", "Paid 28%"),
        GroupMember("Sophie Durand", MemberStatus.PENDING, "€240",   "€3,980.50", "Paid 26%"),
        GroupMember("Liam Smith",    MemberStatus.PENDING, "€810",   "€2,150.92", "Paid 14%"),
        GroupMember("Yuki Tanaka",   MemberStatus.SETTLED,  "",       "€4,509.00", "Paid 32%"),
    )

    Scaffold(
        containerColor = BgPrimary,
        bottomBar = {
            GroupBottomNav(selected = selectedNav, onSelect = { selectedNav = it; onNavSelect(it) })
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 32.dp),
        ) {

            // ── Top Bar ───────────────────────────────────────────────────
            item {
                TripTopBar(onBack = onBack)
                Spacer(Modifier.height(16.dp))
            }

            // ── Total Spend Card ──────────────────────────────────────────
            item {
                TotalSpendCard()
                Spacer(Modifier.height(16.dp))
            }

            // ── Smart Simplification Card ─────────────────────────────────
            item {
                SmartSimplificationCard(
                    enabled = smartSimplification,
                    onToggle = { smartSimplification = it },
                )
                Spacer(Modifier.height(28.dp))
            }

            // ── Group Members ─────────────────────────────────────────────
            item {
                SectionHeader(title = "Group Members", actionLabel = "Manage Members", onAction = onManageMembers)
                Spacer(Modifier.height(16.dp))
            }

            items(members.size) { i ->
                MemberRow(member = members[i])
                Spacer(Modifier.height(2.dp))
            }

            item { Spacer(Modifier.height(20.dp)) }

            // ── Next Stop Card ────────────────────────────────────────────
            item {
                NextStopCard()
                Spacer(Modifier.height(16.dp))
            }

            // ── You Are Owed Card ─────────────────────────────────────────
            item {
                YouAreOwedCard(onSettleUp = onSettleUp)
                Spacer(Modifier.height(28.dp))
            }

            // ── AI Insights ───────────────────────────────────────────────
            item {
                AiInsightsSection()
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

// ─── Top Bar ──────────────────────────────────────────────────────────────────
@Composable
private fun TripTopBar(onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBack, modifier = Modifier.size(36.dp)) {
            Icon(painter = painterResource(R.drawable.ic_wallet), contentDescription = "Back", tint = TextPrimary)
        }
        Spacer(Modifier.width(8.dp))
        Text(
            "Europe Trip\n2024",
            color = TextPrimary,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 26.sp,
            modifier = Modifier.weight(1f),
        )
        // Avatar
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color(0xFF2A2A2A))
                .border(1.5.dp, Color(0xFF3A3A3A), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(painter = painterResource(R.drawable.ic_wallet), contentDescription = null, tint = TextSecondary, modifier = Modifier.size(20.dp))
        }
        Spacer(Modifier.width(10.dp))
        IconButton(onClick = {}, modifier = Modifier.size(36.dp)) {
            Icon(painter = painterResource(R.drawable.ic_wallet), contentDescription = "Notifications", tint = TextPrimary)
        }
    }
}

// ─── Total Spend Card ─────────────────────────────────────────────────────────
@Composable
private fun TotalSpendCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(BgCard)
            .padding(20.dp),
    ) {
        Text(
            "TOTAL GROUP SPEND",
            color = TextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.5.sp,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "€14,850.42",
            color = TextPrimary,
            fontSize = 40.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = (-1.5).sp,
        )
        Spacer(Modifier.height(14.dp))

        // AI Savings chip
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(BgChip)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(painter = painterResource(R.drawable.ic_wallet), contentDescription = null, tint = AccentGreen, modifier = Modifier.size(15.dp))
            Spacer(Modifier.width(6.dp))
            Text("AI Savings Detected: €420.00", color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(Modifier.height(8.dp))

        // Days remaining chip
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(BgChip)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(painter = painterResource(R.drawable.ic_wallet), contentDescription = null, tint = TextSecondary, modifier = Modifier.size(14.dp))
            Spacer(Modifier.width(6.dp))
            Text("14 Days Remaining", color = TextSecondary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }
    }
}

// ─── Smart Simplification Card ────────────────────────────────────────────────
@Composable
private fun SmartSimplificationCard(enabled: Boolean, onToggle: (Boolean) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(BgCard)
            .padding(20.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Sparkle / AI icon
            Icon(
                painter = painterResource(R.drawable.ic_wallet),
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(22.dp),
            )
            Spacer(Modifier.weight(1f))
            Switch(
                checked = enabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = TextPrimary,
                    checkedTrackColor = Color(0xFF3A3A3A),
                    uncheckedThumbColor = TextSecondary,
                    uncheckedTrackColor = Color(0xFF2A2A2A),
                ),
            )
        }
        Spacer(Modifier.height(12.dp))
        Text("Smart Simplification", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(6.dp))
        Text(
            "Our AI reduces the number of transactions by consolidating overlapping debts across the group.",
            color = TextSecondary,
            fontSize = 14.sp,
            lineHeight = 21.sp,
        )
        Spacer(Modifier.height(14.dp))
        HorizontalDivider(color = Color(0xFF2A2A2A), thickness = 1.dp)
        Spacer(Modifier.height(12.dp))
        Text("Active: 4 hops removed", color = TextSecondary, fontSize = 13.sp)
    }
}

// ─── Section Header ───────────────────────────────────────────────────────────
@Composable
private fun SectionHeader(title: String, actionLabel: String, onAction: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(title, color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Text(
            actionLabel,
            color = TextSecondary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onAction() },
        )
    }
}

// ─── Member Row ───────────────────────────────────────────────────────────────
@Composable
private fun MemberRow(member: GroupMember) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(painter = painterResource(R.drawable.ic_wallet), contentDescription = null, tint = TextSecondary, modifier = Modifier.size(24.dp))
        }

        Spacer(Modifier.width(14.dp))

        // Name + status
        Column(modifier = Modifier.weight(1f)) {
            Text(member.name, color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(4.dp))
            if (member.status == MemberStatus.SETTLED) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(SettledBg)
                        .padding(horizontal = 8.dp, vertical = 3.dp),
                ) {
                    Text("SETTLED", color = SettledText, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                }
            } else {
                Text(
                    "PENDING ${member.pendingAmount}",
                    color = PendingText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp,
                )
            }
        }

        // Amount + percentage
        Column(horizontalAlignment = Alignment.End) {
            Text(member.totalAmount, color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(2.dp))
            Text(member.paidPercent, color = TextSecondary, fontSize = 12.sp)
        }
    }
}

// ─── Next Stop Card ───────────────────────────────────────────────────────────
@Composable
private fun NextStopCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(20.dp))
            // Simulate dark atmospheric image with a gradient background
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1A1208), Color(0xFF3D2B0A), Color(0xFF1A1208)),
                )
            )
            // Overlay gradient for text readability
            .drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xCC000000)),
                        startY = size.height * 0.4f,
                    ),
                )
            },
    ) {
        // Avg/day badge — top right
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(14.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0x99000000))
                .padding(horizontal = 10.dp, vertical = 6.dp),
        ) {
            Text("€152.00 avg/day", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }

        // Next stop label — bottom left
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
        ) {
            Text("NEXT STOP", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.5.sp)
            Spacer(Modifier.height(2.dp))
            Text("Paris, France", color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// ─── You Are Owed Card ────────────────────────────────────────────────────────
@Composable
private fun YouAreOwedCard(onSettleUp: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(BgCardLight)
            .padding(24.dp),
    ) {
        Text(
            "YOU ARE OWED",
            color = Color(0xFF555544),
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.5.sp,
        )
        Spacer(Modifier.height(6.dp))
        Text(
            "€1,050.00",
            color = TextDark,
            fontSize = 38.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = (-1).sp,
        )
        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onSettleUp,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E2618),
                contentColor = TextPrimary,
            ),
        ) {
            Icon(painter = painterResource(R.drawable.ic_wallet), contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(10.dp))
            Text("Settle Up", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(Modifier.height(12.dp))
        Text(
            "Next automated sweep in 2 days",
            color = Color(0xFF888877),
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}

// ─── AI Insights Section ──────────────────────────────────────────────────────
@Composable
private fun AiInsightsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(R.drawable.ic_wallet), contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("AI Insights", color = TextPrimary, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(14.dp))

        val insights = listOf(
            "Dining expenses are 15% higher than Budapest. Consider pre-booking.",
            "Currency conversion fees detected. Switch to Yuki's card for shared bookings.",
        )
        insights.forEach { insight ->
            Row(
                modifier = Modifier.padding(vertical = 5.dp),
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 7.dp)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(TextSecondary),
                )
                Spacer(Modifier.width(12.dp))
                Text(insight, color = TextSecondary, fontSize = 14.sp, lineHeight = 21.sp)
            }
        }
    }
}

// ─── Bottom Navigation ────────────────────────────────────────────────────────
@Composable
private fun GroupBottomNav(selected: Int, onSelect: (Int) -> Unit) {
    val items = listOf(
        BottomNavItem("Home",          R.drawable.ic_wallet,R.drawable.ic_wallet,      ),
        BottomNavItem("Groups",        R.drawable.ic_wallet,R.drawable.ic_wallet,     ),
        BottomNavItem("AI\nAssistant", R.drawable.ic_wallet,R.drawable.ic_wallet,),
        BottomNavItem("Analytics",     R.drawable.ic_wallet,R.drawable.ic_wallet, ),
        BottomNavItem("Profile",       R.drawable.ic_wallet,R.drawable.ic_wallet,   ),
    )

    Surface(color = NavBg, tonalElevation = 0.dp) {
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
                                .background(Color(0xFF2A2A2A)),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(painter = painterResource(item.selectedIcon), contentDescription = item.label, tint = Color.White, modifier = Modifier.size(22.dp))
                        }
                    } else {
                        Icon(painter = painterResource(item.icon), contentDescription = item.label, tint = Color(0xFF555555), modifier = Modifier.size(22.dp))
                    }
                    Spacer(Modifier.height(3.dp))
                    Text(
                        item.label,
                        color = if (isSelected) Color.White else Color(0xFF555555),
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

// ─── Preview ──────────────────────────────────────────────────────────────────
@Preview(showBackground = true, backgroundColor = 0xFF111111, showSystemUi = true)
@Composable
fun EuropeTripDetailScreenPreview() {
    EuropeTripDetailScreen()
}