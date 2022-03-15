package com.example.newsapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.repository.Repository
import com.example.database.NewsDataBase
import com.example.model.Source
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = NewsDataBase.getDatabase(application.applicationContext)
    private val newsRepository = Repository(database)

    private val _navigateToAllCategories = MutableLiveData<Boolean>()
    val navigateToAllCategories:LiveData<Boolean> get() = _navigateToAllCategories


    private var _response = MutableLiveData<List<Source>>()
    val response:LiveData<List<Source>> get() = _response

    init {
        getAllNews()
    }

    fun getAllNews(){
        viewModelScope.launch {
            newsRepository.addAllNews()
            Log.i("LOG","Success")
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
            Log.i("Log","GetCategoryNews")
        }
    }

    fun updateUsers(){
        viewModelScope.launch {
            newsRepository.updateUsers()
        }
    }
}