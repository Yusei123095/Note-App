package com.yu.my_note_app

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yu.my_note_app.ui.editor.EditorScreen
import com.yu.my_note_app.ui.editor.EditorType
import com.yu.my_note_app.ui.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable data object Home
@Serializable data class Editor(val id: String? = null, val type: EditorType = EditorType.NOTE)

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination=Home){
        composable<Home>{
            HomeScreen(
                onEditor = {id, type -> navController.navigate(Editor(id, type))}
            )
        }
        composable<Editor> {
            EditorScreen(onBack = {navController.popBackStack()})
        }

    }

}