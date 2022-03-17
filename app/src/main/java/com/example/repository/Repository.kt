package com.example.repository
import com.example.apiService.NewsApi
import com.example.utils.asDomainModel
import com.example.utils.parseNewsJsonResult
import com.example.database.NewsDataBase
import com.example.model.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject

class Repository(private val dataBase: NewsDataBase) {

    suspend fun addAllNewsToDatabase(){
        withContext(Dispatchers.IO) {
            val responseBody:ResponseBody = NewsApi.retrofitService.getNewsFromApi()
            val newsList = parseNewsJsonResult(JSONObject(responseBody.string()))
            dataBase.newsDataBaseDao.insertAll(*newsList.asDomainModel())
        }
    }
    suspend fun showAllNewsFromDatabase():List<Source>{
        lateinit var result:List<Source>
        withContext(Dispatchers.IO){
            result = dataBase.newsDataBaseDao.showAllNews()
        }
        return result
    }
    suspend fun showNewsSortedByCategoryFromDatabase(category:String):List<Source>{
        lateinit var result:List<Source>
        withContext(Dispatchers.IO){
            result = dataBase.newsDataBaseDao.showNewsSortedByCategory(category)
        }
        return result
    }
    suspend fun showAllFavoriteNews():List<Source>{
        lateinit var result:List<Source>
        withContext(Dispatchers.IO){
            result = dataBase.newsDataBaseDao.getAllFavoriteNews()
        }
        return result
    }
    suspend fun setArticleIsFavorite(NewsID: String, isStarred: Boolean) {
        withContext(Dispatchers.IO) {
            dataBase.newsDataBaseDao.setArticleIsFavorite(NewsID, isStarred)
        }
    }
}