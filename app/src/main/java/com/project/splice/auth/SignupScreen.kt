package com.project.splice.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.project.splice.R
import com.project.splice.auth.presentation.signup.SignupViewModel
import com.project.splice.navigation.Screens
import com.project.splice.ui.components.LegalFooter
import com.project.splice.ui.components.SpliceButton
import com.project.splice.ui.components.SpliceTextField
import com.project.splice.ui.components.SplicePasswordField

// ─── Color tokens ────────────────────────────────────────────────────────────
private val BackgroundDark  = Color(0xFF141414)
private val SurfaceDark     = Color(0xFF1E1E1E)
private val IconCircle      = Color(0xFF2A2A2A)
private val OutlineColor    = Color(0xFF2E2E2E)
private val TextPrimary     = Color(0xFFFFFFFF)
private val TextSecondary   = Color(0xFF8A8A8A)
private val TextMuted       = Color(0xFF5A5A5A)
private val DividerColor    = Color(0xFF2E2E2E)

@Composable
fun SignupScreen(
    navController: NavHostController,
    viewModel: SignupViewModel = hiltViewModel(),
    onContinueWithGoogle: () -> Unit = {},
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    val state by viewModel.state

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
                text = "Create Account",
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
                text = "Join Lumina and start splitting expenses smarter with your friends and family.",
                color = TextSecondary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 22.sp,
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.weight(1f))

            if (state.isLoading) {
                CircularProgressIndicator(color = TextPrimary)
                Spacer(Modifier.height(16.dp))
            }

            state.error?.let {
                Text(text = it, color = Color.Red, fontSize = 14.sp)
                Spacer(Modifier.height(16.dp))
            }

            // ── Name Input ───────────────────────────────────────────────
            SpliceTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = "Full Name",
                containerColor = SurfaceDark,
                unfocusedBorderColor = OutlineColor,
                focusedBorderColor = Color(0xFF444444)
            )

            Spacer(Modifier.height(12.dp))

            // ── Email Input ───────────────────────────────────────────────
            SpliceTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email address",
                keyboardType = KeyboardType.Email,
                containerColor = SurfaceDark,
                unfocusedBorderColor = OutlineColor,
                focusedBorderColor = Color(0xFF444444)
            )

            Spacer(Modifier.height(12.dp))

            // ── Password Input ────────────────────────────────────────────
            SplicePasswordField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                containerColor = SurfaceDark,
                unfocusedBorderColor = OutlineColor,
                focusedBorderColor = Color(0xFF444444)
            )

            Spacer(Modifier.height(24.dp))

            // ── Sign Up Button ───────────────────────────────────────────
            SpliceButton(
                text = "Create Account",
                onClick = {
                    viewModel.onSignup(
                        email = email,
                        password = password,
                        name = name,
                        photoUrl = ""
                    )
                }
            )

            LaunchedEffect(state.user) {
                if (state.user != null) {
                    navController.navigate(Screens.HomeScreen)
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Go to Login ──────────────────────────────────────────────
            TextButton(onClick = { navController.navigate(Screens.LoginScreen) }) {
                Text(
                    text = "Already have an account? Sign In",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            // ── Legal Footer ──────────────────────────────────────────────
            LegalFooter(
                onTermsClick = onTermsClick,
                onPrivacyClick = onPrivacyClick,
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}
