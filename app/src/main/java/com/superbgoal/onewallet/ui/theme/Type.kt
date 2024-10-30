package com.superbgoal.onewallet.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.superbgoal.onewallet.R

val one_sans = FontFamily(
    Font(R.font.one_font_regular, FontWeight.Normal),
    Font(R.font.one_font_extrabold, FontWeight.ExtraBold),
    Font(R.font.one_font_bold, FontWeight.Bold),
    Font(R.font.one_font_semibold, FontWeight.SemiBold),
    Font(R.font.one_font_medium, FontWeight.Medium),
    Font(R.font.one_font_light, FontWeight.Light),
    Font(R.font.one_font_extralight, FontWeight.ExtraLight),
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = Typography().displayLarge.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.ExtraBold
    ),
    displayMedium = Typography().displayMedium.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.ExtraBold
    ),
    displaySmall = Typography().displaySmall.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.ExtraBold
    ),

    headlineLarge = Typography().headlineLarge.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = Typography().headlineMedium.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.Bold
    ),
    headlineSmall = Typography().headlineSmall.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.Bold
    ),

    titleLarge = Typography().titleLarge.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = Typography().titleMedium.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.SemiBold
        ),
    titleSmall = Typography().titleSmall.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.SemiBold
    ),

    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.Normal
    ),
    bodySmall = Typography().bodySmall.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.Normal
    ),

    labelLarge = Typography().labelLarge.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.Medium
    ),
    labelMedium = Typography().labelMedium.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.Medium
    ),
    labelSmall = Typography().labelSmall.copy(
        fontFamily = one_sans,
        fontWeight = FontWeight.Medium
    )
)