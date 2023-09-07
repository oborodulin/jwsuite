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
import com.oborodulin.home.ui.main.MainScreen
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

private const val TAG = "App.MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).d("onCreate(Bundle?) called")
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT; // Make to run your application only in portrait mode
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // Make to run your application only in LANDSCAPE mode
        setContent {
            JWSuiteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
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