package com.example.test.utils

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View

/**Начальные отступы для списков (доп. отступы для первого элемента списка) */
class FirstItemLeftMarginDecoration(private val pxMargin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // Вычисление пикселей по dp. Здесь отступ будет равен "pxMargin"
        if (parent.getChildAdapterPosition(view) == 0)
            outRect.left = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                pxMargin.toFloat(),
                view.resources.displayMetrics
            ).toInt()
    }
}
