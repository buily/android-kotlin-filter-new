package com.buily.filternew.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buily.filternew.databinding.ItemNewsBinding
import com.buily.filternew.model.News

class NewsAdapter(private var newsList: List<News>) :
    RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

    var onItemClick: ((News) -> Unit)? = null

    fun setData(newsData: List<News>) {
        newsList = newsData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder.binding.item = newsList[position]

        holder.binding.constrainRoot.setOnClickListener {
            onItemClick?.invoke(newsList[position])
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class NewsHolder(itemView: ItemNewsBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding = itemView
    }


}
