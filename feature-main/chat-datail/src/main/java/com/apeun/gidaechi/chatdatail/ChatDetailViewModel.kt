package com.apeun.gidaechi.chatdatail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.chatdatail.model.ChatDetailUiState
import com.apeun.gidaechi.chatdatail.model.ChatRoomState
import com.apeun.gidaechi.chatdatail.model.TestMessageModel
import com.apeun.gidaechi.chatdatail.model.TestUserModel
import com.apeun.gidaechi.chatdetail.ChatDetailRepository
import com.apeun.gidaechi.common.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class ChatDetailViewModel @Inject constructor(
    private val testConnect: ChatDetailRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChatDetailUiState())
    val state = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(
            roomInfo = ChatRoomState(
                2,
                "박병준, 박재욱",
                members = persistentListOf(
                    TestUserModel(0, "노영재"),
                    TestUserModel(0, "노영재"),
                    TestUserModel(0, "노영재"),
                    TestUserModel(0, "노영재"),
                    TestUserModel(0, "노영재"),
                    TestUserModel(0, "노영재"),
                    TestUserModel(0, "노영재"),
                    TestUserModel(0, "노영재"),
                    TestUserModel(0, "노영재"),
                    TestUserModel(0, "노영재"),
                    TestUserModel(0, "노영재"),
                    TestUserModel(0, "노영재"),
                    TestUserModel(0, "노영재"),

                ),
            ),
            userInfo = TestUserModel(
                3,
                "박병준",
            ),
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
                    Random.nextInt(0..1),
                    "테스트 메세지 $i",
                    time,
                ),
            )
        }
        _state.value = _state.value.copy(
            message = messages.toImmutableList(),
        )
    }

    fun testInit() = viewModelScope.launch {
        testConnect.subscribeRoom(
            chatRoomId = 2,
        ).collectLatest {
            when (it) {
                is Result.Success -> {
                    val data = it.data
                    val message = _state.value.message.toMutableList()
                    message.add(
                        TestMessageModel(
                            id = 3,
                            userName = data.author.name,
                            userId = data.author.id,
                            message = data.message,
                            createdAt = data.timestamp
                        )
                    )
                    _state.value = _state.value.copy(
                        message = message.toImmutableList()
                    )
                }
                is Result.Loading -> {}
                is Result.Error -> {

                }
            }
        }
        Log.d("TAG", "testInit: 구독끝남")
    }

    fun testSend(
        content: String
    ) {
        viewModelScope.launch {
            val e = testConnect.sendMessage(2, content)
            Log.d("TAG", "testSend: $e")
        }
    }
}
