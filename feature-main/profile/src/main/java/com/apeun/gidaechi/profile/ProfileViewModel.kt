package com.apeun.gidaechi.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.common.model.Result
import com.apeun.gidaechi.data.profile.ProfileRepository
import com.apeun.gidaechi.profile.model.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
): ViewModel() {

    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    fun load() = viewModelScope.launch {
        profileRepository.getProfile("664bdd0b9dfce726abd30462").collect { result ->
            when(result) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            profileInfo = result.data
                        )
                    }
                }
                is Result.Loading -> {

                }
                is Result.Error -> {
                    result.throwable.printStackTrace()
                }
            }
        }
    }
}