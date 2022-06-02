package com.muzz_test_felipe.core

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class AppExecutors(
    private val diskIo: Executor,
    private val networkIo: Executor,
    private val mainThread: Executor
) {
    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecutor()
    )

    fun diskIo(): Executor = diskIo
    fun networkIo(): Executor = networkIo
    fun mainThread(): Executor = mainThread

    private class MainThreadExecutor : Executor {
        private val threadHandler = Handler(Looper.getMainLooper())
        override fun execute(p0: Runnable) {
            threadHandler.post(p0)
        }
    }
}