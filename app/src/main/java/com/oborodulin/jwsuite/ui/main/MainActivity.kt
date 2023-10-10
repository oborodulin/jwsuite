package com.oborodulin.jwsuite.ui.main

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

private const val TAG = "App.MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                ) { MainScreen() }
            }
        }
    }

    // https://stackoverflow.com/questions/65182773/what-does-androidconfigchanges-do
    // https://developer.android.com/guide/topics/manifest/activity-element#config
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Timber.tag(TAG).d("onConfigurationChanged(Configuration) called")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag(TAG).d("onStop() called")
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DefaultPreview() {
    JWSuiteTheme {
        Surface {
            MainScreen()
        }
    }
}