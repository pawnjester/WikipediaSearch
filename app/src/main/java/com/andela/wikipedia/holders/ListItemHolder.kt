package com.andela.wikipedia.holders

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.andela.wikipedia.R
import com.andela.wikipedia.activities.ArticleDetailActivity
import com.andela.wikipedia.models.WikiPage
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.article_card_item.view.*

class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val articleImageView: ImageView = itemView.findViewById(R.id.result_icon)
    private val titleTextview: TextView = itemView.findViewById(R.id.result_title)

    private var currentPage: WikiPage? = null

    init {
        itemView.setOnClickListener { view: View ->
            var detailPageIntent = Intent(itemView.context, ArticleDetailActivity::class.java)
            var pageJson = Gson().toJson(currentPage)
            detailPageIntent.putExtra("page", pageJson)
            itemView.context.startActivity(detailPageIntent)
        }
    }

    fun updateWithPage(page: WikiPage) {
        currentPage = page

        titleTextview.text = page.title

        if (page.thumbnail != null) {
            Picasso.get().load(page.thumbnail!!.source).into(articleImageView)
        }
    }
}