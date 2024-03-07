package me.qcuncle.nowinnews.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun NinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    themeIndex: Int = -1,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> when (themeIndex) {
            0 -> DarkColors0
            1 -> DarkColors1
            2 -> DarkColors2
            3 -> DarkColors3
            4 -> DarkColors4
            5 -> DarkColors5
            6 -> DarkColors6
            7 -> DarkColors7
            8 -> DarkColors8
            9 -> DarkColors9
            else -> DarkColors0
        }

        else -> when (themeIndex) {
            0 -> LightColors0
            1 -> LightColors1
            2 -> LightColors2
            3 -> LightColors3
            4 -> LightColors4
            5 -> LightColors5
            6 -> LightColors6
            7 -> LightColors7
            8 -> LightColors8
            9 -> LightColors9
            else -> LightColors0
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Set the color of the status bar and navigation bar to be consistent with the background color
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.surfaceVariant.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                !darkTheme
        }
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

private val LightColors0
    get() = run {
        Color0.run {
            lightColorScheme(
                primary = this.md_theme_light_primary,
                onPrimary = this.md_theme_light_onPrimary,
                primaryContainer = this.md_theme_light_primaryContainer,
                onPrimaryContainer = this.md_theme_light_onPrimaryContainer,
                secondary = this.md_theme_light_secondary,
                onSecondary = this.md_theme_light_onSecondary,
                secondaryContainer = this.md_theme_light_secondaryContainer,
                onSecondaryContainer = this.md_theme_light_onSecondaryContainer,
                tertiary = this.md_theme_light_tertiary,
                onTertiary = this.md_theme_light_onTertiary,
                tertiaryContainer = this.md_theme_light_tertiaryContainer,
                onTertiaryContainer = this.md_theme_light_onTertiaryContainer,
                error = this.md_theme_light_error,
                errorContainer = this.md_theme_light_errorContainer,
                onError = this.md_theme_light_onError,
                onErrorContainer = this.md_theme_light_onErrorContainer,
                background = this.md_theme_light_background,
                onBackground = this.md_theme_light_onBackground,
                surface = this.md_theme_light_surface,
                onSurface = this.md_theme_light_onSurface,
                surfaceVariant = this.md_theme_light_surfaceVariant,
                onSurfaceVariant = this.md_theme_light_onSurfaceVariant,
                outline = this.md_theme_light_outline,
                inverseOnSurface = this.md_theme_light_inverseOnSurface,
                inverseSurface = this.md_theme_light_inverseSurface,
                inversePrimary = this.md_theme_light_inversePrimary,
                surfaceTint = this.md_theme_light_surfaceTint,
                outlineVariant = this.md_theme_light_outlineVariant,
                scrim = this.md_theme_light_scrim,
            )
        }
    }

private val DarkColors0
    get() = run {
        Color0.run {
            darkColorScheme(
                primary = this.md_theme_dark_primary,
                onPrimary = this.md_theme_dark_onPrimary,
                primaryContainer = this.md_theme_dark_primaryContainer,
                onPrimaryContainer = this.md_theme_dark_onPrimaryContainer,
                secondary = this.md_theme_dark_secondary,
                onSecondary = this.md_theme_dark_onSecondary,
                secondaryContainer = this.md_theme_dark_secondaryContainer,
                onSecondaryContainer = this.md_theme_dark_onSecondaryContainer,
                tertiary = this.md_theme_dark_tertiary,
                onTertiary = this.md_theme_dark_onTertiary,
                tertiaryContainer = this.md_theme_dark_tertiaryContainer,
                onTertiaryContainer = this.md_theme_dark_onTertiaryContainer,
                error = this.md_theme_dark_error,
                errorContainer = this.md_theme_dark_errorContainer,
                onError = this.md_theme_dark_onError,
                onErrorContainer = this.md_theme_dark_onErrorContainer,
                background = this.md_theme_dark_background,
                onBackground = this.md_theme_dark_onBackground,
                surface = this.md_theme_dark_surface,
                onSurface = this.md_theme_dark_onSurface,
                surfaceVariant = this.md_theme_dark_surfaceVariant,
                onSurfaceVariant = this.md_theme_dark_onSurfaceVariant,
                outline = this.md_theme_dark_outline,
                inverseOnSurface = this.md_theme_dark_inverseOnSurface,
                inverseSurface = this.md_theme_dark_inverseSurface,
                inversePrimary = this.md_theme_dark_inversePrimary,
                surfaceTint = this.md_theme_dark_surfaceTint,
                outlineVariant = this.md_theme_dark_outlineVariant,
                scrim = this.md_theme_dark_scrim,
            )
        }
    }

