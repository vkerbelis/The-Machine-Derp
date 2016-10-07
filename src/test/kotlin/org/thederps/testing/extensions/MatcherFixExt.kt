package org.thederps.testing.extensions

import org.mockito.Mockito

/**
 * @author Vidmantas on 2016-10-08.
 */
fun <T> any(): T {
    return Mockito.any<T>()
}