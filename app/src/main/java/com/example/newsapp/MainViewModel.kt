package com.example.newsapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.repository.Repository
import com.example.database.NewsDataBase
import com.example.model.Source
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = NewsDataBase.getDatabase(application.applicationContext)
    private val newsRepository = Repository(database)

    private var _response = MutableLiveData<List<Source>>()
    val response:LiveData<List<Source>> get() = _response

    init {
        getAllNews()
    }
    private fun getAllNews(){
        viewModelScope.launch {
            newsRepository.addAllNews()
            Log.i("LOG","LoadAllNews")
        }
    }
    fun getNewsFromDataBase(){
        viewModelScope.launch {
            _response.value = newsRepository.getAllNews()
            Log.i("LOG","GetNewsFromDataBase")
        }
    }
    fun getCategoryNewsFromDataBase(category:String){
        viewModelScope.launch {
            _response.value = newsRepository.getCategoryNews(category)
            Log.i("Log","GetCategoryNews")
        }
    }
    fun getStarredNewsFromDataBase(){
        viewModelScope.launch {
            _response.value = newsRepository.getAllStaredNews()
            Log.i("Log","GetStarredNews")
        }
    }
    fun setNewsStarred(id:String, isStarred:Boolean) {
        viewModelScope.launch {
            newsRepository.setNewsStarred(id,isStarred)
        }
    }
}