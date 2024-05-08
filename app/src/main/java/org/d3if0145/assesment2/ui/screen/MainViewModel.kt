package org.d3if0145.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if0145.assesment2.database.ParfumDao
import org.d3if0145.assesment2.model.Parfum

class MainViewModel(dao: ParfumDao) : ViewModel() {
    val data: StateFlow<List<Parfum>> = dao.getParfum().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}