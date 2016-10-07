package org.thederps.tools

import java.util.concurrent.CompletableFuture

/**
 * @author Vidmantas on 2016-10-07.
 */
class CompletableFutureAsyncRunner : AsyncRunner {
    override fun run(runnable: Runnable) {
        CompletableFuture.runAsync(runnable)
    }
}