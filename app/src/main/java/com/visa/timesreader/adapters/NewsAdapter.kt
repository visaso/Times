package com.visa.timesreader.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.visa.timesreader.NewsDetailActivity
import com.visa.timesreader.databinding.NewsItemBinding
import com.visa.timesreader.databinding.NewsItemHighlightBinding
import com.visa.timesreader.models.Result
import com.visa.timesreader.utils.DateTimeParser
import com.visa.timesreader.utils.Constants.Companion.NEWS_ITEM
import com.visa.timesreader.utils.Constants.Companion.NEWS_ITEM_HIGHLIGHT
import com.visa.timesreader.utils.ImageFetcher


class NewsAdapter(private val data: List<Result>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        /**
         * Fetch image for article and resize if the element is not highlighted.
         */

        /**
         * Bind data to views. Variables that need no parsing are automatically populated
         * with DataBinding
         */
        fun bind(item: Result) {
            val imageFetcher = ImageFetcher()
            val dateParser = DateTimeParser()
            if (binding is NewsItemBinding) {
                binding.item = item

                val context = binding.root.context
                binding.container.setOnClickListener {
                    val intent = Intent(context, NewsDetailActivity::class.java)
                    intent.putExtra("url", item.url)
                    context.startActivity(intent)
                }

                binding.parsedDate =
                    dateParser.timeBetween(item.published_date, binding)

                try {
                    imageFetcher.fetchCoverImage(false, item.multimedia[0].url, binding.multimedia)
                } catch (e: Exception) {
                    Log.d("Basic item exception", e.message!!)
                }
            } else if (binding is NewsItemHighlightBinding) {
                binding.item = item
                val context = binding.root.context
                binding.container.setOnClickListener {
                    val intent = Intent(context, NewsDetailActivity::class.java)
                    intent.putExtra("url", item.url)
                    context.startActivity(intent)
                }
                binding.parsedCategory = item.section.replaceFirstChar { it.uppercase() }
                binding.parsedDate =
                    dateParser.timeBetween(item.published_date, binding)
                try {
                    imageFetcher.fetchCoverImage(true, item.multimedia[0].url, binding.multimedia)
                } catch (e: Exception) {
                    Log.d("Highlight item exception", e.message!!)
                }
            }
            binding.executePendingBindings()
        }
    }

    /**
     * Inflate layout using ViewDataBinding depending on the viewType
     * viewType 0 = NEWS_ITEM
     * viewType 1 = NEWS_ITEM_HIGHLIGHT
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            NEWS_ITEM -> NewsItemBinding.inflate(inflater, parent, false)
            else -> NewsItemHighlightBinding.inflate(inflater, parent, false)
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news: Result = data[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    /**
     * Highlight every 4th element.
     *
     * Add 1 to avoid first element being highlighted
     * to achieve similar UI as found in assignment
     */
    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % 4 == 0) {
            NEWS_ITEM_HIGHLIGHT
        } else {
            NEWS_ITEM
        }
    }
}