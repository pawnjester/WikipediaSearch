package com.andela.wikipedia.fragments


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andela.wikipedia.Providers.ArticleDataProvider

import com.andela.wikipedia.R
import com.andela.wikipedia.WikiApplication
import com.andela.wikipedia.activities.SearchActivity
import com.andela.wikipedia.adapters.ArticleCardAdapter
import com.andela.wikipedia.manager.WikiManager
import com.andela.wikipedia.models.WikiResult
import kotlinx.android.synthetic.main.fragment_explore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ExploreFragment : Fragment() {

//    private val articleProvider: ArticleDataProvider = ArticleDataProvider()
    private var wikiManager: WikiManager? = null
    var adapter: ArticleCardAdapter = ArticleCardAdapter()

    var searchCardView: CardView? = null
    var exploreArticleRecycler: RecyclerView? = null

    var refresher: SwipeRefreshLayout? = null

    override fun onAttach(context: Context?) {
        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_explore, container, false)

        searchCardView = view?.findViewById<CardView>(R.id.search_card_view)
        exploreArticleRecycler = view?.findViewById<RecyclerView>(R.id.explore_article_recycler)
        refresher = view.findViewById<SwipeRefreshLayout>(R.id.refresher)

        searchCardView!!.setOnClickListener {
            val searchIntent = Intent(context, SearchActivity::class.java)
            context!!.startActivity(searchIntent)
        }

        exploreArticleRecycler!!.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        exploreArticleRecycler!!.adapter = adapter

        refresher?.setOnRefreshListener {
            getRandomArticles()
        }

        getRandomArticles()
        return view

    }

    private fun getRandomArticles() {
        refresher?.isRefreshing = true
        try {
            wikiManager?.getRandom(15, { result: WikiResult ->
                adapter.currentResults.clear()
                adapter.currentResults.addAll(result.query!!.pages)
                activity?.runOnUiThread {
                    adapter.notifyDataSetChanged()
                    refresher?.isRefreshing = false
                }
                null
            })
        } catch (ex: Exception) {
            //show alert
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(ex.message).setTitle("oops!!")
            val dialog = builder.create()
            dialog.show()
        }
    }


}
