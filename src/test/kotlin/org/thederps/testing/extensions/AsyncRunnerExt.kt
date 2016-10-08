package org.thederps.testing.extensions

import org.mockito.Mockito.doAnswer
import org.thederps.tools.AsyncRunner

/**
 * @author Vidmantas on 2016-10-07.
 */
internal fun AsyncRunner.prepareRunForTest() {
    doAnswer({ invocation ->
        val runnable = invocation.arguments[0] as Runnable
        runnable.run()
    }).`when`(this).run(any())
}