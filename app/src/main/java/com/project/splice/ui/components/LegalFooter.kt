package com.project.splice.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun LegalFooter(
    modifier: Modifier = Modifier,
    onTermsClick: () -> Unit = {},
    onPrivacyClick: () -> Unit = {},
    textColor: Color = Color(0xFF5A5A5A),
    highlightColor: Color = Color(0xFF8A8A8A)
) {
    val annotatedText = buildAnnotatedString {
        withStyle(SpanStyle(color = textColor, fontSize = 13.sp)) {
            append("By continuing, you agree to our ")
        }
        pushStringAnnotation(tag = "terms", annotation = "terms")
        withStyle(
            SpanStyle(
                color = highlightColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.None,
            )
        ) {
            append("Terms of Service")
        }
        pop()
        withStyle(SpanStyle(color = textColor, fontSize = 13.sp)) {
            append(" and ")
        }
        pushStringAnnotation(tag = "privacy", annotation = "privacy")
        withStyle(
            SpanStyle(
                color = highlightColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
            )
        ) {
            append("Privacy Policy")
        }
        pop()
        withStyle(SpanStyle(color = textColor, fontSize = 13.sp)) {
            append(".")
        }
    }

    Text(
        text = annotatedText,
        modifier = modifier,
        textAlign = TextAlign.Center,
        lineHeight = 20.sp,
    )
}
