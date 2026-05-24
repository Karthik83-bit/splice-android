package com.project.splice.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.project.splice.R
import com.project.splice.navigation.Screens

// ─── Color tokens ────────────────────────────────────────────────────────────
private val BackgroundDark  = Color(0xFF141414)
private val SurfaceDark     = Color(0xFF1E1E1E)
private val IconCircle      = Color(0xFF2A2A2A)
private val OutlineColor    = Color(0xFF2E2E2E)
private val TextPrimary     = Color(0xFFFFFFFF)
private val TextSecondary   = Color(0xFF8A8A8A)
private val TextMuted       = Color(0xFF5A5A5A)
private val DividerColor    = Color(0xFF2E2E2E)

// ─── Screen ──────────────────────────────────────────────────────────────────
@Composable
fun LuminaWelcomeScreen(
    navController: NavHostController,
    onContinueWithGoogle: () -> Unit = {},
    onSendOtp: (String) -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
) {
    var phoneNumber by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Spacer(Modifier.weight(1f))

            // ── App Icon ──────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(IconCircle),
                contentAlignment = Alignment.Center
            ) {
                // Replace R.drawable.ic_wallet with your actual wallet icon resource
                Icon(
                    painter = painterResource(R.drawable.ic_wallet),
                    contentDescription = "Lumina logo",
                    tint = TextPrimary,
                    modifier = Modifier.size(70.dp)
                )
            }

            Spacer(Modifier.height(32.dp))

            // ── Title ─────────────────────────────────────────────────────
            Text(
                text = "Welcome to\nLumina",
                color = TextPrimary,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 48.sp,
                textAlign = TextAlign.Center,
                letterSpacing = (-1).sp,
            )

            Spacer(Modifier.height(16.dp))

            // ── Subtitle ──────────────────────────────────────────────────
            Text(
                text = "Smarter splits, effortless settling. Experience the future of collaborative finance.",
                color = TextSecondary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 22.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.weight(1f))

            // ── Google Button ─────────────────────────────────────────────
            OutlinedButton(
                onClick = onContinueWithGoogle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = SurfaceDark,
                    contentColor = TextPrimary,
                ),
                border = BorderStroke(1.dp, OutlineColor),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    // Google "G" icon — swap for your branded vector asset
                    Icon(
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = "Google logo",
                        tint = TextPrimary,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = "Continue with Google",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary,
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── OR Divider ────────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = DividerColor,
                    thickness = 1.dp,
                )
                Text(
                    text = "  OR  ",
                    color = TextMuted,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp,
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = DividerColor,
                    thickness = 1.dp,
                )
            }

            Spacer(Modifier.height(24.dp))

            // ── Phone Input ───────────────────────────────────────────────
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                placeholder = {
                    Text(
                        "Phone number",
                        color = TextMuted,
                        fontSize = 15.sp,
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_call),
                        contentDescription = "Phone",
                        tint = TextMuted,
                        modifier = Modifier.size(20.dp),
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor   = SurfaceDark,
                    unfocusedContainerColor = SurfaceDark,
                    focusedBorderColor      = Color(0xFF444444),
                    unfocusedBorderColor    = OutlineColor,
                    cursorColor             = TextPrimary,
                    focusedTextColor        = TextPrimary,
                    unfocusedTextColor      = TextPrimary,
                ),
                shape = RoundedCornerShape(12.dp),
            )

            Spacer(Modifier.height(12.dp))

            // ── Send OTP Button ───────────────────────────────────────────
            Button(
                onClick = {
                    onSendOtp(phoneNumber)
                    navController.navigate(Screens.HomeScreen)
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TextPrimary,
                    contentColor   = BackgroundDark,
                ),
            ) {
                Text(
                    text = "Send OTP Code",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = BackgroundDark,
                )
            }

            Spacer(Modifier.height(28.dp))

            // ── Legal Footer ──────────────────────────────────────────────
            LegalFooter(
                onTermsClick   = onTermsClick,
                onPrivacyClick = onPrivacyClick,
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

// ─── Legal footer with clickable spans ───────────────────────────────────────
@Composable
private fun LegalFooter(
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
) {
    val annotatedText = buildAnnotatedString {
        withStyle(SpanStyle(color = TextMuted, fontSize = 13.sp)) {
            append("By continuing, you agree to our ")
        }
        withStyle(
            SpanStyle(
                color = TextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.None,
            )
        ) {
            append("Terms of Service")
        }
        withStyle(SpanStyle(color = TextMuted, fontSize = 13.sp)) {
            append(" and ")
        }
        withStyle(
            SpanStyle(
                color = TextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
            )
        ) {
            append("Privacy Policy")
        }
        withStyle(SpanStyle(color = TextMuted, fontSize = 13.sp)) {
            append(".")
        }
    }

    // For clickable spans, use ClickableText from androidx.compose.foundation.text
    // and tag the "Terms of Service" / "Privacy Policy" ranges.
    // Simplified here as a plain Text; swap out if needed:
    Text(
        text = annotatedText,
        textAlign = TextAlign.Center,
        lineHeight = 20.sp,
    )
}

// ─── Minimal Google "G" icon drawn with Canvas ────────────────────────────────
// Replace this with your actual ic_google vector asset for production.
@Composable
private fun GoogleGIcon(modifier: Modifier = Modifier) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        // A simplified coloured circle as placeholder
        drawCircle(color = Color(0xFF4285F4), radius = w / 2f)
        drawCircle(color = Color.White, radius = w / 4f)
    }
}

// ─── Preview ──────────────────────────────────────────────────────────────────
@Preview(showBackground = true, backgroundColor = 0xFF141414)
@Composable
fun LuminaWelcomeScreenPreview() {
//    LuminaWelcomeScreen()
}