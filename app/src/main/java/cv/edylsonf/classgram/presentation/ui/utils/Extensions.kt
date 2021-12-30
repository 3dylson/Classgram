package cv.edylsonf.classgram.presentation.ui.utils

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt

fun View.isRtl() = layoutDirection == View.LAYOUT_DIRECTION_RTL

/** Reads the color attribute from the theme for given [colorAttributeId] */
fun Context.getColorFromTheme(colorAttributeId: Int): Int {
    val typedValue = TypedValue()
    val typedArray: TypedArray =
        this.obtainStyledAttributes(
            typedValue.data, intArrayOf(colorAttributeId)
        )
    @ColorInt val color = typedArray.getColor(0, 0)
    typedArray.recycle()
    return color
}

fun newStaticLayout(
    source: CharSequence,
    paint: TextPaint,
    width: Int,
    alignment: Layout.Alignment,
    spacingmult: Float,
    spacingadd: Float,
    includepad: Boolean
): StaticLayout {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        StaticLayout.Builder.obtain(source, 0, source.length, paint, width).apply {
            setAlignment(alignment)
            setLineSpacing(spacingadd, spacingmult)
            setIncludePad(includepad)
        }.build()
    } else {
        @Suppress("DEPRECATION")
        (StaticLayout(
            source,
            paint,
            width,
            alignment,
            spacingmult,
            spacingadd,
            includepad
        ))
    }
}