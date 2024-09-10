package com.qstack.maptos.ui.home

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.qstack.maptos.R
import kotlin.math.abs

class WhiteOverlayBehavior(context: Context, attrs: AttributeSet) : AppBarLayout.Behavior(context, attrs) {

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type
        )

        // 监听滑动偏移量
        val totalScrollRange = child.totalScrollRange
        val currentScroll = abs(child.top) // 获取当前滑动偏移量
        val alpha = currentScroll.toFloat() / totalScrollRange // 计算透明度比例

        // 查找需要渐变的视图
        val overlayView = coordinatorLayout.findViewById<View>(R.id.whiteOverlay)

        // 设置透明度，实现渐变效果
        overlayView.alpha = 0.2f // alpha值从 0 (完全透明) 到 1 (完全不透明)
    }
}
