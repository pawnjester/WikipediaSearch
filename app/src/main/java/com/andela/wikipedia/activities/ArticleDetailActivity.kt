package com.andela.wikipedia.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.andela.wikipedia.R
import com.andela.wikipedia.WikiApplication
import com.andela.wikipedia.manager.WikiManager
import com.andela.wikipedia.models.WikiPage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_article_detail.*
import org.jetbrains.anko.toast

class ArticleDetailActivity : AppCompatActivity() {

    private var currentPage: WikiPage? = null
    private var wikiManager: WikiManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        wikiManager = (applicationContext as WikiApplication).wikiManager
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = currentPage!!.title

        val wikiPageJson = intent.getStringExtra("page")
        currentPage = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)

        article_detail_webview?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        article_detail_webview.loadUrl(currentPage!!.fullUrl)

        wikiManager?.addFavorites(currentPage!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        } else if (item!!.itemId == R.id.action_favorite) {
            try {
                if (wikiManager!!.getIsFavorite(currentPage!!.pageId!!)) {
                    wikiManager!!.removeFavorites(currentPage!!.pageId!!)
                    toast("Article removed from Favorites")
                } else {
                    wikiManager!!.addFavorites(currentPage!!)
                    toast("Article added to favorites")
                }

            } catch (ex: Exception) {
                toast("Unable to update update this article")
            }
        }
        return true
    }

}