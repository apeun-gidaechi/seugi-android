package com.apeun.gidaechi.chatdatail

import androidx.lifecycle.ViewModel
import com.apeun.gidaechi.chatdatail.model.ChatRoomState
import com.apeun.gidaechi.chatdatail.model.ChatUiState
import com.apeun.gidaechi.chatdatail.model.TestMessageModel
import com.apeun.gidaechi.chatdatail.model.TestUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

@HiltViewModel
class ChatDetailViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow(ChatUiState())
    val state = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(
            roomInfo = ChatRoomState(
                0,
                "박병준, 박재욱",
            ),
            userInfo = TestUserModel(
                0,
                "박병준"
            )
        )
    }

    fun loadMessage() {
        var time = LocalDateTime.now()
        val messages: MutableList<TestMessageModel> = mutableListOf()
        for (i in 1..20) {

            if (Random.nextBoolean()) {
                time = time.plusDays(1)
            }
            messages.add(
                TestMessageModel(
                    0,
                    "이강현",
                    Random.nextInt(0.. 1),
                    "테스트 메세지 ${i}",
                    time
                )
            )
        }
        _state.value = _state.value.copy(
            message = messages.toImmutableList()
        )


    }
}