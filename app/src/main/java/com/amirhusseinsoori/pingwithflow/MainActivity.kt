package com.amirhusseinsoori.pingwithflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launchWhenStarted {
            isNetworkConnected(NetworkConnectingTypeState.Result)
        }
    }

    private suspend fun isNetworkConnected(state: NetworkConnectingTypeState) {
        when (state) {
            NetworkConnectingTypeState.Result -> {
                Di.injectConnection().resultSolution().collect { data ->
                    data.fold(onSuccess = {
                        when (it) {
                            true -> Log.e("result", " : isConnected ")
                            false -> Log.e("result", " : isNotConnected ")
                        }
                    }, onFailure = { ex ->
                        Log.e("result", "showError :  ${ex.message}")
                    })
                }
            }

            NetworkConnectingTypeState.Either -> {
                Di.injectConnection().eitherSolution().collect { data ->
                    data.fold(ifLeft = {
                        when (it) {
                            true -> Log.e("result", " : isConnected ")
                            false -> Log.e("result", " : isNotConnected ")
                        }
                    }, ifRight = { ex ->
                        Log.e("result", "showError :  ${ex.message}")
                    })

                }
            }
        }

    }
}









