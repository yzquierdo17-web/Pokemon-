package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val DarkTacticalColorScheme = darkColorScheme(
    primary = CyberCyan,
    onPrimary = Color(0xFF002A32),
    primaryContainer = Color(0xFF004D5A),
    onPrimaryContainer = Color(0xFFB8F8FF),
    secondary = ElectricCrimson,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF5C0F22),
    onSecondaryContainer = Color(0xFFFFD9DE),
    tertiary = RaidGold,
    onTertiary = Color(0xFF332900),
    tertiaryContainer = Color(0xFF524300),
    onTertiaryContainer = Color(0xFFFFE066),
    background = ArenaNavyBg,
    onBackground = Color(0xFFEAF2FF),
    surface = ArenaSlateSurface,
    onSurface = Color(0xFFEAF2FF),
    surfaceVariant = ArenaCardElevated,
    onSurfaceVariant = Color(0xFF9EB0D2),
    outline = ArenaOutlineDark,
    error = Color(0xFFFF5252),
    onError = Color.White
)

private val LightStadiumColorScheme = lightColorScheme(
    primary = DeepCyanPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC4F3FF),
    onPrimaryContainer = Color(0xFF001F26),
    secondary = CrimsonSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFD9E0),
    onSecondaryContainer = Color(0xFF3F0012),
    tertiary = Color(0xFF997A00),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFE57F),
    onTertiaryContainer = Color(0xFF261D00),
    background = StadiumLightBg,
    onBackground = Color(0xFF0F172A),
    surface = StadiumLightSurface,
    onSurface = Color(0xFF0F172A),
    surfaceVariant = StadiumCardElevated,
    onSurfaceVariant = Color(0xFF475569),
    outline = StadiumOutlineLight,
    error = Color(0xFFD32F2F),
    onError = Color.White
)

val TacticalShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkTacticalColorScheme else LightStadiumColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = TacticalShapes,
        content = content
    )
}
