package io.github.sovathna.ui.splash

data class SplashState(
    val progress: Float = 0.0f,
    val shouldRedirect: Boolean = false,
    val throwable: Throwable? = null
)
