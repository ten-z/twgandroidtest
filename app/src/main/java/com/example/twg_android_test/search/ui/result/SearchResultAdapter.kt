package com.example.twg_android_test.search.ui.result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twg_android_test.R
import com.example.twg_android_test.search.data.Product

class SearchResultAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ITEM = 1
        const val TYPE_FOOTER = 2

        const val LOADING = 1
        const val LOADING_COMPLETE = 2
        const val LOADING_END = 3
    }

    private var data: MutableList<Product> = mutableListOf()
    private var currentLoadState = LOADING_COMPLETE

    var onItemClick: ((Product) -> Unit)? = null

    fun submitData(newData: List<Product>) {
        data = newData.toMutableList()
        notifyDataSetChanged()
    }

    fun setLoadState(state: Int) {
        currentLoadState = state
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int =
        if (position == data.size) TYPE_FOOTER else TYPE_ITEM

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == TYPE_ITEM) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product, parent, false)
            SearchResultViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_refresh_footer, parent, false)
            FooterViewHolder(view)
        }

    override fun getItemCount(): Int = data.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchResultViewHolder -> {
                if (position < data.size) {
                    holder.bind(data[position])

                    holder.itemView.setOnClickListener {
                        onItemClick?.invoke(data[position])
                    }
                }
            }

            is FooterViewHolder -> {
                when (currentLoadState) {
                    LOADING -> {
                        holder.progressBar.visibility = View.VISIBLE
                        holder.loadingText.visibility = View.VISIBLE
                        holder.endLayout.visibility = View.GONE
                    }

                    LOADING_COMPLETE -> {
                        holder.progressBar.visibility = View.INVISIBLE
                        holder.loadingText.visibility = View.INVISIBLE
                        holder.endLayout.visibility = View.GONE
                    }

                    LOADING_END -> {
                        holder.progressBar.visibility = View.GONE
                        holder.loadingText.visibility = View.GONE
                        holder.endLayout.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productNameTextView: TextView = itemView.findViewById(R.id.tv_product_name)
        private val productImageView: ImageView = itemView.findViewById(R.id.iv_product)
        private val priceTextView: TextView = itemView.findViewById(R.id.tv_price)

        fun bind(product: Product) {
            productNameTextView.text = product.productName

            priceTextView.text = "$${product.price}"

            Glide.with(itemView.context)
                .load(product.productImageUrl)
                .into(productImageView)
        }
    }

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.pb_loading)
        val loadingText: TextView = itemView.findViewById(R.id.tv_loading)
        val endLayout: View = itemView.findViewById(R.id.ll_end)
    }
}