private val LightColors1
    get() = run {
        Color1.run {
            lightColorScheme(
                primary = this.md_theme_light_primary,
                onPrimary = this.md_theme_light_onPrimary,
                primaryContainer = this.md_theme_light_primaryContainer,
                onPrimaryContainer = this.md_theme_light_onPrimaryContainer,
                secondary = this.md_theme_light_secondary,
                onSecondary = this.md_theme_light_onSecondary,
                secondaryContainer = this.md_theme_light_secondaryContainer,
                onSecondaryContainer = this.md_theme_light_onSecondaryContainer,
                tertiary = this.md_theme_light_tertiary,
                onTertiary = this.md_theme_light_onTertiary,
                tertiaryContainer = this.md_theme_light_tertiaryContainer,
                onTertiaryContainer = this.md_theme_light_onTertiaryContainer,
                error = this.md_theme_light_error,
                errorContainer = this.md_theme_light_errorContainer,
                onError = this.md_theme_light_onError,
                onErrorContainer = this.md_theme_light_onErrorContainer,
                background = this.md_theme_light_background,
                onBackground = this.md_theme_light_onBackground,
                surface = this.md_theme_light_surface,
                onSurface = this.md_theme_light_onSurface,
                surfaceVariant = this.md_theme_light_surfaceVariant,
                onSurfaceVariant = this.md_theme_light_onSurfaceVariant,
                outline = this.md_theme_light_outline,
                inverseOnSurface = this.md_theme_light_inverseOnSurface,
                inverseSurface = this.md_theme_light_inverseSurface,
                inversePrimary = this.md_theme_light_inversePrimary,
                surfaceTint = this.md_theme_light_surfaceTint,
                outlineVariant = this.md_theme_light_outlineVariant,
                scrim = this.md_theme_light_scrim,
            )
        }
    }

private val DarkColors1
    get() = run {
        Color1.run {
            darkColorScheme(
                primary = this.md_theme_dark_primary,
                onPrimary = this.md_theme_dark_onPrimary,
                primaryContainer = this.md_theme_dark_primaryContainer,
                onPrimaryContainer = this.md_theme_dark_onPrimaryContainer,
                secondary = this.md_theme_dark_secondary,
                onSecondary = this.md_theme_dark_onSecondary,
                secondaryContainer = this.md_theme_dark_secondaryContainer,
                onSecondaryContainer = this.md_theme_dark_onSecondaryContainer,
                tertiary = this.md_theme_dark_tertiary,
                onTertiary = this.md_theme_dark_onTertiary,
                tertiaryContainer = this.md_theme_dark_tertiaryContainer,
                onTertiaryContainer = this.md_theme_dark_onTertiaryContainer,
                error = this.md_theme_dark_error,
                errorContainer = this.md_theme_dark_errorContainer,
                onError = this.md_theme_dark_onError,
                onErrorContainer = this.md_theme_dark_onErrorContainer,
                background = this.md_theme_dark_background,
                onBackground = this.md_theme_dark_onBackground,
                surface = this.md_theme_dark_surface,
                onSurface = this.md_theme_dark_onSurface,
                surfaceVariant = this.md_theme_dark_surfaceVariant,
                onSurfaceVariant = this.md_theme_dark_onSurfaceVariant,
                outline = this.md_theme_dark_outline,
                inverseOnSurface = this.md_theme_dark_inverseOnSurface,
                inverseSurface = this.md_theme_dark_inverseSurface,
                inversePrimary = this.md_theme_dark_inversePrimary,
                surfaceTint = this.md_theme_dark_surfaceTint,
                outlineVariant = this.md_theme_dark_outlineVariant,
                scrim = this.md_theme_dark_scrim,
            )
        }
    }

private val LightColors2
    get() = run {
        Color2.run {
            lightColorScheme(
                primary = this.md_theme_light_primary,
                onPrimary = this.md_theme_light_onPrimary,
                primaryContainer = this.md_theme_light_primaryContainer,
                onPrimaryContainer = this.md_theme_light_onPrimaryContainer,
                secondary = this.md_theme_light_secondary,
                onSecondary = this.md_theme_light_onSecondary,
                secondaryContainer = this.md_theme_light_secondaryContainer,
                onSecondaryContainer = this.md_theme_light_onSecondaryContainer,
                tertiary = this.md_theme_light_tertiary,
                onTertiary = this.md_theme_light_onTertiary,
                tertiaryContainer = this.md_theme_light_tertiaryContainer,
                onTertiaryContainer = this.md_theme_light_onTertiaryContainer,
                error = this.md_theme_light_error,
                errorContainer = this.md_theme_light_errorContainer,
                onError = this.md_theme_light_onError,
                onErrorContainer = this.md_theme_light_onErrorContainer,
                background = this.md_theme_light_background,
                onBackground = this.md_theme_light_onBackground,
                surface = this.md_theme_light_surface,
                onSurface = this.md_theme_light_onSurface,
                surfaceVariant = this.md_theme_light_surfaceVariant,
                onSurfaceVariant = this.md_theme_light_onSurfaceVariant,
                outline = this.md_theme_light_outline,
                inverseOnSurface = this.md_theme_light_inverseOnSurface,
                inverseSurface = this.md_theme_light_inverseSurface,
                inversePrimary = this.md_theme_light_inversePrimary,
                surfaceTint = this.md_theme_light_surfaceTint,
                outlineVariant = this.md_theme_light_outlineVariant,
                scrim = this.md_theme_light_scrim,
            )
        }
    }

