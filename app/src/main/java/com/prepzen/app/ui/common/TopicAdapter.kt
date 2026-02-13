package com.prepzen.app.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prepzen.app.databinding.ItemTopicBinding
import com.prepzen.app.domain.Topic

class TopicAdapter(
    private val onTopicClicked: (Topic) -> Unit
) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {

    private val items = mutableListOf<Topic>()
    private val bookmarks = mutableSetOf<String>()

    fun submitList(newItems: List<Topic>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun submitBookmarks(ids: Set<String>) {
        bookmarks.clear()
        bookmarks.addAll(ids)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class TopicViewHolder(private val binding: ItemTopicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topic) {
            binding.topicTitle.text = topic.title
            binding.topicSubtitle.text = topic.subCategory
            binding.bookmarkBadge.alpha = if (bookmarks.contains(topic.id)) 1f else 0f
            binding.root.setOnClickListener { onTopicClicked(topic) }
        }
    }
}
