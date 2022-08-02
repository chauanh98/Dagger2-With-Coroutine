package com.example.dagger2.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dagger2.R
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerDialogFragment
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, V : ViewModel> : DaggerDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mViewDataBinding: B
    protected lateinit var mViewModel: V

    val binding: B get() = mViewDataBinding
    val viewModel: V get() = mViewModel

    abstract fun injectViewModel()
    abstract fun getViewModelClass(): Class<V>

    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.Theme_Dagger2)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectViewModel()
        mViewDataBinding =
            DataBindingUtil.inflate(inflater, getLayoutResourceId(), container, false)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
        return mViewDataBinding.root
    }

    protected fun snackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}