private val DarkColors2
    get() = run {
        Color2.run {
            darkColorScheme(
                primary = this.md_theme_dark_primary,
                onPrimary = this.md_theme_dark_onPrimary,
                primaryContainer = this.md_theme_dark_primaryContainer,
                onPrimaryContainer = this.md_theme_dark_onPrimaryContainer,
                secondary = this.md_theme_dark_secondary,
                onSecondary = this.md_theme_dark_onSecondary,
                secondaryContainer = this.md_theme_dark_secondaryContainer,
                onSecondaryContainer = this.md_theme_dark_onSecondaryContainer,
                tertiary = this.md_theme_dark_tertiary,
                onTertiary = this.md_theme_dark_onTertiary,
                tertiaryContainer = this.md_theme_dark_tertiaryContainer,
                onTertiaryContainer = this.md_theme_dark_onTertiaryContainer,
                error = this.md_theme_dark_error,
                errorContainer = this.md_theme_dark_errorContainer,
                onError = this.md_theme_dark_onError,
                onErrorContainer = this.md_theme_dark_onErrorContainer,
                background = this.md_theme_dark_background,
                onBackground = this.md_theme_dark_onBackground,
                surface = this.md_theme_dark_surface,
                onSurface = this.md_theme_dark_onSurface,
                surfaceVariant = this.md_theme_dark_surfaceVariant,
                onSurfaceVariant = this.md_theme_dark_onSurfaceVariant,
                outline = this.md_theme_dark_outline,
                inverseOnSurface = this.md_theme_dark_inverseOnSurface,
                inverseSurface = this.md_theme_dark_inverseSurface,
                inversePrimary = this.md_theme_dark_inversePrimary,
                surfaceTint = this.md_theme_dark_surfaceTint,
                outlineVariant = this.md_theme_dark_outlineVariant,
                scrim = this.md_theme_dark_scrim,
            )
        }
    }

private val LightColors3
    get() = run {
        Color3.run {
            lightColorScheme(
                primary = this.md_theme_light_primary,
                onPrimary = this.md_theme_light_onPrimary,
                primaryContainer = this.md_theme_light_primaryContainer,
                onPrimaryContainer = this.md_theme_light_onPrimaryContainer,
                secondary = this.md_theme_light_secondary,
                onSecondary = this.md_theme_light_onSecondary,
                secondaryContainer = this.md_theme_light_secondaryContainer,
                onSecondaryContainer = this.md_theme_light_onSecondaryContainer,
                tertiary = this.md_theme_light_tertiary,
                onTertiary = this.md_theme_light_onTertiary,
                tertiaryContainer = this.md_theme_light_tertiaryContainer,
                onTertiaryContainer = this.md_theme_light_onTertiaryContainer,
                error = this.md_theme_light_error,
                errorContainer = this.md_theme_light_errorContainer,
                onError = this.md_theme_light_onError,
                onErrorContainer = this.md_theme_light_onErrorContainer,
                background = this.md_theme_light_background,
                onBackground = this.md_theme_light_onBackground,
                surface = this.md_theme_light_surface,
                onSurface = this.md_theme_light_onSurface,
                surfaceVariant = this.md_theme_light_surfaceVariant,
                onSurfaceVariant = this.md_theme_light_onSurfaceVariant,
                outline = this.md_theme_light_outline,
                inverseOnSurface = this.md_theme_light_inverseOnSurface,
                inverseSurface = this.md_theme_light_inverseSurface,
                inversePrimary = this.md_theme_light_inversePrimary,
                surfaceTint = this.md_theme_light_surfaceTint,
                outlineVariant = this.md_theme_light_outlineVariant,
                scrim = this.md_theme_light_scrim,
            )
        }
    }

