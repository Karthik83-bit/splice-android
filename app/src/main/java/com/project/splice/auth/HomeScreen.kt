package com.splitwiseai.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.project.splice.R
import com.project.splice.navigation.Screens

// ─── Color Palette ────────────────────────────────────────────────────────────
private val BgPrimary      = Color(0xFF111111)
private val BgCard         = Color(0xFF1C1C1C)
private val BgCardAlt      = Color(0xFF1A1A1A)
private val AccentGreen    = Color(0xFFE8E8E8)
private val AccentRed      = Color(0xFFFF6B6B)
private val AccentOrange   = Color(0xFFFF8C42)
private val TextPrimary    = Color(0xFFFFFFFF)
private val TextSecondary  = Color(0xFF8A8A8A)
private val TextMuted      = Color(0xFF555555)
private val PositiveGreen  = Color(0xFF4CAF50)
private val NegativeRed    = Color(0xFFFF5252)
private val DotGreen       = Color(0xFF9EE09E)
private val DotRed         = Color(0xFFFF6B6B)
private val DotGray        = Color(0xFF555555)
private val NavSelected    = Color(0xFFFFFFFF)
private val NavBg          = Color(0xFF1C1C1C)

// ─── Data Models ──────────────────────────────────────────────────────────────
data class ActivityItem(
    val title: String,
    val subtitle: String,
    val amount: String,
    val label: String,
    val isNegative: Boolean,
    val iconBg: Color = Color(0xFF2A2A2A),
)

data class GroupItem(
    val name: String,
    val balance: String,
    val dotColor: Color,
)

// ─── Main Screen ──────────────────────────────────────────────────────────────
@Composable
fun SplitwiseAIHomeScreen(
    onSeeAllActivity: () -> Unit = {},
    onViewAnalysis: () -> Unit = {},
    onScanReceipt: () -> Unit = {},
    onFabClick: () -> Unit = {},
    onNavSelect: (Int) -> Unit = {},
    navController: NavHostController
) {
    var selectedNav by remember { mutableStateOf(0) }

    val activities = listOf(
        ActivityItem(
            title = "Dinner at L'Artusi",
            subtitle = "Yesterday in NYC Foodies",
            amount = "-\$85.00",
            label = "You paid",
            isNegative = false,   // "You paid" → neutral/white
        ),
        ActivityItem(
            title = "Monthly Utilities",
            subtitle = "Oct 12 in Apartment 4B",
            amount = "+\$124.20",
            label = "Sarah owes you",
            isNegative = false,
            iconBg = Color(0xFF2A3A4A),
        ),
        ActivityItem(
            title = "Ski Trip Cabin",
            subtitle = "Oct 10 in Winter 2024",
            amount = "-\$210.00",
            label = "You owe Mark",
            isNegative = true,
        ),
    )

    val groups = listOf(
        GroupItem("Apartment 4B", "+\$240.00", DotGreen),
        GroupItem("NYC Foodies",  "-\$42.10",  DotRed),
        GroupItem("Winter 2024",  "\$0.00",    DotGray),
    )

    Scaffold(
        containerColor = BgPrimary,
        bottomBar = {
            SplitwiseBottomNav(
                selected = selectedNav,
                onSelect = { selectedNav = it; onNavSelect(it) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFabClick,
                containerColor = TextPrimary,
                contentColor = BgPrimary,
                shape = CircleShape,
                modifier = Modifier.size(52.dp),
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(24.dp))
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {

            // ── Top Bar ───────────────────────────────────────────────────
            item {
                TopBar()
                Spacer(Modifier.height(16.dp))
            }

            // ── Balance Card ──────────────────────────────────────────────
            item {
                BalanceCard()
                Spacer(Modifier.height(16.dp))
            }

            // ── AI Insights Card ──────────────────────────────────────────
            item {
                AiInsightsCard(onViewAnalysis = onViewAnalysis)
                Spacer(Modifier.height(28.dp))
            }

            // ── Recent Activity ───────────────────────────────────────────
            item {
                SectionHeader(title = "Recent Activity", actionLabel = "See all", onAction = onSeeAllActivity)
                Spacer(Modifier.height(16.dp))
            }

            items(activities.size) { i ->
                ActivityRow(item = activities[i])
                if (i < activities.lastIndex) {
                    Spacer(Modifier.height(20.dp))
                }
            }

            item { Spacer(Modifier.height(28.dp)) }

            // ── Your Groups ───────────────────────────────────────────────
            item {
                GroupsCard(groups = groups){
                    navController.navigate(Screens.GroupScreen)
                }
                Spacer(Modifier.height(16.dp))
            }

            // ── Scan Receipt ──────────────────────────────────────────────
            item {
                ScanReceiptCard(onClick = onScanReceipt)
            }
        }
    }
}

// ─── Top Bar ──────────────────────────────────────────────────────────────────
@Composable
private fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Avatar placeholder
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(Icons.Default.Person, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(22.dp))
        }

        Spacer(Modifier.width(12.dp))

        Text(
            text = "Splitwise AI",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
        )

        IconButton(onClick = {}) {
            Icon(
                Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = TextPrimary,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

// ─── Balance Card ─────────────────────────────────────────────────────────────
@Composable
private fun BalanceCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(BgCard),
    ) {
        // Decorative wave in background
        WaveDecoration(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .fillMaxWidth()
                .height(80.dp),
        )

        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "TOTAL BALANCE",
                color = TextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.5.sp,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "\$1,240.50",
                color = TextPrimary,
                fontSize = 38.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp,
            )
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                BalanceSubItem(label = "YOU OWE",     amount = "\$420.00",   color = AccentOrange)
                BalanceSubItem(label = "YOU ARE OWED", amount = "\$1,660.50", color = TextPrimary)
            }
        }
    }
}

