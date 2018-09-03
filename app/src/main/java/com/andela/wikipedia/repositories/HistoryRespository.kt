package com.andela.wikipedia.repositories

import com.andela.wikipedia.models.WikiPage
import com.andela.wikipedia.models.WikiThumbnail
import com.google.gson.Gson
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select

class HistoryRespository (val databaseHelper: ArticleDatabaseOpenHelper){
    private val TABLE_NAME: String = "History"

    fun addHistory(page: WikiPage) {
        databaseHelper.use {
            insert(TABLE_NAME,
                    "id" to page.pageId,
                    "title" to page.title,
                    "url" to page.fullUrl,
                    "thumbnailJson" to Gson().toJson(page.thumbnail))
        }
    }

    fun removeHistoryById(pageId: Int) {
        databaseHelper.use {
            delete(TABLE_NAME,
                    "id ={pageId}",
                    "pageId" to pageId)
        }
    }


    fun getAllHistory() : ArrayList<WikiPage> {
        var pages = ArrayList<WikiPage>()

        var articleRowParser = rowParser { id: Int, title: String, url: String, thunmbnailJson: String ->
            val page = WikiPage()
            page.title = title
            page.pageId = id
            page.fullUrl = url
            page.thumbnail = Gson().fromJson(thunmbnailJson, WikiThumbnail::class.java)

            pages.add(page)
        }

        databaseHelper.use {
            select(TABLE_NAME).parseList(articleRowParser)
        }
        return pages
    }
}
