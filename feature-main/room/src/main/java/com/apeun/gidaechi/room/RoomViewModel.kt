package com.apeun.gidaechi.room

import androidx.lifecycle.ViewModel
import com.apeun.gidaechi.room.model.RoomUiState
import com.apeun.gidaechi.room.model.TestRoomItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class RoomViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(RoomUiState())
    val state = _state.asStateFlow()

    fun loadChats() {
        _state.value = _state.value.copy(
            chatItems = listOf(
                TestRoomItem(
                    0,
                    "노영재너캡숑짱ㅋ",
                    null,
                    "정말 좋습니다",
                    "12:39",
                    if (Random.nextBoolean()) Random.nextInt() else null,
                    4
                ),
                TestRoomItem(
                    0,
                    "노영재너캡숑짱ㅋ",
                    null,
                    "정말 좋습니다",
                    "12:39",
                    if (Random.nextBoolean()) Random.nextInt() else null,
                    4
                ),

                TestRoomItem(
                    0,
                    "노영재너캡숑짱ㅋ",
                    null,
                    "정말 좋습니다",
                    "12:39",
                    if (Random.nextBoolean()) Random.nextInt() else null,
                    4
                ),

                TestRoomItem(
                    0,
                    "노영재너캡숑짱ㅋ",
                    null,
                    "정말 좋습니다",
                    "12:39",
                    if (Random.nextBoolean()) Random.nextInt() else null,
                    4
                ),

                TestRoomItem(
                    0,
                    "노영재너캡숑짱ㅋ",
                    null,
                    "정말 좋습니다",
                    "12:39",
                    if (Random.nextBoolean()) Random.nextInt() else null,
                    4
                ),

                TestRoomItem(
                    0,
                    "노영재너캡숑짱ㅋ",
                    null,
                    "정말 좋습니다",
                    "12:39",
                    if (Random.nextBoolean()) Random.nextInt() else null,
                    4
                ),
                TestRoomItem(
                    0,
                    "노영재너캡숑짱ㅋ",
                    null,
                    "정말 좋습니다",
                    "12:39",
                    if (Random.nextBoolean()) Random.nextInt() else null,
                    4
                ),
                TestRoomItem(
                    0,
                    "노영재너캡숑짱ㅋ",
                    null,
                    "정말 좋습니다",
                    "12:39",
                    if (Random.nextBoolean()) Random.nextInt() else null,
                    4
                ),

                TestRoomItem(
                    0,
                    "노영재너캡숑짱ㅋ",
                    null,
                    "정말 좋습니다",
                    "12:39",
                    if (Random.nextBoolean()) Random.nextInt() else null,
                    4
                ),

                TestRoomItem(
                    0,
                    "노영재너캡숑짱ㅋ",
                    null,
                    "정말 좋습니다",
                    "12:39",
                    if (Random.nextBoolean()) Random.nextInt() else null,
                    4
                ),

                TestRoomItem(
                    0,
                    "노영재너캡숑짱ㅋ",
                    null,
                    "정말 좋습니다",
                    "12:39",
                    if (Random.nextBoolean()) Random.nextInt() else null,
                    4
                ),

                TestRoomItem(
                    0,
                    "노영재너캡숑짱ㅋ",
                    null,
                    "정말 좋습니다",
                    "12:39",
                    if (Random.nextBoolean()) Random.nextInt() else null,
                    4
                ),

                ).toImmutableList(),
        )
    }
}
