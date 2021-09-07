package com.vertigo.andersen_homework_6.recyclers

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

class CustomItemDecorator(val context: Context?): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            left = 25
            top = 25
            bottom = 25
            right = 25
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        }
    }
}