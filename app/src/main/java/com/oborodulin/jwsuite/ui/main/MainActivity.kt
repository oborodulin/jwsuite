package com.oborodulin.jwsuite.ui.main

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.R
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.rememberAppState
import com.oborodulin.jwsuite.presentation.ui.session.SessionUiAction
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.ui.navigation.graphs.RootNavigationGraph
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

private const val TAG = "App.MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // https://stackoverflow.com/questions/53665470/wait-until-kotlin-coroutine-finishes-in-oncreateview?rq=4
    private var job: Job = Job()
    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val viewModel: SessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate(Bundle?) called")
        // Make to run your application only in portrait mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // Make to run your application only in LANDSCAPE mode
        setContent {
            JWSuiteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val appState = rememberAppState(appName = stringResource(R.string.app_name))
                    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
                        CommonScreen(state = state) { session ->
                            Timber.tag(TAG)
                                .d("mainActivity: startDestination = %s", session.startDestination)
                            RootNavigationGraph(
                                appState = appState,
                                startDestination = session.startDestination
                                    ?: NavRoutes.Signup.route
                            )
                        }
                    }
                }
            }
        }
    }

    // https://stackoverflow.com/questions/65182773/what-does-androidconfigchanges-do
    // https://developer.android.com/guide/topics/manifest/activity-element#config
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Timber.tag(TAG).d("onConfigurationChanged(newConfig) called")
    }

    //https://stackoverflow.com/questions/74012465/how-to-get-lifecyclescope-from-context-or-activity
    override fun onStop() {
        super.onStop()
        Timber.tag(TAG).d("onStop() called")
        val logoutJob = lifecycleScope.launch {
            viewModel.actionsJobFlow.collectLatest { job ->
                Timber.tag(TAG).d(
                    "MemberScreen(...): Start actionsJobFlow.collect [job = %s]",
                    job?.toString()
                )
                job?.join()
            }
        }
        // logout
        viewModel.submitAction(SessionUiAction.Logout)
        //logoutJob.join()
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DefaultPreview() {
    JWSuiteTheme { Surface { MainScreen(rememberAppState()) } }
}