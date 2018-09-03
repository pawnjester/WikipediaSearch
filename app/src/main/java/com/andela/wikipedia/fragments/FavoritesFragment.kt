package com.andela.wikipedia.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.andela.wikipedia.R
import com.andela.wikipedia.WikiApplication
import com.andela.wikipedia.adapters.ArticleCardAdapter
import com.andela.wikipedia.adapters.ArticleListItemAdapter
import com.andela.wikipedia.manager.WikiManager
import com.andela.wikipedia.models.WikiPage
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.jetbrains.anko.doAsync

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoritesFragment : Fragment() {

    private var favoritesArticleRecycler: RecyclerView? = null
    private var wikiManager: WikiManager? = null
    private val adapter: ArticleCardAdapter = ArticleCardAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_favorites, container, false)
        favoritesArticleRecycler = view?.findViewById<RecyclerView>(R.id.favorites_article_recycler)

        favoritesArticleRecycler?.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        favoritesArticleRecycler?.adapter = adapter
        // Inflate the layout for this fragment
        return view
    }

    override fun onResume() {
        super.onResume()
        doAsync {
            val favoritesArticles = wikiManager!!.getFavorites()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(favoritesArticles as ArrayList<WikiPage>)
            activity?.runOnUiThread { adapter.notifyDataSetChanged() }
        }
    }


}
