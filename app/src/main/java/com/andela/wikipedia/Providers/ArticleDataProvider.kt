package com.andela.wikipedia.Providers

import android.util.Log
import com.andela.wikipedia.models.Urls
import com.andela.wikipedia.models.WikiResult
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import java.io.Reader

class ArticleDataProvider {

    init {
        FuelManager.instance.baseHeaders = mapOf("User-Agent" to "Plurasight Wikipedia")
    }

    fun search(term: String, skip: Int, take: Int, responseHandler: (result : WikiResult) -> Unit?) {
        Urls.getSearchUrl(term, skip, take).httpGet()
                .responseObject(WikipediaDataDeserializer()) {_, response, result ->

                    if(response.httpStatusCode != 200) {
                        throw Exception("Unable to get articles")
                    }
                    val(data, _) = result
                    responseHandler.invoke(data as WikiResult)
        }
    }

//    private val ch: (Int, (result: WikiResult) -> Unit?) -> Unit = {a, b ->
//        Urls.getRandomUrl(a).httpGet()
//                .responseObject(WikipediaDataDeserializer()) {_, response, result ->
//                    if(response.httpStatusCode != 200) {
//
//                        throw Exception("Unable to get articles")
//                    }
//                    val(data, _) = result
//                    data?.let { b(it) }
//                }
//    }
//
//
//    fun getRandom(take: Int, responseHandler: (result: WikiResult) -> Unit?) {
//        ch(take, responseHandler)
//    }
    fun getRandom(take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        Urls.getRandomUrl(take).httpGet()
                .responseObject(WikipediaDataDeserializer()) {_, response, result ->
                    if(response.httpStatusCode != 200) {

                        throw Exception("Unable to get articles")
                    }
                    val(data, _) = result
//                    data?.let { responseHandler(it) }
                    if (data != null) {

                        responseHandler.invoke(data as WikiResult)
                    }
//                        responseHandler(data)
//                    } else {
//                        Log.e(">>>", "" + result.component2())
//                    }
                }
    }

    class WikipediaDataDeserializer : ResponseDeserializable<WikiResult> {
        override fun deserialize(reader: Reader) = Gson().fromJson(reader, WikiResult::class.java)
//        }
    }
}