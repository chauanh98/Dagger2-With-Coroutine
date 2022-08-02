package com.example.dagger2.ui.user

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dagger2.R
import com.example.dagger2.base.BaseActivity
import com.example.dagger2.data.model.User
import com.example.dagger2.databinding.ActivityUserBinding
import com.example.dagger2.di.injectViewModel
import com.google.android.material.snackbar.Snackbar
import com.example.dagger2.common.Result
import com.example.dagger2.ui.detail.DetailFragment
import javax.inject.Inject

class UserActivity : BaseActivity<ActivityUserBinding, UserViewModel>() {

    private lateinit var adapter: UserAdapter

    @Inject
    lateinit var detailFragment: DetailFragment

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    override fun getViewModelClass(): Class<UserViewModel> {
        return UserViewModel::class.java
    }

    override fun initView() {
        adapter = UserAdapter(this::onItemClicked)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL,
                false
            )

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, RecyclerView.VERTICAL)
        )

        binding.adapter = adapter
        observeUi()
    }

    private fun observeUi() {
        viewModel.user.observe(this) { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let {
                        adapter.setUsersList(it)
                    }
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.progressbar.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    result.message?.let {
                        Snackbar.make(
                            binding.recyclerView,
                            it,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.progressbar.visibility = View.GONE
                }

                Result.Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onItemClicked(user: User) {
        val args = Bundle().apply {
            putString(DetailFragment.ID_USER, user.id)
        }
        detailFragment.arguments = args
        detailFragment.show(supportFragmentManager, "DetailFragment")
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_user
    }
}