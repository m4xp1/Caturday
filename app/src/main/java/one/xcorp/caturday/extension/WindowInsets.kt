package one.xcorp.caturday.extension

import android.graphics.Insets
import android.os.Build
import android.view.WindowInsets

fun WindowInsets.chaneSystemWindowLeftInset(value: Int) = chaneSystemWindowInsets(
    value,
    systemWindowInsetTop,
    systemWindowInsetRight,
    systemWindowInsetBottom
)

fun WindowInsets.chaneSystemWindowTopInset(value: Int) = chaneSystemWindowInsets(
    systemWindowInsetLeft,
    value,
    systemWindowInsetRight,
    systemWindowInsetBottom
)

fun WindowInsets.chaneSystemWindowRightInset(value: Int) = chaneSystemWindowInsets(
    systemWindowInsetLeft,
    systemWindowInsetTop,
    value,
    systemWindowInsetBottom
)

fun WindowInsets.chaneSystemWindowBottomInset(value: Int) = chaneSystemWindowInsets(
    systemWindowInsetLeft,
    systemWindowInsetTop,
    systemWindowInsetRight,
    value
)

fun WindowInsets.chaneSystemWindowInsets(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
): WindowInsets {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        @Suppress("DEPRECATION")
        replaceSystemWindowInsets(left, top, right, bottom)
    } else {
        val newSystemWindowInsets = Insets.of(left, top, right, bottom)

        WindowInsets.Builder(this)
            .setSystemWindowInsets(newSystemWindowInsets)
            .build()
    }
}
