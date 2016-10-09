package org.thederps.tools

import org.junit.Test
import org.mockito.Mockito.mock

/**
 * @author Vidmantas on 2016-10-08.
 */
class CompletableFutureAsyncRunnerTest {
    @Test
    fun testRun_doesNotCrash() {
        CompletableFutureAsyncRunner().run(mock(Runnable::class.java))
    }
}