package org.thederps.module

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.thederps.testing.CommandModuleTest
import org.thederps.testing.extensions.setUpMessage
import org.thederps.testing.extensions.setUpMessageBot

/**
 * @author Vidmantas on 2016-10-09.
 */
class ModuleControlModuleTest : CommandModuleTest<ModuleControlModule>() {
    override val module: ModuleControlModule
        get() = ModuleControlModule(messageCreator)

    @Test
    fun testGetMessageCreator_returnsSetValue() {
        val actualMessageCreator = module.messageCreator

        Assert.assertSame("Not same", messageCreator, actualMessageCreator)
    }

    @Test
    fun testEnable_isEnabled() {
        val enabled = module.enable(client)

        Assert.assertTrue("Disabled", enabled)
    }

    @Test
    fun testDisable() {
        module.disable()
    }

    @Test
    fun testGetCommand_notEmpty() {
        Assert.assertTrue("Command empty", !module.command.isEmpty())
    }

    @Test
    fun testOnMessage_sendsMessageToSameChannelWhenCommandValidUserNotBot() {
        setUpMessage(module.command)

        module.onMessage(messageEvent)

        Mockito.verify(messageBuilder).withChannel(channel)
        Mockito.verify(messageBuilder).build()
    }

    @Test
    fun testOnMessage_doesNotSendMessageWhenCommandValidUserIsBot() {
        setUpMessageBot(module.command)

        module.onMessage(messageEvent)

        Mockito.verify(messageBuilder, Mockito.times(0)).withChannel(channel)
        Mockito.verify(messageBuilder, Mockito.times(0)).build()
    }

    @Test
    fun testOnMessage_doesNotSendMessageWhenCommandInvalid() {
        setUpMessage("__")

        module.onMessage(messageEvent)

        Mockito.verify(messageBuilder, Mockito.times(0)).withChannel(channel)
        Mockito.verify(messageBuilder, Mockito.times(0)).build()
    }

    @Test
    fun testOnMessage_doesNotSendMessageWhenCommandInvalidUserIsBot() {
        setUpMessageBot("__")

        module.onMessage(messageEvent)

        Mockito.verify(messageBuilder, Mockito.times(0)).withChannel(channel)
        Mockito.verify(messageBuilder, Mockito.times(0)).build()
    }
}