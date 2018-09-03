package com.andela.wikipedia.manager

import com.andela.wikipedia.Providers.ArticleDataProvider
import com.andela.wikipedia.models.WikiPage
import com.andela.wikipedia.models.WikiResult
import com.andela.wikipedia.repositories.FavoritesRespository
import com.andela.wikipedia.repositories.HistoryRespository

class WikiManager(private val provider: ArticleDataProvider,
                  private val favoritesRespository: FavoritesRespository,
                  private val historyRespository: HistoryRespository) {

    private var favoritesCache: ArrayList<WikiPage>? = null
    private var historyCache: ArrayList<WikiPage>? = null

    fun search(term: String, skip: Int, take: Int, handler: (response: WikiResult) -> Unit?) {
        return provider.search(term, skip, take, handler)
    }

    fun getRandom(take: Int, handler: (response: WikiResult) -> Unit?) {
        return provider.getRandom(take, handler)
    }

    fun getHistory(): ArrayList<WikiPage>? {
        if (historyCache ==  null)
            historyCache = historyRespository.getAllHistory()
        return historyCache
    }

    fun getFavorites(): ArrayList<WikiPage>? {
        if (favoritesCache == null )
            favoritesCache = favoritesRespository.getAllFavorites()
        return favoritesCache
    }

    fun addFavorites(page: WikiPage) {
        favoritesCache?.add(page)
        favoritesRespository.addFavorites(page)
    }

    fun removeFavorites(pageId: Int) {
        favoritesRespository.removeFavorites(pageId)
        favoritesCache = favoritesCache!!.filter { it.pageId != pageId } as ArrayList<WikiPage>
    }

    fun getIsFavorite(pageId: Int) : Boolean {
        return favoritesRespository.isArticleFavorite(pageId)
    }

    fun addHistory(page: WikiPage) {
        historyCache?.add(page)
        historyRespository.addHistory(page)
    }

    fun clearHistory() {
        historyCache?.clear()
        val allHistory = historyRespository.getAllHistory()
        allHistory?.forEach { historyRespository.removeHistoryById(it.pageId!!) }
    }



}