private val DarkColors3
    get() = run {
        Color3.run {
            darkColorScheme(
                primary = this.md_theme_dark_primary,
                onPrimary = this.md_theme_dark_onPrimary,
                primaryContainer = this.md_theme_dark_primaryContainer,
                onPrimaryContainer = this.md_theme_dark_onPrimaryContainer,
                secondary = this.md_theme_dark_secondary,
                onSecondary = this.md_theme_dark_onSecondary,
                secondaryContainer = this.md_theme_dark_secondaryContainer,
                onSecondaryContainer = this.md_theme_dark_onSecondaryContainer,
                tertiary = this.md_theme_dark_tertiary,
                onTertiary = this.md_theme_dark_onTertiary,
                tertiaryContainer = this.md_theme_dark_tertiaryContainer,
                onTertiaryContainer = this.md_theme_dark_onTertiaryContainer,
                error = this.md_theme_dark_error,
                errorContainer = this.md_theme_dark_errorContainer,
                onError = this.md_theme_dark_onError,
                onErrorContainer = this.md_theme_dark_onErrorContainer,
                background = this.md_theme_dark_background,
                onBackground = this.md_theme_dark_onBackground,
                surface = this.md_theme_dark_surface,
                onSurface = this.md_theme_dark_onSurface,
                surfaceVariant = this.md_theme_dark_surfaceVariant,
                onSurfaceVariant = this.md_theme_dark_onSurfaceVariant,
                outline = this.md_theme_dark_outline,
                inverseOnSurface = this.md_theme_dark_inverseOnSurface,
                inverseSurface = this.md_theme_dark_inverseSurface,
                inversePrimary = this.md_theme_dark_inversePrimary,
                surfaceTint = this.md_theme_dark_surfaceTint,
                outlineVariant = this.md_theme_dark_outlineVariant,
                scrim = this.md_theme_dark_scrim,
            )
        }
    }

private val LightColors4
    get() = run {
        Color4.run {
            lightColorScheme(
                primary = this.md_theme_light_primary,
                onPrimary = this.md_theme_light_onPrimary,
                primaryContainer = this.md_theme_light_primaryContainer,
                onPrimaryContainer = this.md_theme_light_onPrimaryContainer,
                secondary = this.md_theme_light_secondary,
                onSecondary = this.md_theme_light_onSecondary,
                secondaryContainer = this.md_theme_light_secondaryContainer,
                onSecondaryContainer = this.md_theme_light_onSecondaryContainer,
                tertiary = this.md_theme_light_tertiary,
                onTertiary = this.md_theme_light_onTertiary,
                tertiaryContainer = this.md_theme_light_tertiaryContainer,
                onTertiaryContainer = this.md_theme_light_onTertiaryContainer,
                error = this.md_theme_light_error,
                errorContainer = this.md_theme_light_errorContainer,
                onError = this.md_theme_light_onError,
                onErrorContainer = this.md_theme_light_onErrorContainer,
                background = this.md_theme_light_background,
                onBackground = this.md_theme_light_onBackground,
                surface = this.md_theme_light_surface,
                onSurface = this.md_theme_light_onSurface,
                surfaceVariant = this.md_theme_light_surfaceVariant,
                onSurfaceVariant = this.md_theme_light_onSurfaceVariant,
                outline = this.md_theme_light_outline,
                inverseOnSurface = this.md_theme_light_inverseOnSurface,
                inverseSurface = this.md_theme_light_inverseSurface,
                inversePrimary = this.md_theme_light_inversePrimary,
                surfaceTint = this.md_theme_light_surfaceTint,
                outlineVariant = this.md_theme_light_outlineVariant,
                scrim = this.md_theme_light_scrim,
            )
        }
    }

private val DarkColors4
    get() = run {
        Color4.run {
            darkColorScheme(
                primary = this.md_theme_dark_primary,
                onPrimary = this.md_theme_dark_onPrimary,
                primaryContainer = this.md_theme_dark_primaryContainer,
                onPrimaryContainer = this.md_theme_dark_onPrimaryContainer,
                secondary = this.md_theme_dark_secondary,
                onSecondary = this.md_theme_dark_onSecondary,
                secondaryContainer = this.md_theme_dark_secondaryContainer,
                onSecondaryContainer = this.md_theme_dark_onSecondaryContainer,
                tertiary = this.md_theme_dark_tertiary,
                onTertiary = this.md_theme_dark_onTertiary,
                tertiaryContainer = this.md_theme_dark_tertiaryContainer,
                onTertiaryContainer = this.md_theme_dark_onTertiaryContainer,
                error = this.md_theme_dark_error,
                errorContainer = this.md_theme_dark_errorContainer,
                onError = this.md_theme_dark_onError,
                onErrorContainer = this.md_theme_dark_onErrorContainer,
                background = this.md_theme_dark_background,
                onBackground = this.md_theme_dark_onBackground,
                surface = this.md_theme_dark_surface,
                onSurface = this.md_theme_dark_onSurface,
                surfaceVariant = this.md_theme_dark_surfaceVariant,
                onSurfaceVariant = this.md_theme_dark_onSurfaceVariant,
                outline = this.md_theme_dark_outline,
                inverseOnSurface = this.md_theme_dark_inverseOnSurface,
                inverseSurface = this.md_theme_dark_inverseSurface,
                inversePrimary = this.md_theme_dark_inversePrimary,
                surfaceTint = this.md_theme_dark_surfaceTint,
                outlineVariant = this.md_theme_dark_outlineVariant,
                scrim = this.md_theme_dark_scrim,
            )
        }
    }

