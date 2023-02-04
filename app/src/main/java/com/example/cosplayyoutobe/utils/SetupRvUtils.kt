package com.example.cosplayyoutobe.utils

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.recyclerview.widget.*

fun setupLinearLayoutRecyclerView(
    context: Context?,
    recyclerView: RecyclerView
) {
    val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
    recyclerView.layoutManager = mLayoutManager
    recyclerView.itemAnimator = DefaultItemAnimator()
    recyclerView.setHasFixedSize(true)
}

fun setupLinearLayoutWithDividerRecyclerView(
    context: Context?,
    recyclerView: RecyclerView
) {
    // setLayoutManager
    val llm = LinearLayoutManager(context)
    llm.orientation = LinearLayoutManager.VERTICAL
    val DividerItemDecoration = DividerItemDecoration(
        recyclerView.context,
        llm.orientation
    )
    recyclerView.addItemDecoration(DividerItemDecoration)
    recyclerView.itemAnimator = DefaultItemAnimator()
    recyclerView.setHasFixedSize(true)
    recyclerView.isNestedScrollingEnabled = false
    recyclerView.layoutManager = llm
}

fun setupStaggeredGridRecyclerView(recyclerView: RecyclerView, column: Int) {
    val mLayoutManager =
        StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL)
    recyclerView.layoutManager = mLayoutManager
    recyclerView.setItemViewCacheSize(20)
    recyclerView.isDrawingCacheEnabled = true
    recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
}

fun setupGridLayoutRecyclerView(
    context: Context?,
    recyclerView: RecyclerView,
    column: Int
) {
    val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, column)
    recyclerView.layoutManager = mLayoutManager
    recyclerView.itemAnimator = DefaultItemAnimator()
    recyclerView.setHasFixedSize(true)
    recyclerView.isNestedScrollingEnabled = false
}

fun RecyclerView.getViewHolderForAdapterPosition(positionViewInRecyclerView: Int, idView: Int) : View? {
    val viewHolder : RecyclerView.ViewHolder? = this.findViewHolderForAdapterPosition(positionViewInRecyclerView)
    return viewHolder?.itemView?.findViewById(idView)
}

fun setTextColorItem(textView: TextView, intArrayColor: IntArray){
    textView.viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            textView.viewTreeObserver.removeOnGlobalLayoutListener(this)
            val height= textView.height.toFloat()
            val width= textView.width.toFloat()
            textView.paint.shader = LinearGradient(
                width/2, height, width/2,0f , intArrayColor,
                null, Shader.TileMode.CLAMP
            )
        }
    })
}

fun RecyclerView.smoothScrollToPositionWithOfSet(position :Int){
    this.smoothScrollToPosition(position)
    (layoutManager as GridLayoutManager).scrollToPositionWithOffset(position,0)
}

fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {
    val smoothScroller = object : LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int = snapMode
        override fun getHorizontalSnapPreference(): Int = snapMode
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}