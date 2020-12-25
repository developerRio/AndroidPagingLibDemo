package com.originalstocks.paginglibdemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.originalstocks.paginglibdemo.data.State
import com.originalstocks.paginglibdemo.databinding.ItemListFooterBinding
import kotlinx.android.synthetic.main.item_list_footer.view.*

class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(status: State?) {
        itemView.progress_bar.visibility = if (status == State.LOADING) VISIBLE else INVISIBLE
        itemView.txt_error.visibility = if (status == State.ERROR) VISIBLE else INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
            val binding = ItemListFooterBinding.inflate(LayoutInflater.from(parent.context))
            binding.txtError.setOnClickListener { retry() }
            return ListFooterViewHolder(binding.root)
        }
    }
}