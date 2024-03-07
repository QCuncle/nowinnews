package me.qcuncle.nowinnews.presentation.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.qcuncle.nowinnews.domain.model.Bookmark
import me.qcuncle.nowinnews.domain.usecases.bookmark.GetAllBookmarks
import me.qcuncle.nowinnews.domain.usecases.bookmark.RemoveBookmark
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getAllBookmarks: GetAllBookmarks,
    private val removeBookmark: RemoveBookmark,
) : ViewModel() {

    private val _data = MutableStateFlow(emptyList<Bookmark>())
    val data: StateFlow<List<Bookmark>> = _data.asStateFlow()

    private val _isEmpty = mutableStateOf(false)
    val isEmpty: State<Boolean> = _isEmpty

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAllBookmarks().collect { bookmarks ->
                _data.value = bookmarks
                _isEmpty.value = bookmarks.isEmpty()
            }
        }
    }

    fun onEvent(event: BookmarkEvent) {
        when (event) {
            is BookmarkEvent.DeleteEvent -> {
                viewModelScope.launch(Dispatchers.IO) {
                    removeBookmark(event.url)
                }
            }
        }
    }
}