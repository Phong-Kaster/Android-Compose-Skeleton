package com.example.skeleton.ui.fragment.home

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.skeleton.R
import com.example.skeleton.core.CoreFragment
import com.example.skeleton.core.CoreLayout
import com.example.skeleton.ui.component.CoreBottomBar
import com.example.skeleton.ui.component.CoreTopBar
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.getValue

class HomeFragment : CoreFragment() {
    private val viewModel: HomeViewModel by viewModel()
    @Composable
    override fun ComposeView() {
        super.ComposeView()
        HomeLayout(
            uiState = viewModel.uiState.collectAsState().value,
        )
    }
}

@Composable
private fun HomeLayout(
    uiState: HomeUiState,
) {
    CoreLayout(
        modifier = Modifier,
        topBar = {
            CoreTopBar(title = "Home")
        },
        bottomBar = {
            CoreBottomBar()
        },
        content = {

        }
    )
}

@Preview
@Composable
private fun HomeLayoutPreview() {
    HomeLayout(
        uiState = HomeUiState()
    )
}