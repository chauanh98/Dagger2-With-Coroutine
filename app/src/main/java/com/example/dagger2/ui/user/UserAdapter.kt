package com.example.dagger2.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dagger2.R
import com.example.dagger2.data.model.User
import com.example.dagger2.databinding.UserItemBinding

class UserAdapter(val listener: Listener) : RecyclerView.Adapter<UserAdapter.Holder>() {

    private val list = arrayListOf<User>()

    internal fun setUsersList(list: List<User>) {
        if (this.list.isNotEmpty()) {
            this.list.clear()
        }
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<UserItemBinding>(inflater, R.layout.user_item, parent, false)

        return Holder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

    inner class Holder(var binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) = binding.apply {
            user = item
            image = item.imageUrl
            val icon = if (item.isFavorite) {
                R.drawable.ic_baseline_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
            imageViewFavorite.setImageResource(icon)
            mainLayout.setOnClickListener {
                listener(item)
            }
        }
    }
}

typealias Listener = (User) -> Unit