@Composable
private fun BalanceSubItem(label: String, amount: String, color: Color) {
    Column {
        Text(label, color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp)
        Spacer(Modifier.height(2.dp))
        Text(amount, color = color, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun WaveDecoration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.4f, h * 0.9f)
            cubicTo(w * 0.5f, h * 0.5f, w * 0.65f, h * 0.7f, w * 0.75f, h * 0.3f)
            cubicTo(w * 0.85f, h * 0.0f, w * 0.92f, h * 0.2f, w, h * 0.1f)
            lineTo(w, h)
            lineTo(w * 0.4f, h)
            close()
        }
        drawPath(path, color = Color.White.copy(alpha = 0.03f), style = Fill)

        val stroke = Path().apply {
            moveTo(w * 0.35f, h * 0.95f)
            cubicTo(w * 0.5f, h * 0.55f, w * 0.65f, h * 0.75f, w * 0.75f, h * 0.35f)
            cubicTo(w * 0.85f, h * 0.05f, w * 0.92f, h * 0.25f, w, h * 0.15f)
        }
        drawPath(
            stroke,
            color = Color.White.copy(alpha = 0.08f),
            style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round),
        )
    }
}

// ─── AI Insights Card ─────────────────────────────────────────────────────────
@Composable
private fun AiInsightsCard(onViewAnalysis: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(BgCard)
            .padding(20.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2A2A2A)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(painter = painterResource(R.drawable.ic_wallet), contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(10.dp))
            Text("AI INSIGHTS", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.5.sp)
        }

        Spacer(Modifier.height(14.dp))

        Text(
            text = "You spent 15% less on dining this month.",
            color = TextPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 28.sp,
        )

        Spacer(Modifier.height(14.dp))

        Row(
            modifier = Modifier.clickable { onViewAnalysis() },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("View Analysis", color = TextSecondary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(Modifier.width(4.dp))
            Icon(Icons.Default.ArrowForward, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(14.dp))
        }
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
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onAction() },
        )
    }
}

