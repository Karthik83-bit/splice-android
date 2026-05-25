package com.project.splice.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.splice.R
import com.project.splice.navigation.Screens
import com.project.splice.ui.components.*

// ─── Color Palette ────────────────────────────────────────────────────────────
private val BgPrimary      = Color(0xFF111111)
private val BgCard         = Color(0xFF1C1C1C)
private val TextPrimary    = Color(0xFFFFFFFF)
private val TextSecondary  = Color(0xFF8A8A8A)
private val PositiveGreen  = Color(0xFF4CAF50)
private val NegativeRed    = Color(0xFFFF5252)
private val DotGreen       = Color(0xFF9EE09E)
private val DotRed         = Color(0xFFFF6B6B)
private val DotGray        = Color(0xFF555555)
private val AccentOrange   = Color(0xFFFF8C42)

// ─── Data Models ──────────────────────────────────────────────────────────────
data class HomeActivityItem(
    val title: String,
    val subtitle: String,
    val amount: String,
    val label: String,
    val isNegative: Boolean,
    val iconBg: Color = Color(0xFF2A2A2A),
)

data class HomeGroupItem(
    val name: String,
    val balance: String,
    val dotColor: Color,
)

// ─── Main Screen ──────────────────────────────────────────────────────────────
@Composable
fun SplitwiseAIHomeScreen(
    navController: NavHostController,
    onSeeAllActivity: () -> Unit = {},
    onViewAnalysis: () -> Unit = {},
    onScanReceipt: () -> Unit = {},
    onFabClick: () -> Unit = {},
    onNavSelect: (Int) -> Unit = {},
) {
    var selectedNav by remember { mutableStateOf(0) }

    val activities = listOf(
        HomeActivityItem(
            title = "Dinner at L'Artusi",
            subtitle = "Yesterday in NYC Foodies",
            amount = "-\$85.00",
            label = "You paid",
            isNegative = false,
        ),
        HomeActivityItem(
            title = "Monthly Utilities",
            subtitle = "Oct 12 in Apartment 4B",
            amount = "+\$124.20",
            label = "Sarah owes you",
            isNegative = false,
            iconBg = Color(0xFF2A3A4A),
        ),
        HomeActivityItem(
            title = "Ski Trip Cabin",
            subtitle = "Oct 10 in Winter 2024",
            amount = "-\$210.00",
            label = "You owe Mark",
            isNegative = true,
        ),
    )

    val groups = listOf(
        HomeGroupItem("Apartment 4B", "+\$240.00", DotGreen),
        HomeGroupItem("NYC Foodies",  "-\$42.10",  DotRed),
        HomeGroupItem("Winter 2024",  "\$0.00",    DotGray),
    )

    val navItems = listOf(
        SpliceNavItem("Home", R.drawable.ic_wallet, R.drawable.ic_wallet),
        SpliceNavItem("Groups", R.drawable.ic_wallet, R.drawable.ic_wallet),
        SpliceNavItem("AI\nAssistant", R.drawable.ic_wallet, R.drawable.ic_wallet),
        SpliceNavItem("Analytics", R.drawable.ic_wallet, R.drawable.ic_wallet),
        SpliceNavItem("Profile", R.drawable.ic_wallet, R.drawable.ic_wallet),
    )

    Scaffold(
        containerColor = BgPrimary,
        bottomBar = {
            SpliceBottomNav(
                selected = selectedNav,
                onSelect = { selectedNav = it; onNavSelect(it) },
                items = navItems
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
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {

            item {
                SpliceTopBar(title = "Splitwise AI")
                Spacer(Modifier.height(16.dp))
            }

            item {
                BalanceCard()
                Spacer(Modifier.height(16.dp))
            }

            item {
                AiInsightsCard(onViewAnalysis = onViewAnalysis)
                Spacer(Modifier.height(28.dp))
            }

            item {
                SectionHeader(title = "Recent Activity", actionLabel = "See all", onAction = onSeeAllActivity)
                Spacer(Modifier.height(16.dp))
            }

            items(activities.size) { i ->
                val activity = activities[i]
                ActivityRow(
                    title = activity.title,
                    subtitle = activity.subtitle,
                    amount = activity.amount,
                    label = activity.label,
                    isNegative = activity.isNegative,
                    iconBg = activity.iconBg
                )
                if (i < activities.lastIndex) {
                    Spacer(Modifier.height(20.dp))
                }
            }

            item { Spacer(Modifier.height(28.dp)) }

            item {
                GroupsCard(groups = groups) {
                    navController.navigate(Screens.GroupScreen)
                }
                Spacer(Modifier.height(16.dp))
            }

            item {
                ScanReceiptCard(onClick = onScanReceipt)
            }
        }
    }
}

@Composable
private fun BalanceCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(BgCard)
            .padding(20.dp),
    ) {
        Column {
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
private fun AiInsightsCard(onViewAnalysis: () -> Unit) {
    // Content simplified
}

@Composable
private fun GroupsCard(groups: List<HomeGroupItem>, onClick: () -> Unit) {
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
            GroupRow(
                name = group.name,
                balance = group.balance,
                dotColor = group.dotColor,
                onClick = onClick
            )
            if (index < groups.lastIndex) {
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ScanReceiptCard(onClick: () -> Unit) {
    // Content simplified
}
