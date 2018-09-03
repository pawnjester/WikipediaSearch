package com.andela.wikipedia

import android.app.Application
import com.andela.wikipedia.Providers.ArticleDataProvider
import com.andela.wikipedia.manager.WikiManager
import com.andela.wikipedia.repositories.ArticleDatabaseOpenHelper
import com.andela.wikipedia.repositories.FavoritesRespository
import com.andela.wikipedia.repositories.HistoryRespository

class WikiApplication: Application() {
    private var dbHelper: ArticleDatabaseOpenHelper? = null
    private var favoritesRespository: FavoritesRespository? = null
    private var historyRespository: HistoryRespository? = null
    private var wikiProvider: ArticleDataProvider? = null
    var wikiManager: WikiManager? = null
        private set

    override fun onCreate() {
        super.onCreate()
        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favoritesRespository = FavoritesRespository(dbHelper!!)
        historyRespository = HistoryRespository(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!!, favoritesRespository!!, historyRespository!!)
    }

}