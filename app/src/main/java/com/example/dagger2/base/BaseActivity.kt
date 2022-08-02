package com.example.dagger2.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dagger2.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding, V : ViewModel> : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mViewDataBinding: B
    protected lateinit var mViewModel: V

    val binding: B get() = mViewDataBinding
    val viewModel: V get() = mViewModel

    abstract fun injectViewModel()
    abstract fun getViewModelClass(): Class<V>
    abstract fun initView()

    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Dagger2)
        super.onCreate(savedInstanceState)
        injectViewModel()
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutResourceId())
        initView()
    }

}