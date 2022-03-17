package com.example.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.example.repository.Repository
import com.example.database.NewsDataBase
import com.example.model.Source
import kotlinx.coroutines.launch

open class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = NewsDataBase.getDatabase(application.applicationContext)
    private val newsRepository = Repository(database)

    private var _response = MutableLiveData<List<Source>>()
    val response:LiveData<List<Source>> get() = _response

    private var _logged = MutableLiveData<Boolean>()
    val logged:LiveData<Boolean> get() = _logged

    init {
        _logged.value = false
        getAllNews()
    }

    private fun getAllNews(){
        viewModelScope.launch {
            newsRepository.addAllNewsToDatabase()
        }
    }
    fun getNewsFromDataBase(){
        viewModelScope.launch {
            _response.value = newsRepository.showAllNewsFromDatabase()
        }
    }
    fun getCategoryNewsFromDataBase(category:String){
        viewModelScope.launch {
            _response.value = newsRepository.showNewsSortedByCategoryFromDatabase(category)
        }
    }
    fun getStarredNewsFromDataBase(){
        viewModelScope.launch {
            _response.value = newsRepository.showAllFavoriteNews()
        }
    }
    fun setNewsStarred(id:String, isStarred:Boolean) {
        viewModelScope.launch {
            newsRepository.setArticleIsFavorite(id,isStarred)
        }
    }
    fun loggedByGoogle(){
        _logged.value = true
    }
}