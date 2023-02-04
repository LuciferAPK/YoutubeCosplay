package com.example.cosplayyoutobe.ui.screen.detail

import android.os.Bundle
import android.view.View
import com.example.cosplayyoutobe.R
import com.example.cosplayyoutobe.base.BaseBottomSheetDialog
import com.example.cosplayyoutobe.base.BaseFragment
import com.example.cosplayyoutobe.databinding.LayoutCommentsBottomSheetBinding
import com.example.cosplayyoutobe.ext.displayMetrics
import com.example.cosplayyoutobe.ext.heightScreen
import com.example.cosplayyoutobe.utils.StatusBarUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentsBottomSheet: BaseBottomSheetDialog<LayoutCommentsBottomSheetBinding>() {

    override fun getLayoutResource(): Int {
        return R.layout.layout_comments_bottom_sheet
    }

    override fun initView(saveInstanceState: Bundle?, view: View?) {
        setHeight()
        StatusBarUtils.makeStatusBarTransparentAndLight(requireActivity())
    }

    private fun setHeight() {
        if (dialog is BottomSheetDialog) {
            val dialog = dialog as BottomSheetDialog
            val behavior: BottomSheetBehavior<*> = dialog.behavior
            behavior.peekHeight = displayMetrics.heightPixels - resources.getDimensionPixelOffset(R.dimen.dp60)
            binding.viewParent.minHeight = heightScreen - resources.getDimensionPixelOffset(R.dimen.dp60)
        }
    }

    override fun initListener(view: View?) {
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
    }

//    override fun init(saveInstanceState: Bundle?, view: View?) {
//        setHeight()
//        StatusBarUtils.makeStatusBarTransparentAndLight(requireActivity())
//    }
//
//    override fun setUp(view: View?) {
//        binding.btnClose.setOnClickListener {
//            dismiss()
//        }
//    }
}