// ─── Activity Row ─────────────────────────────────────────────────────────────
@Composable
private fun ActivityRow(item: ActivityItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Icon / Avatar
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(item.iconBg),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                if (item.title.contains("Utilities"))  painterResource(R.drawable.ic_wallet) else painterResource(R.drawable.ic_wallet),
                contentDescription = null,
                tint = TextSecondary,
                modifier = Modifier.size(22.dp),
            )
        }

        Spacer(Modifier.width(14.dp))

        // Title + Subtitle
        Column(modifier = Modifier.weight(1f)) {
            Text(item.title, color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(2.dp))
            Text(item.subtitle, color = TextSecondary, fontSize = 12.sp)
        }

        Spacer(Modifier.width(12.dp))

        // Amount + Label
        Column(horizontalAlignment = Alignment.End) {
            Text(
                item.amount,
                color = if (item.amount.startsWith("+")) PositiveGreen
                else if (item.isNegative) NegativeRed
                else TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(2.dp))
            Text(item.label, color = TextSecondary, fontSize = 11.sp, textAlign = TextAlign.End)
        }
    }
}

// ─── Groups Card ──────────────────────────────────────────────────────────────
@Composable
private fun GroupsCard(groups: List<GroupItem>,onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(BgCard)
            .padding(20.dp),
    ) {
        Text("YOUR GROUPS", color = TextSecondary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.5.sp)
        Spacer(Modifier.height(16.dp))

        groups.forEachIndexed { index, group ->
            GroupRow(item = group,onClick={onClick()})
            if (index < groups.lastIndex) {
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun GroupRow(item: GroupItem,onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = {onClick()}),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Colored dot
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(item.dotColor),
        )
        Spacer(Modifier.width(14.dp))
        Text(item.name, color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
        Text(
            item.balance,
            color = when {
                item.balance.startsWith("+") -> PositiveGreen
                item.balance.startsWith("-") -> NegativeRed
                else -> TextSecondary
            },
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

// ─── Scan Receipt Card ────────────────────────────────────────────────────────
@Composable
private fun ScanReceiptCard(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(BgCard)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF2A2A2A)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(painter = painterResource(R.drawable.ic_wallet), contentDescription = null, tint = TextSecondary, modifier = Modifier.size(22.dp))
        }

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text("Scan Receipt", color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(2.dp))
            Text("Let AI handle the splitting", color = TextSecondary, fontSize = 12.sp)
        }

        Icon(painter = painterResource(R.drawable.ic_wallet), contentDescription = null, tint = TextSecondary, modifier = Modifier.size(20.dp))
    }
}

// ─── Bottom Navigation ────────────────────────────────────────────────────────
data class NavItem(val label: String, val icon: Int, val selectedIcon: Int)

@Composable
private fun SplitwiseBottomNav(selected: Int, onSelect: (Int) -> Unit) {
    val items = listOf(
        NavItem("Home",        R.drawable.ic_wallet,        R.drawable.ic_wallet),
        NavItem("Groups",      R.drawable.ic_wallet,       R.drawable.ic_wallet),
        NavItem("AI\nAssistant", R.drawable.ic_wallet, R.drawable.ic_wallet),
        NavItem("Analytics",   R.drawable.ic_wallet,    R.drawable.ic_wallet),
        NavItem("Profile",     R.drawable.ic_wallet,     R.drawable.ic_wallet),
    )

    Surface(
        color = NavBg,
        tonalElevation = 0.dp,
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
                NavBarItem(
                    item = item,
                    selected = selected == index,
                    onClick = { onSelect(index) },
                )
            }
        }
    }
}

@Composable
private fun NavBarItem(item: NavItem, selected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF2A2A2A)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(painter = painterResource(item.selectedIcon), contentDescription = item.label, tint = NavSelected, modifier = Modifier.size(22.dp))
            }
        } else {
            Icon(painter = painterResource(item.icon), contentDescription = item.label, tint = TextMuted, modifier = Modifier.size(22.dp))
        }

        Spacer(Modifier.height(3.dp))
        Text(
            item.label,
            color = if (selected) TextPrimary else TextMuted,
            fontSize = 10.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            textAlign = TextAlign.Center,
            lineHeight = 12.sp,
        )
    }
}

// ─── Preview ──────────────────────────────────────────────────────────────────
@Preview(showBackground = true, backgroundColor = 0xFF111111, showSystemUi = true)
@Composable
fun SplitwiseAIHomeScreenPreview() {
//    SplitwiseAIHomeScreen()
}