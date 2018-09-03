package com.andela.wikipedia.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.andela.wikipedia.R
import com.andela.wikipedia.holders.CardHolder
import com.andela.wikipedia.holders.ListItemHolder
import com.andela.wikipedia.models.WikiPage

class ArticleListItemAdapter : RecyclerView.Adapter<ListItemHolder>() {


    var currentResults: ArrayList<WikiPage> = ArrayList<WikiPage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        var cardItem = LayoutInflater.from(parent?.context)
                .inflate(R.layout.article_list_item, parent , false)
        return ListItemHolder(cardItem)
    }

    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
        var page = currentResults[position]
        holder.updateWithPage(page)
    }
}