private val LightColors5
    get() = run {
        Color5.run {
            lightColorScheme(
                primary = this.md_theme_light_primary,
                onPrimary = this.md_theme_light_onPrimary,
                primaryContainer = this.md_theme_light_primaryContainer,
                onPrimaryContainer = this.md_theme_light_onPrimaryContainer,
                secondary = this.md_theme_light_secondary,
                onSecondary = this.md_theme_light_onSecondary,
                secondaryContainer = this.md_theme_light_secondaryContainer,
                onSecondaryContainer = this.md_theme_light_onSecondaryContainer,
                tertiary = this.md_theme_light_tertiary,
                onTertiary = this.md_theme_light_onTertiary,
                tertiaryContainer = this.md_theme_light_tertiaryContainer,
                onTertiaryContainer = this.md_theme_light_onTertiaryContainer,
                error = this.md_theme_light_error,
                errorContainer = this.md_theme_light_errorContainer,
                onError = this.md_theme_light_onError,
                onErrorContainer = this.md_theme_light_onErrorContainer,
                background = this.md_theme_light_background,
                onBackground = this.md_theme_light_onBackground,
                surface = this.md_theme_light_surface,
                onSurface = this.md_theme_light_onSurface,
                surfaceVariant = this.md_theme_light_surfaceVariant,
                onSurfaceVariant = this.md_theme_light_onSurfaceVariant,
                outline = this.md_theme_light_outline,
                inverseOnSurface = this.md_theme_light_inverseOnSurface,
                inverseSurface = this.md_theme_light_inverseSurface,
                inversePrimary = this.md_theme_light_inversePrimary,
                surfaceTint = this.md_theme_light_surfaceTint,
                outlineVariant = this.md_theme_light_outlineVariant,
                scrim = this.md_theme_light_scrim,
            )
        }
    }

private val DarkColors5
    get() = run {
        Color5.run {
            darkColorScheme(
                primary = this.md_theme_dark_primary,
                onPrimary = this.md_theme_dark_onPrimary,
                primaryContainer = this.md_theme_dark_primaryContainer,
                onPrimaryContainer = this.md_theme_dark_onPrimaryContainer,
                secondary = this.md_theme_dark_secondary,
                onSecondary = this.md_theme_dark_onSecondary,
                secondaryContainer = this.md_theme_dark_secondaryContainer,
                onSecondaryContainer = this.md_theme_dark_onSecondaryContainer,
                tertiary = this.md_theme_dark_tertiary,
                onTertiary = this.md_theme_dark_onTertiary,
                tertiaryContainer = this.md_theme_dark_tertiaryContainer,
                onTertiaryContainer = this.md_theme_dark_onTertiaryContainer,
                error = this.md_theme_dark_error,
                errorContainer = this.md_theme_dark_errorContainer,
                onError = this.md_theme_dark_onError,
                onErrorContainer = this.md_theme_dark_onErrorContainer,
                background = this.md_theme_dark_background,
                onBackground = this.md_theme_dark_onBackground,
                surface = this.md_theme_dark_surface,
                onSurface = this.md_theme_dark_onSurface,
                surfaceVariant = this.md_theme_dark_surfaceVariant,
                onSurfaceVariant = this.md_theme_dark_onSurfaceVariant,
                outline = this.md_theme_dark_outline,
                inverseOnSurface = this.md_theme_dark_inverseOnSurface,
                inverseSurface = this.md_theme_dark_inverseSurface,
                inversePrimary = this.md_theme_dark_inversePrimary,
                surfaceTint = this.md_theme_dark_surfaceTint,
                outlineVariant = this.md_theme_dark_outlineVariant,
                scrim = this.md_theme_dark_scrim,
            )
        }
    }

