package com.seugi.start

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.oauth.OauthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val oauthRepository: OauthRepository
): ViewModel() {

    fun googleLogin(code: String){
        viewModelScope.launch {
            oauthRepository.authenticate(code = code).collect{
                when(it){
                    is Result.Success -> {
                        Log.d("TAG", "성공: ${it.data}")
                    }
                    is Result.Error -> {
                        it.throwable.printStackTrace()
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }


}