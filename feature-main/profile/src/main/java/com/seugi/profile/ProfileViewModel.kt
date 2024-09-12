package com.seugi.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.data.core.model.ProfileModel
import com.seugi.data.profile.ProfileRepository
import com.seugi.profile.model.ProfileSideEffect
import com.seugi.profile.model.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _sideEffect = Channel<ProfileSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun updateState(profile: ProfileModel) = viewModelScope.launch(dispatcher) {

        with(profile) {
            profileRepository.patchProfile(
                workspaceId = workspaceId,
                nick = nick,
                status = status,
                spot = spot,
                belong = belong,
                phone = phone,
                wire = wire,
                location = location
            ).collect {
                when (it) {
                    is Result.Success -> {

                    }
                    is Result.Loading -> {

                    }
                    is Result.Error -> {
                        it.throwable.printStackTrace()
                        _sideEffect.send(ProfileSideEffect.FailedChange(it.throwable))
                    }
                }
            }
        }
    }
}
