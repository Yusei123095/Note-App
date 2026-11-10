package com.yu.my_note_app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


val LightColorScheme = lightColorScheme(
    primary            = Orange500, // logo background
    onPrimary          = white, // something on primary colour
    primaryContainer   = Orange100, // primary container
    onPrimaryContainer = Orange400, // something on the primary container
    secondary          = Orange600,
    onSecondary        = white,
    background         = Orange50,
    onBackground       = InkBrown,
    surface            = white,
    onSurface          = gray,
    surfaceVariant     = Orange100,
    onSurfaceVariant   = gray,
    surfaceContainerLowest  = white,
    surfaceContainerLow     = Orange50,
    surfaceContainer        = Orange50,
    surfaceContainerHigh    = Orange100,
    surfaceContainerHighest = white,
    outline            = Orange100,
    outlineVariant     = Orange200,
    error              = Red500,
    onError            = Color.White,
)


val DarkColorScheme = darkColorScheme(
    primary            = Orange400,
    onPrimary          = DarkOnPrimary,
    primaryContainer   = Orange900,
    onPrimaryContainer = Orange100,
    secondary          = Orange300,
    onSecondary        = DarkOnPrimary,
    background         = DarkBackground,
    onBackground       = DarkOnBg,
    surface            = DarkSurface,
    onSurface          = DarkOnBg,
    surfaceVariant     = DarkSurfaceVar,
    onSurfaceVariant   = DarkOnSurfaceVar,
    outline            = DarkOutline,
    outlineVariant     = Color(0xFF4A362A),
    error              = Red400,
    onError            = Color(0xFF2A0A0A),           // 暗い赤系の文字色
)

@Composable
fun MyNoteAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}