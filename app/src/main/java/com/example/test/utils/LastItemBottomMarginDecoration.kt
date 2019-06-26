package com.example.test.utils

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View

/**Конечные отступы для списков (доп. отступы снизу для последнего элемента списка) */
class LastItemBottomMarginDecoration(
    private val pxMargin: Int,
    private val listSize: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // Вычисление пикселей по dp. Здесь отступ будет равен "pxMargin"
        if (parent.getChildAdapterPosition(view) == listSize)
            outRect.bottom = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                pxMargin.toFloat(),
                view.resources.displayMetrics
            ).toInt()
    }
}
