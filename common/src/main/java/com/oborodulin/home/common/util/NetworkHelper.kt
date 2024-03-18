package com.oborodulin.home.common.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.oborodulin.home.common.data.network.ConnectionState
import com.oborodulin.home.common.util.LogLevel.LOG_NETWORK
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

private const val TAG = "Network"

// https://medium.com/scalereal/observing-live-connectivity-status-in-jetpack-compose-way-f849ce8431c7
// https://stackoverflow.com/questions/32242384/android-getallnetworkinfo-is-deprecated-what-is-the-alternative?answertab=trending#tab-top
/**
 * Network utility to get current state of internet connection
 */
val Context.currentConnectivityState: ConnectionState
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return getCurrentConnectivityState(connectivityManager)
    }

private fun getCurrentConnectivityState(
    connectivityManager: ConnectivityManager
): ConnectionState {
    // https://stackoverflow.com/questions/70107145/connectivity-manager-allnetworks-deprecated
    val network =
        connectivityManager.activeNetwork // network is currently in a high power state for performing data transmission.
    if (LOG_NETWORK) {
        Timber.tag(TAG).d("active network $network")
    }
    network ?: return ConnectionState.Unavailable // return Unavailable if network is null
    val actNetwork = connectivityManager.getNetworkCapabilities(network)
        ?: return ConnectionState.Unavailable // return false if Network Capabilities is null
    return when {
        actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> { // check if wifi is connected
            if (LOG_NETWORK) {
                Timber.tag(TAG).d("wifi connected")
            }
            ConnectionState.Available
        }

        actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> { // check if mobile dats is connected
            if (LOG_NETWORK) {
                Timber.tag(TAG).d("cellular network connected")
            }
            ConnectionState.Available
        }

        else -> {
            if (LOG_NETWORK) {
                Timber.tag(TAG).d("internet not connected")
            }
            ConnectionState.Unavailable
        }
    }
    /*val connected = connectivityManager.allNetworks.any { network ->
        connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }
    return if (connected) ConnectionState.Available else ConnectionState.Unavailable
     */
}

/**
 * Network Utility to observe availability or unavailability of Internet connection
 */
fun Context.observeConnectivityAsFlow() = callbackFlow {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val callback = NetworkCallback { connectionState -> trySend(connectionState) }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    // Set current state
    val currentState = getCurrentConnectivityState(connectivityManager)
    trySend(currentState)

    // Remove callback when not used
    awaitClose {
        // Remove listeners
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

fun NetworkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectionState.Available)
        }

        override fun onLost(network: Network) {
            callback(ConnectionState.Unavailable)
        }
    }
}

@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    // Creates a State<ConnectionState> with current connectivity state as initial value
    return produceState(initialValue = context.currentConnectivityState) {
        // In a coroutine, can make suspend calls
        context.observeConnectivityAsFlow().collect { value = it }
    }
}