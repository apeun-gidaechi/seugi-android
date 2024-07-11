package com.apeun.gidaechi.chatseugi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apeun.gidaechi.chatseugi.model.ChatData
import com.apeun.gidaechi.chatseugi.model.ChatSeugiUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ChatSeugiViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ChatSeugiUiState())
    val state = _state.asStateFlow()

    fun sendMessage(message: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                chatMessage = it.chatMessage.toMutableList().apply {
                    add(0, ChatData.User(message, LocalDateTime.now(), true))
                }.toImmutableList(),
            )
        }
        delay(1000)
        val aiMessage = mutableListOf<ChatData>()
        when {
            message.indexOf("급식") != -1 -> {
                aiMessage.add(0, ChatData.AI("오늘 급식은 다음과 같습니다.", LocalDateTime.now(), true, false))
                aiMessage.add(
                    0,
                    ChatData.AI(
                        "아침\n---------\n쇠고기야채죽\n연유프렌치토스트\n배추김치\n포도\n허쉬초코크런치시리얼+우유\n\n" +
                            "점심\n---------\n추가밥\n매콤로제해물파스타\n#브리오슈수제버거\n모듬야채피클\n맥케인\n망고사고\n\n" +
                            "저녁\n---------\n현미밥\n돼지국밥\n삼색나물무침\n-오징어야채볶음\n석박지",
                        LocalDateTime.now(),
                        false,
                        true,
                    ),
                )
            }
            message.indexOf("7월 행사") != -1 -> {
                aiMessage.add(0, ChatData.AI("7월의 행사는 다음과 같습니다.", LocalDateTime.now(), true, false))
                aiMessage.add(
                    0,
                    ChatData.AI(
                        "7/12 나르샤프로젝트발표,SW축제\n7/16 독서토론대회\n7/17 해커톤",
                        LocalDateTime.now(),
                        false,
                        true,
                    ),
                )
            }
            message.indexOf("8월 행사") != -1 -> {
                aiMessage.add(0, ChatData.AI("8월의 행사는 다음과 같습니다.", LocalDateTime.now(), true, false))
                aiMessage.add(
                    0,
                    ChatData.AI(
                        "8/12 개학식, 타운홀미팅\n8/22 SW연합 토크콘서트\n8/30 수업나눔의 날",
                        LocalDateTime.now(),
                        false,
                        true,
                    ),
                )
            }
            message.indexOf("날씨") != -1 -> {
                aiMessage.add(0, ChatData.AI("오늘의 날씨는 다음과 같습니다.", LocalDateTime.now(), true, false))
                aiMessage.add(
                    0,
                    ChatData.AI(
                        "최고 기온 : 32°C\n최저 기온 : 21°C\n강수확률 : 20%\n습도 : 64%",
                        LocalDateTime.now(),
                        false,
                        true,
                    ),
                )
            }
            else -> {
                aiMessage.add(0, ChatData.AI("오늘의 명언입니다.", LocalDateTime.now(), true, false))
                aiMessage.add(
                    0,
                    ChatData.AI(
                        "절대 어제를 후회하지 마라 . 인생은 오늘의 나 안에 있고 내일은 스스로 만드는 것이다 - L.론허바드",
                        LocalDateTime.now(),
                        false,
                        true,
                    ),
                )
            }
        }

        _state.update {
            it.copy(
                chatMessage = it.chatMessage.toMutableList().apply {
                    this.addAll(0, aiMessage)
                }.toImmutableList(),
            )
        }
    }
}
