package one.xcorp.caturday.extension

import android.view.View

fun View.setPaddingLeft(value: Int) = setPadding(
    value,
    paddingTop,
    paddingRight,
    paddingBottom
)

fun View.setPaddingTop(value: Int) = setPadding(
    paddingLeft,
    value,
    paddingRight,
    paddingBottom
)

fun View.setPaddingRight(value: Int) = setPadding(
    paddingLeft,
    paddingTop,
    value,
    paddingBottom
)

fun View.setPaddingBottom(value: Int) = setPadding(
    paddingLeft,
    paddingTop,
    paddingRight,
    value
)
