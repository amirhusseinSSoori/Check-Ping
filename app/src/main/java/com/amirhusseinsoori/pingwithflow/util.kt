package com.amirhusseinsoori.pingwithflow

import arrow.core.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


const val COMMAND = "/system/bin/ping -c 1 8.8.8.8"

fun String.handlePing(): Boolean = Runtime.getRuntime().exec(this).waitFor() == 0


interface SolutionType {
    fun eitherSolution(): Flow<Either<Boolean, Throwable>>
    fun resultSolution(): Flow<Result<Boolean>>
}


class NetworkConnecting : SolutionType {
    override fun eitherSolution(): Flow<Either<Boolean, Throwable>> =
        flow<Either<Boolean, Throwable>> {
            emit(Either.Left(COMMAND.handlePing()))
        }.catch { ex ->
            emit(Either.Right(ex))
        }.flowOn(Dispatchers.IO)

    override fun resultSolution(): Flow<Result<Boolean>> = flow {
        emit(Result.success(COMMAND.handlePing()))
    }.catch { ex ->
        emit(Result.failure(ex))
    }.flowOn(Dispatchers.IO)

}


sealed class NetworkConnectingTypeState {
    object Result : NetworkConnectingTypeState()
    object Either : NetworkConnectingTypeState()
}



