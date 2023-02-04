package com.example.cosplayyoutobe.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.SharedElementCallback
import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.PopupWindow
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.youtube.player.YouTubeBaseActivity

abstract class BaseActivity<BINDING : ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: BINDING
    lateinit var loadingDialog: PopupWindow
    lateinit var popupLayoutReject: View
    private var dialog: Dialog? = null
    private var timeStartLoading = 0L
    private var timeDelayLoading = 1 //second

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = DataBindingUtil.setContentView(this, getContentLayout())
        setContentView(binding.root)
        initView()
        getLayoutLoading()
        initListener()
        observerLiveData()
    }

    abstract fun getContentLayout(): Int

    abstract fun initView()

    abstract fun initListener()

    abstract fun observerLiveData()

    abstract fun getLayoutLoading(): View?

    private fun showErrorDialog(@StringRes id: Int) {
        if (loadingDialog.isShowing) loadingDialog.dismiss()
    }

    private fun showErrorSnackBar(@StringRes id: Int) {
        Snackbar.make(binding.root, id, Snackbar.LENGTH_LONG).show()
    }

    private fun showErrorDialog(error: String) {
        if (loadingDialog.isShowing) loadingDialog.dismiss()
    }

    fun showErrorSnackBar(error: String) {
        /**
        if (connectionLiveData.value == true) Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
         */
    }


    //clear focus edittext when touch outside
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val view: View? = currentFocus
            if (view is EditText) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
            }
        }
        return super.dispatchTouchEvent(event)
    }

    protected fun <T : Any> handleResult(
        result: Result<T>,
        onSuccess: (response: T) -> Unit
    ) {
        when (result) {
            is Result.InProgress -> {
                timeStartLoading = System.currentTimeMillis()
            }
            is Result.Success<T> -> {
                onSuccess.invoke(result.data)

            }
            is Result.Failure -> {
            }
            is Result.Error -> {
                showErrorSnackBar(result.toString())
            }
            is Result.Failures<*> -> {
                showErrorSnackBar(result.toString())
            }
        }
    }

//    protected fun <T : Any> handleResultWithoutLoading(
//        result: Result<T>,
//        onSuccess: (response: T) -> Unit
//    ) {
//        when (result) {
//            is Result.InProgress -> {
//            }
//            is Result.Success<T> -> {
//                onSuccess.invoke(result.data)
//            }
//            is Result.Failure -> {
//            }
//            is Result.Error -> {
//                showErrorSnackBar(result.toString())
//            }
//            is Result.Failures<*> -> {
//                showErrorSnackBar(result.toString())
//            }
//        }
//    }

    fun setExitSharedElementCallback(key: String, value: View?) {
        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                if (value == null) {
                    return
                }
                sharedElements?.put(key, value)
            }
        })
    }
}
