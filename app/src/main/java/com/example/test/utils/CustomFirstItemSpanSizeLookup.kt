package com.example.test.utils

import android.support.v7.widget.GridLayoutManager
import com.example.test.ui.adapters.SearchAdapter

class CustomFirstItemSpanSizeLookup(
    private val adapterSearch: SearchAdapter
) : GridLayoutManager.SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int =
        if (adapterSearch.getItemViewType(position) == 0) 2
        else 1
}
