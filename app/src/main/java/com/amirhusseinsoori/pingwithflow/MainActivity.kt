package com.amirhusseinsoori.pingwithflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import arrow.core.Either
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
            isNetworkConnected().collect { data ->
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


    fun isNetworkConnected(): Flow<Result<Boolean>> = flow {
        emit(Result.success(COMMAND.handlePing()))
    }.catch { ex ->
        emit(Result.failure(ex))
    }.flowOn(Dispatchers.IO)


//    fun isNetworkConnected(): Flow<Either<Boolean, Throwable>> =
//        flow<Either<Boolean, Throwable>> {
//            emit(Either.Left(COMMAND.handlePing()))
//        }.catch { ex ->
//            emit(Either.Right(ex))
//        }.flowOn(Dispatchers.IO)
}



