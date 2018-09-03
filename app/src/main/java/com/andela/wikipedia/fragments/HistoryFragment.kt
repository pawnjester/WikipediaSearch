package com.andela.wikipedia.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*

import com.andela.wikipedia.R
import com.andela.wikipedia.WikiApplication
import com.andela.wikipedia.adapters.ArticleListItemAdapter
import com.andela.wikipedia.manager.WikiManager
import com.andela.wikipedia.models.WikiPage
import kotlinx.android.synthetic.main.fragment_history.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HistoryFragment : Fragment() {

    var historyArticleRecycler: RecyclerView? = null
    private var wikiManager: WikiManager? = null
    private var adapter = ArticleListItemAdapter()

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_history, container, false)

        historyArticleRecycler = view?.findViewById(R.id.history_article_recycler)
        historyArticleRecycler?.layoutManager = LinearLayoutManager(context)
        historyArticleRecycler?.adapter = adapter
        // Inflate the layout for this fragment
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.history_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        try {
            if (item!!.itemId == R.id.action_clear_history) {
                activity!!.alert("Are you sure you want to clear the history?", "Confirm") {
                    yesButton {
                        adapter.currentResults.clear()
                        doAsync {
                            wikiManager?.clearHistory()
                        }
                        activity?.runOnUiThread { adapter.notifyDataSetChanged() }
                    }
                    noButton {
                        //do nothing
                    }
                }.show()
            }
        } catch (ex: Exception) {

        }
        return true
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }

    override fun onResume() {
        super.onResume()
        doAsync {
            val historyArticles = wikiManager!!.getHistory()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(historyArticles as ArrayList<WikiPage>)
            activity?.runOnUiThread { adapter.notifyDataSetChanged() }
        }
    }


}
