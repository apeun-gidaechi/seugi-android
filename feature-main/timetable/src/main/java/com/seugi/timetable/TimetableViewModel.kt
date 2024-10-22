package com.seugi.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seugi.common.model.Result
import com.seugi.data.core.model.TimetableModel
import com.seugi.data.timetable.TimetableRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val timetableRepository: TimetableRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<ImmutableMap<String, ImmutableList<TimetableModel>>>(
        persistentMapOf(),
    )
    val state = _state.asStateFlow()

    fun loadTimeTable(workspaceId: String) = viewModelScope.launch {
        timetableRepository.getTimetableWeekend(
            workspaceId = workspaceId,
        ).collect {
            when (it) {
                is Result.Success -> {
                    _state.update { _ ->
                        it.data
                            .groupBy { it.time }
                            .mapValues {
                                it.value
                                    .sortedBy {
                                        (((it.date.year * 12) + it.date.monthNumber) * 30) + it.date.dayOfMonth
                                    }
                                    .toImmutableList()
                            }
                            .toImmutableMap()
                    }
                }
                Result.Loading -> {
                }
                is Result.Error -> {
                    it.throwable.printStackTrace()
                }
            }
        }
    }
}
