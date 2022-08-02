package com.example.dagger2.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.Observer
import com.example.dagger2.R
import com.example.dagger2.base.BaseFragment
import com.example.dagger2.data.model.User
import com.example.dagger2.databinding.FragmentDetailBinding
import com.example.dagger2.di.injectViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import com.example.dagger2.common.Result

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    private lateinit var idUser: String

    private lateinit var user: User
    private var isFavorite: Boolean = false

    private lateinit var checkBox: CheckBox

    override fun getViewModelClass(): Class<DetailViewModel> {
        return DetailViewModel::class.java
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_detail
    }

    override fun injectViewModel() {
        mViewModel = injectViewModel(viewModelFactory)
    }

    companion object {
        const val ID_USER = "id_user"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsToolbar.setNavigationOnClickListener {
            dismiss()
        }

        detailsToolbar.inflateMenu(R.menu.menu_details)

        val starMenuItem = detailsToolbar.menu.findItem(R.id.action_favorite)
        checkBox = starMenuItem.actionView as CheckBox


        arguments?.getString(ID_USER)?.let { idUser = it }
        viewModel.observeUserById(idUser)

        checkBox.setOnClickListener {
            user.isFavorite = !user.isFavorite
            viewModel.updateFavorite(user)
        }

        observeUser()
        observeFavorite()
    }

    private fun observeUser() {
        viewModel.user.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.SUCCESS -> {
                    if (it.data != null) user = it.data
                    displayUser()
                    binding.progressbar.visibility = View.GONE
                    binding.content.visibility = View.VISIBLE
                }

                Result.Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.content.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    it.message?.let { it1 -> snackBar(it1) }
                    binding.progressbar.visibility = View.GONE
                    binding.content.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun observeFavorite() {
        viewModel.favorite.observe(viewLifecycleOwner) {
            when (it.status) {
                Result.Status.SUCCESS -> {
                    binding.progressbar.visibility = View.GONE
                    isFavorite = it.data ?: false

                    if (isFavorite) {
                        snackBar("User has been added to favorite")
                        checkBox.isChecked = isFavorite
                    } else {
                        snackBar("User has been deleted from favorite")
                        checkBox.isChecked = user.isFavorite
                    }
                }

                Result.Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE
                }

                Result.Status.ERROR -> {
                    it.message?.let { it1 -> snackBar(it1) }
                    binding.progressbar.visibility = View.GONE
                }
            }
        }
    }

    private fun displayUser() {
        binding.user = user
        binding.image = user.imageUrl
        checkBox.isChecked = user.isFavorite
    }
}