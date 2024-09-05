package com.seugi.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.core.model.ProfileModel
import com.seugi.data.profile.ProfileRepository
import com.seugi.profile.model.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    fun load(profile: ProfileModel) = viewModelScope.launch {
        _state.update {
            it.copy(
                profileInfo = profile
            )
        }
    }

    fun updateState(target: String, text: String) {
        val info = _state.value.profileInfo
        with(info) {
            _state.update {
                it.copy(
                    profileInfo = info.copy(
                        status = if (target == "status") text else status,
                        member = member,
                        workspaceId = workspaceId,
                        nick = if (target == "nick") text else nick,
                        spot = if (target == "spot") text else spot,
                        belong = if (target == "belong") text else belong,
                        phone = if (target == "phone") text else phone,
                        wire = if (target == "wire") text else wire,
                        location = location,
                    ),
                )
            }
        }
    }
}
