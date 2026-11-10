package com.yu.my_note_app.data

sealed interface SaveResult {
    data object Success: SaveResult
    data object NoteFound: SaveResult
    data object Failure: SaveResult
}