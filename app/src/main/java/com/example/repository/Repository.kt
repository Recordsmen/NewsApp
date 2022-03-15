package com.example.repository
import android.util.Log
import com.example.ApiService.NewsApi
import com.example.Utils.asDomainModel
import com.example.Utils.parseNewsJsonResult
import com.example.database.NewsDataBase
import com.example.model.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject

class Repository(private val dataBase: NewsDataBase) {

    suspend fun addAllNews(){
        withContext(Dispatchers.IO) {
            val responseBody:ResponseBody = NewsApi.retrofitService.getNews()
            val newsList = parseNewsJsonResult(JSONObject(responseBody.string()))
            dataBase.newsDataBaseDao.insertAll(*newsList.asDomainModel())

        }
    }
    suspend fun getAllNews():List<Source>{
        lateinit var result:List<Source>
        withContext(Dispatchers.IO){
            result = dataBase.newsDataBaseDao.getAllNews()

        }
        return result
    }
    suspend fun getCategoryNews(category:String):List<Source>{
        lateinit var result:List<Source>
        withContext(Dispatchers.IO){
            result = dataBase.newsDataBaseDao.getCategoryNews(category)
            Log.i("LOG",result.size.toString())
        }
        return result
    }

    suspend fun getAllStaredNews():List<Source>{
        lateinit var result:List<Source>
        withContext(Dispatchers.IO){
            result = dataBase.newsDataBaseDao.getAllStarredNews()
            Log.i("LOG",result.size.toString())
        }
        return result
    }

    suspend fun updateUsers(){
        withContext(Dispatchers.IO){
            dataBase.newsDataBaseDao.updateUsers()
        }
    }
}