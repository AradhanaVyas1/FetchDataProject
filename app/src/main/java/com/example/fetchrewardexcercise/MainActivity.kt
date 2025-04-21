package com.example.fetchrewardexcercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.fetchrewardexcercise.ui.theme.FetchRewardExcerciseTheme
import com.example.fetchrewardexcercise.ui.view.MainScreen
import com.example.fetchrewardexcercise.ui.viewmodel.ItemViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FetchRewardExcerciseTheme {
                val viewModel = ItemViewModel() // manually created
                MainScreen(viewModel)
            }
        }
    }
}