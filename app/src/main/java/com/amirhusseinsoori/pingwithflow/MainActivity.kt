package com.amirhusseinsoori.pingwithflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.lang.Runtime.getRuntime

class MainActivity : AppCompatActivity() {

    private val COMMAND = "/system/bin/ping -c 1 8.8.8.8"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope.launchWhenStarted {
            isConnectedToInternet().collect { data ->
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


    }
    private fun isConnectedToInternet(): Flow<Result<Boolean>> = flow {
        emit(Result.success(COMMAND.handlePing()))
    }.catch { ex ->
        emit(Result.failure(ex))
    }.flowOn(Dispatchers.IO)
}



