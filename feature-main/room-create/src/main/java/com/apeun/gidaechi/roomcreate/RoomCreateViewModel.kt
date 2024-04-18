package com.apeun.gidaechi.roomcreate

import androidx.lifecycle.ViewModel
import com.apeun.gidaechi.roomcreate.model.RoomCreateUiState
import com.apeun.gidaechi.roomcreate.model.TestMemberItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class RoomCreateViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(RoomCreateUiState())
    val state = _state.asStateFlow()

    fun loadUser() {
        val users = mutableListOf<TestMemberItem>()
        for (i in 1..30) {
            users.add(
                TestMemberItem(
                    id = i,
                    name = "노영재 ${i}세",
                ),
            )
        }
        _state.value = _state.value.copy(
            userItem = users.toImmutableList(),
        )
    }

    fun updateChecked(userId: Int) {
        _state.value = _state.value.copy(
            userItem = _state.value.userItem.map {
                if (it.id == userId) {
                    it.copy(
                        checked = it.checked.not(),
                    )
                } else {
                    it
                }
            }.toImmutableList(),
        )
    }
}