private val LightColors6
    get() = run {
        Color6.run {
            lightColorScheme(
                primary = this.md_theme_light_primary,
                onPrimary = this.md_theme_light_onPrimary,
                primaryContainer = this.md_theme_light_primaryContainer,
                onPrimaryContainer = this.md_theme_light_onPrimaryContainer,
                secondary = this.md_theme_light_secondary,
                onSecondary = this.md_theme_light_onSecondary,
                secondaryContainer = this.md_theme_light_secondaryContainer,
                onSecondaryContainer = this.md_theme_light_onSecondaryContainer,
                tertiary = this.md_theme_light_tertiary,
                onTertiary = this.md_theme_light_onTertiary,
                tertiaryContainer = this.md_theme_light_tertiaryContainer,
                onTertiaryContainer = this.md_theme_light_onTertiaryContainer,
                error = this.md_theme_light_error,
                errorContainer = this.md_theme_light_errorContainer,
                onError = this.md_theme_light_onError,
                onErrorContainer = this.md_theme_light_onErrorContainer,
                background = this.md_theme_light_background,
                onBackground = this.md_theme_light_onBackground,
                surface = this.md_theme_light_surface,
                onSurface = this.md_theme_light_onSurface,
                surfaceVariant = this.md_theme_light_surfaceVariant,
                onSurfaceVariant = this.md_theme_light_onSurfaceVariant,
                outline = this.md_theme_light_outline,
                inverseOnSurface = this.md_theme_light_inverseOnSurface,
                inverseSurface = this.md_theme_light_inverseSurface,
                inversePrimary = this.md_theme_light_inversePrimary,
                surfaceTint = this.md_theme_light_surfaceTint,
                outlineVariant = this.md_theme_light_outlineVariant,
                scrim = this.md_theme_light_scrim,
            )
        }
    }

private val DarkColors6
    get() = run {
        Color6.run {
            darkColorScheme(
                primary = this.md_theme_dark_primary,
                onPrimary = this.md_theme_dark_onPrimary,
                primaryContainer = this.md_theme_dark_primaryContainer,
                onPrimaryContainer = this.md_theme_dark_onPrimaryContainer,
                secondary = this.md_theme_dark_secondary,
                onSecondary = this.md_theme_dark_onSecondary,
                secondaryContainer = this.md_theme_dark_secondaryContainer,
                onSecondaryContainer = this.md_theme_dark_onSecondaryContainer,
                tertiary = this.md_theme_dark_tertiary,
                onTertiary = this.md_theme_dark_onTertiary,
                tertiaryContainer = this.md_theme_dark_tertiaryContainer,
                onTertiaryContainer = this.md_theme_dark_onTertiaryContainer,
                error = this.md_theme_dark_error,
                errorContainer = this.md_theme_dark_errorContainer,
                onError = this.md_theme_dark_onError,
                onErrorContainer = this.md_theme_dark_onErrorContainer,
                background = this.md_theme_dark_background,
                onBackground = this.md_theme_dark_onBackground,
                surface = this.md_theme_dark_surface,
                onSurface = this.md_theme_dark_onSurface,
                surfaceVariant = this.md_theme_dark_surfaceVariant,
                onSurfaceVariant = this.md_theme_dark_onSurfaceVariant,
                outline = this.md_theme_dark_outline,
                inverseOnSurface = this.md_theme_dark_inverseOnSurface,
                inverseSurface = this.md_theme_dark_inverseSurface,
                inversePrimary = this.md_theme_dark_inversePrimary,
                surfaceTint = this.md_theme_dark_surfaceTint,
                outlineVariant = this.md_theme_dark_outlineVariant,
                scrim = this.md_theme_dark_scrim,
            )
        }
    }

private val LightColors7
    get() = run {
        Color7.run {
            lightColorScheme(
                primary = this.md_theme_light_primary,
                onPrimary = this.md_theme_light_onPrimary,
                primaryContainer = this.md_theme_light_primaryContainer,
                onPrimaryContainer = this.md_theme_light_onPrimaryContainer,
                secondary = this.md_theme_light_secondary,
                onSecondary = this.md_theme_light_onSecondary,
                secondaryContainer = this.md_theme_light_secondaryContainer,
                onSecondaryContainer = this.md_theme_light_onSecondaryContainer,
                tertiary = this.md_theme_light_tertiary,
                onTertiary = this.md_theme_light_onTertiary,
                tertiaryContainer = this.md_theme_light_tertiaryContainer,
                onTertiaryContainer = this.md_theme_light_onTertiaryContainer,
                error = this.md_theme_light_error,
                errorContainer = this.md_theme_light_errorContainer,
                onError = this.md_theme_light_onError,
                onErrorContainer = this.md_theme_light_onErrorContainer,
                background = this.md_theme_light_background,
                onBackground = this.md_theme_light_onBackground,
                surface = this.md_theme_light_surface,
                onSurface = this.md_theme_light_onSurface,
                surfaceVariant = this.md_theme_light_surfaceVariant,
                onSurfaceVariant = this.md_theme_light_onSurfaceVariant,
                outline = this.md_theme_light_outline,
                inverseOnSurface = this.md_theme_light_inverseOnSurface,
                inverseSurface = this.md_theme_light_inverseSurface,
                inversePrimary = this.md_theme_light_inversePrimary,
                surfaceTint = this.md_theme_light_surfaceTint,
                outlineVariant = this.md_theme_light_outlineVariant,
                scrim = this.md_theme_light_scrim,
            )
        }
    }

