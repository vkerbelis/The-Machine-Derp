package org.thederps.testing.extensions

import org.mockito.Mockito
import org.thederps.module.Module
import org.thederps.testing.CommandModuleTest

/**
 * @author Vidmantas on 2016-10-09.
 */

fun <M : Module> CommandModuleTest<M>.setUpMessage(content: String) {
    Mockito.`when`(message.content).thenReturn(content)
    Mockito.`when`(user.isBot).thenReturn(false)
}

fun <M : Module> CommandModuleTest<M>.setUpMessageBot(content: String) {
    Mockito.`when`(message.content).thenReturn(content)
    Mockito.`when`(user.isBot).thenReturn(true)
}