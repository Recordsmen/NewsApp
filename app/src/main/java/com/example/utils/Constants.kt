package com.example.utils

import com.example.database.DataBaseNews
import com.example.model.Source
import org.json.JSONObject

object Constants {

    const val BASE_URL = "https://newsapi.org/v2/top-headlines/"
    const val API_KEY = "sources?apiKey=7d3bbd1879e44d289de679c65458e798"

}

fun parseNewsJsonResult(jsonResult: JSONObject): ArrayList<Source> {
    val sourcesObjectsJson = jsonResult.getJSONArray("sources")

    val sourcesList = ArrayList<Source>()
    for (i in 0 until sourcesObjectsJson.length() ){
        val newsJson = sourcesObjectsJson.getJSONObject(i)

        val id = newsJson.getString("id")
        val category = newsJson.getString("category")
        val description = newsJson.getString("description")
        val country = newsJson.getString("country")
        val language = newsJson.getString("language")
        val name = newsJson.getString("name")
        val url = newsJson.getString("url")

        val news = Source(
            id,
            category, country, description,
            language, name, url, isFavorite = false
        )
        sourcesList.add(news)
    }

    return sourcesList
}

fun ArrayList<Source>.asDomainModel(): Array<DataBaseNews> {
    return map {
        DataBaseNews(
            id = it.id,
            category = it.category,
            country = it.country,
            description = it.description,
            language = it.language,
            name = it.name,
            url = it.url,
            isFavorite = false
        )
    }
        .toTypedArray()
}