private val DarkColors7
    get() = run {
        Color7.run {
            darkColorScheme(
                primary = this.md_theme_dark_primary,
                onPrimary = this.md_theme_dark_onPrimary,
                primaryContainer = this.md_theme_dark_primaryContainer,
                onPrimaryContainer = this.md_theme_dark_onPrimaryContainer,
                secondary = this.md_theme_dark_secondary,
                onSecondary = this.md_theme_dark_onSecondary,
                secondaryContainer = this.md_theme_dark_secondaryContainer,
                onSecondaryContainer = this.md_theme_dark_onSecondaryContainer,
                tertiary = this.md_theme_dark_tertiary,
                onTertiary = this.md_theme_dark_onTertiary,
                tertiaryContainer = this.md_theme_dark_tertiaryContainer,
                onTertiaryContainer = this.md_theme_dark_onTertiaryContainer,
                error = this.md_theme_dark_error,
                errorContainer = this.md_theme_dark_errorContainer,
                onError = this.md_theme_dark_onError,
                onErrorContainer = this.md_theme_dark_onErrorContainer,
                background = this.md_theme_dark_background,
                onBackground = this.md_theme_dark_onBackground,
                surface = this.md_theme_dark_surface,
                onSurface = this.md_theme_dark_onSurface,
                surfaceVariant = this.md_theme_dark_surfaceVariant,
                onSurfaceVariant = this.md_theme_dark_onSurfaceVariant,
                outline = this.md_theme_dark_outline,
                inverseOnSurface = this.md_theme_dark_inverseOnSurface,
                inverseSurface = this.md_theme_dark_inverseSurface,
                inversePrimary = this.md_theme_dark_inversePrimary,
                surfaceTint = this.md_theme_dark_surfaceTint,
                outlineVariant = this.md_theme_dark_outlineVariant,
                scrim = this.md_theme_dark_scrim,
            )
        }
    }

private val LightColors8
    get() = run {
        Color8.run {
            lightColorScheme(
                primary = this.md_theme_light_primary,
                onPrimary = this.md_theme_light_onPrimary,
                primaryContainer = this.md_theme_light_primaryContainer,
                onPrimaryContainer = this.md_theme_light_onPrimaryContainer,
                secondary = this.md_theme_light_secondary,
                onSecondary = this.md_theme_light_onSecondary,
                secondaryContainer = this.md_theme_light_secondaryContainer,
                onSecondaryContainer = this.md_theme_light_onSecondaryContainer,
                tertiary = this.md_theme_light_tertiary,
                onTertiary = this.md_theme_light_onTertiary,
                tertiaryContainer = this.md_theme_light_tertiaryContainer,
                onTertiaryContainer = this.md_theme_light_onTertiaryContainer,
                error = this.md_theme_light_error,
                errorContainer = this.md_theme_light_errorContainer,
                onError = this.md_theme_light_onError,
                onErrorContainer = this.md_theme_light_onErrorContainer,
                background = this.md_theme_light_background,
                onBackground = this.md_theme_light_onBackground,
                surface = this.md_theme_light_surface,
                onSurface = this.md_theme_light_onSurface,
                surfaceVariant = this.md_theme_light_surfaceVariant,
                onSurfaceVariant = this.md_theme_light_onSurfaceVariant,
                outline = this.md_theme_light_outline,
                inverseOnSurface = this.md_theme_light_inverseOnSurface,
                inverseSurface = this.md_theme_light_inverseSurface,
                inversePrimary = this.md_theme_light_inversePrimary,
                surfaceTint = this.md_theme_light_surfaceTint,
                outlineVariant = this.md_theme_light_outlineVariant,
                scrim = this.md_theme_light_scrim,
            )
        }
    }

