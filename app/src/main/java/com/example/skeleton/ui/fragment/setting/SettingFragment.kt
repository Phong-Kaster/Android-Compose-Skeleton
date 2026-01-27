package com.example.skeleton.ui.fragment.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.skeleton.core.CoreFragment
import com.example.skeleton.core.CoreLayout
import com.example.skeleton.ui.component.CoreBottomBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : CoreFragment() {
    private val viewModel: SettingViewModel by viewModel()

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        SettingLayout(
            uiState = viewModel.uiState.collectAsState().value,
        )
    }
}

@Composable
private fun SettingLayout(
    uiState: SettingUiState,
) {
    CoreLayout(
        bottomBar = {
            CoreBottomBar()
        },
        content = {

        }
    )
}

@Preview
@Composable
private fun PreviewSettingLayout() {
    SettingLayout(
        uiState = SettingUiState(),
    )
}