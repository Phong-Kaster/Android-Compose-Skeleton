package com.example.skeleton.ui.fragment.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.skeleton.R
import com.example.skeleton.core.CoreFragment
import com.example.skeleton.core.CoreLayout
import com.example.skeleton.ui.component.CoreBottomBar
import com.example.skeleton.ui.component.CoreTopBar
import com.example.skeleton.ui.fragment.home.component.HomeRequestPermission
import com.example.skeleton.ui.fragment.home.component.isLocationGranted
import com.example.skeleton.ui.fragment.home.component.isNotificationGranted
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : CoreFragment() {
    private val viewModel: HomeViewModel by viewModel()

    // Enable request permission
    var triggerRequestPermission by mutableStateOf(0)


    override fun onResume() {
        super.onResume()
        checkPermission()
    }

    private fun checkPermission() {

        val notiEnable = isNotificationGranted(requireContext())
        val locationEnable = isLocationGranted(requireContext())

        if (!notiEnable) {
            triggerRequestPermission++
        }

        if (!locationEnable) {
            triggerRequestPermission++
        }
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()



        HomeLayout(
            uiState = viewModel.uiState.collectAsState().value,
        )

        // Request camera permission & storage permission
        HomeRequestPermission(
            enable = triggerRequestPermission,
            onNotificationGranted = {
                // Handle notification granted
            },
            onLocationGranted = {
                // Handle location granted
            }
        )
    }
}

@Composable
private fun HomeLayout(
    uiState: HomeUiState,
) {
    CoreLayout(
        modifier = Modifier,
        topBar = { CoreTopBar(title = stringResource(R.string.home)) },
        bottomBar = { CoreBottomBar() },
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