private val DarkColors8
    get() = run {
        Color8.run {
            darkColorScheme(
                primary = this.md_theme_dark_primary,
                onPrimary = this.md_theme_dark_onPrimary,
                primaryContainer = this.md_theme_dark_primaryContainer,
                onPrimaryContainer = this.md_theme_dark_onPrimaryContainer,
                secondary = this.md_theme_dark_secondary,
                onSecondary = this.md_theme_dark_onSecondary,
                secondaryContainer = this.md_theme_dark_secondaryContainer,
                onSecondaryContainer = this.md_theme_dark_onSecondaryContainer,
                tertiary = this.md_theme_dark_tertiary,
                onTertiary = this.md_theme_dark_onTertiary,
                tertiaryContainer = this.md_theme_dark_tertiaryContainer,
                onTertiaryContainer = this.md_theme_dark_onTertiaryContainer,
                error = this.md_theme_dark_error,
                errorContainer = this.md_theme_dark_errorContainer,
                onError = this.md_theme_dark_onError,
                onErrorContainer = this.md_theme_dark_onErrorContainer,
                background = this.md_theme_dark_background,
                onBackground = this.md_theme_dark_onBackground,
                surface = this.md_theme_dark_surface,
                onSurface = this.md_theme_dark_onSurface,
                surfaceVariant = this.md_theme_dark_surfaceVariant,
                onSurfaceVariant = this.md_theme_dark_onSurfaceVariant,
                outline = this.md_theme_dark_outline,
                inverseOnSurface = this.md_theme_dark_inverseOnSurface,
                inverseSurface = this.md_theme_dark_inverseSurface,
                inversePrimary = this.md_theme_dark_inversePrimary,
                surfaceTint = this.md_theme_dark_surfaceTint,
                outlineVariant = this.md_theme_dark_outlineVariant,
                scrim = this.md_theme_dark_scrim,
            )
        }
    }

private val LightColors9
    get() = run {
        Color9.run {
            lightColorScheme(
                primary = this.md_theme_light_primary,
                onPrimary = this.md_theme_light_onPrimary,
                primaryContainer = this.md_theme_light_primaryContainer,
                onPrimaryContainer = this.md_theme_light_onPrimaryContainer,
                secondary = this.md_theme_light_secondary,
                onSecondary = this.md_theme_light_onSecondary,
                secondaryContainer = this.md_theme_light_secondaryContainer,
                onSecondaryContainer = this.md_theme_light_onSecondaryContainer,
                tertiary = this.md_theme_light_tertiary,
                onTertiary = this.md_theme_light_onTertiary,
                tertiaryContainer = this.md_theme_light_tertiaryContainer,
                onTertiaryContainer = this.md_theme_light_onTertiaryContainer,
                error = this.md_theme_light_error,
                errorContainer = this.md_theme_light_errorContainer,
                onError = this.md_theme_light_onError,
                onErrorContainer = this.md_theme_light_onErrorContainer,
                background = this.md_theme_light_background,
                onBackground = this.md_theme_light_onBackground,
                surface = this.md_theme_light_surface,
                onSurface = this.md_theme_light_onSurface,
                surfaceVariant = this.md_theme_light_surfaceVariant,
                onSurfaceVariant = this.md_theme_light_onSurfaceVariant,
                outline = this.md_theme_light_outline,
                inverseOnSurface = this.md_theme_light_inverseOnSurface,
                inverseSurface = this.md_theme_light_inverseSurface,
                inversePrimary = this.md_theme_light_inversePrimary,
                surfaceTint = this.md_theme_light_surfaceTint,
                outlineVariant = this.md_theme_light_outlineVariant,
                scrim = this.md_theme_light_scrim,
            )
        }
    }

private val DarkColors9
    get() = run {
        Color9.run {
            darkColorScheme(
                primary = this.md_theme_dark_primary,
                onPrimary = this.md_theme_dark_onPrimary,
                primaryContainer = this.md_theme_dark_primaryContainer,
                onPrimaryContainer = this.md_theme_dark_onPrimaryContainer,
                secondary = this.md_theme_dark_secondary,
                onSecondary = this.md_theme_dark_onSecondary,
                secondaryContainer = this.md_theme_dark_secondaryContainer,
                onSecondaryContainer = this.md_theme_dark_onSecondaryContainer,
                tertiary = this.md_theme_dark_tertiary,
                onTertiary = this.md_theme_dark_onTertiary,
                tertiaryContainer = this.md_theme_dark_tertiaryContainer,
                onTertiaryContainer = this.md_theme_dark_onTertiaryContainer,
                error = this.md_theme_dark_error,
                errorContainer = this.md_theme_dark_errorContainer,
                onError = this.md_theme_dark_onError,
                onErrorContainer = this.md_theme_dark_onErrorContainer,
                background = this.md_theme_dark_background,
                onBackground = this.md_theme_dark_onBackground,
                surface = this.md_theme_dark_surface,
                onSurface = this.md_theme_dark_onSurface,
                surfaceVariant = this.md_theme_dark_surfaceVariant,
                onSurfaceVariant = this.md_theme_dark_onSurfaceVariant,
                outline = this.md_theme_dark_outline,
                inverseOnSurface = this.md_theme_dark_inverseOnSurface,
                inverseSurface = this.md_theme_dark_inverseSurface,
                inversePrimary = this.md_theme_dark_inversePrimary,
                surfaceTint = this.md_theme_dark_surfaceTint,
                outlineVariant = this.md_theme_dark_outlineVariant,
                scrim = this.md_theme_dark_scrim,
            )
        }
    }

