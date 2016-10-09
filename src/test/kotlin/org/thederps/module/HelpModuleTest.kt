package org.thederps.module

import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.thederps.testing.CommandModuleTest
import org.thederps.testing.extensions.setUpMessage
import org.thederps.testing.extensions.setUpMessageBot
import org.thederps.tools.appendCommand

/**
 * @author Vidmantas on 2016-10-09.
 */
class HelpModuleTest : CommandModuleTest<HelpModule>() {
    override val module: HelpModule
        get() = HelpModule(messageCreator, moduleRetriever)

    @Test
    fun testGetMessageCreator_returnsSetValue() {
        val actualMessageCreator = module.messageCreator

        Assert.assertSame("Not same", messageCreator, actualMessageCreator)
    }

    @Test
    fun testGetModuleRetriever_returnsSetValue() {
        val actualModuleRetriever = module.moduleRetriever

        Assert.assertSame("Not same", moduleRetriever, actualModuleRetriever)
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
    fun testOnMessage_sendsMessageToSameChannel() {
        setUpMessage(module.command)

        module.onMessage(messageEvent)

        verify(messageBuilder).withChannel(channel)
        verify(messageBuilder).build()
    }

    @Test
    fun testOnMessage_doesNotSendMessageWhenCommandValidUserIsBot() {
        setUpMessageBot(module.command)

        module.onMessage(messageEvent)

        verify(messageBuilder, Mockito.times(0)).withChannel(channel)
        verify(messageBuilder, Mockito.times(0)).build()
    }

    @Test
    fun testOnMessage_doesNotSendMessageWhenCommandInvalid() {
        setUpMessage("__")

        module.onMessage(messageEvent)

        verify(messageBuilder, Mockito.times(0)).withChannel(channel)
        verify(messageBuilder, Mockito.times(0)).build()
    }

    @Test
    fun testOnMessage_doesNotSendMessageWhenCommandInvalidUserIsBot() {
        setUpMessageBot("__")

        module.onMessage(messageEvent)

        verify(messageBuilder, Mockito.times(0)).withChannel(channel)
        verify(messageBuilder, Mockito.times(0)).build()
    }

    @Test
    fun testOnMessage_appendsMessageWithModuleCommands() {
        setUpMessage(module.command)
        val listModule = mock(Module::class.java)
        `when`(listModule.command).thenReturn("command")
        `when`(listModule.description).thenReturn("")
        val expectedModules = listOf<Module>(listModule)
        `when`(moduleRetriever.getModules()).thenReturn(expectedModules)

        module.onMessage(messageEvent)

        verify(messageBuilder).appendCommand(1, listModule.command, listModule.description)
    }

    @Test
    fun testOnMessage_skipsNonExecutableCommandAppend() {
        setUpMessage(module.command)
        val listModule = mock(Module::class.java)
        `when`(listModule.command).thenReturn("")
        `when`(listModule.description).thenReturn("description")
        val expectedModules = listOf<Module>(listModule)
        `when`(moduleRetriever.getModules()).thenReturn(expectedModules)

        module.onMessage(messageEvent)

        verify(moduleRetriever).getModules()
        verify(messageBuilder, Mockito.times(0)).appendCommand(1, listModule.command, listModule.description)
    }
}




