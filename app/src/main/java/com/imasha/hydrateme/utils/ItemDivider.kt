package com.imasha.hydrateme.utils

import android.graphics.Canvas
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.Drawable
import android.content.Context
import androidx.core.content.ContextCompat

class ItemDivider(context: Context, dividerDrawableRes: Int) : RecyclerView.ItemDecoration() {

    private val divider: Drawable? = ContextCompat.getDrawable(context, dividerDrawableRes)

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        divider ?: return

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + (divider.intrinsicHeight ?: 0)

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: android.view.View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position < state.itemCount - 1) {
            outRect.bottom = divider?.intrinsicHeight ?: 0
        }
    }
}
