package com.example.cosplayyoutobe.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Created by thevu2907@gmail.com on 22/02/2022.
 */
abstract class BaseFragment<BINDING : ViewDataBinding> :
    Fragment() {
    lateinit var binding: BINDING
    lateinit var loadingDialog : PopupWindow
    lateinit var  popupLayoutReject: View
    private var timeStartLoading = 0L
    private var timeDelayLoading = 1 //second

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getContentLayout(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    protected fun <T : Any> handleResult(result: Result<T>, onSuccess: (response: T) -> Unit) {
        when(result) {
            is Result.InProgress -> {
                timeStartLoading = System.currentTimeMillis()
            }
            is Result.Success<T> -> {
                onSuccess.invoke(result.data)
            }
            is Result.Failure -> {
                showErrorSnackBar(result.toString())
            }
            is Result.Error -> {
                showErrorSnackBar(result.toString())
            }
            is Result.Failures<*> -> {
                showErrorSnackBar(result.toString())
            }
        }
    }

//    protected fun <T : Any> handleResultWithoutLoading(result: Result<T>, onSuccess: (response: T) -> Unit) {
//        when(result) {
//            is Result.InProgress -> {
//            }
//            is Result.Success<T> -> {
//                onSuccess.invoke(result.data)
//            }
//            is Result.Failure -> {
//                showErrorSnackBar(result.toString())
//            }
//            is Result.Error -> {
//                showErrorSnackBar(result.toString())
//            }
//            is Result.Failures<*> -> {
//                showErrorSnackBar(result.toString())
//            }
//        }
//    }

    private fun showErrorSnackBar(error: String) {
        (activity as BaseActivity<*>).showErrorSnackBar(error)
    }
}
