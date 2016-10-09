package org.thederps.module

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.thederps.Authenticator
import org.thederps.testing.extensions.prepareRunForTest
import org.thederps.tools.AsyncRunner
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.EventDispatcher
import sx.blah.discord.handle.impl.events.DiscordDisconnectedEvent
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas on 2016-10-08.
 */
class ReconnectModuleTest {
    private lateinit var disconnectEvent: DiscordDisconnectedEvent
    private lateinit var authenticator: Authenticator
    private lateinit var asyncRunner: AsyncRunner
    private lateinit var dispatcher: EventDispatcher
    private lateinit var module: ReconnectModule
    private lateinit var client: IDiscordClient

    @Before
    fun setUp() {
        client = mock(IDiscordClient::class.java)
        asyncRunner = mock(AsyncRunner::class.java)
        dispatcher = mock(EventDispatcher::class.java)
        authenticator = mock(Authenticator::class.java)
        disconnectEvent = mock(DiscordDisconnectedEvent::class.java)
        `when`(disconnectEvent.client).thenReturn(client)
        `when`(client.dispatcher).thenReturn(dispatcher)
        module = ReconnectModule(authenticator, asyncRunner)
    }

    @Test
    fun testGetAuthenticator_returnsSetValue() {
        val actualAuthenticator = module.authenticator

        Assert.assertSame("Not same", authenticator, actualAuthenticator)
    }

    @Test
    fun testGetAsyncRunner_returnsSetValue() {
        val actualAsyncRunner = module.asyncRunner

        Assert.assertSame("Not same", asyncRunner, actualAsyncRunner)
    }

    @Test
    fun testEnable_returnsTrue() {
        val enabled = module.enable(client)

        Assert.assertTrue("Disabled", enabled)
    }

    @Test
    fun testDisable() {
        module.disable()
    }

    @Test
    fun testGetCommand_isEmpty() {
        Assert.assertTrue("Command not empty", module.command.isEmpty())
    }

    @Test
    fun testGetDescription_notEmpty() {
        Assert.assertTrue("Description is empty", !module.description.isEmpty())
    }

    @Test
    fun testGetName_notEmpty() {
        Assert.assertTrue("Name is empty", !module.name.isEmpty())
    }

    @Test
    fun testGetVersion_notEmpty() {
        Assert.assertTrue("Version is empty", !module.version.isEmpty())
    }

    @Test
    fun testGetMinimumDiscord4JVersion_notEmpty() {
        Assert.assertTrue("Min version is empty", !module.minimumDiscord4JVersion.isEmpty())
    }

    @Test
    fun testGetAuthor_isMe() {
        Assert.assertTrue("Author is empty", !module.author.isEmpty())
        Assert.assertEquals("Author is not me", "Vidmantas K.", module.author)
    }

    @Test
    fun testOnDisconnect_callsClientLoginIfNotTerminated() {
        `when`(authenticator.isTerminated()).thenReturn(false)
        asyncRunner.prepareRunForTest()

        module.onDisconnect(disconnectEvent)

        verify(authenticator).login(client)
    }

    @Test
    fun testOnDisconnect_doesNotCrashOnClientLoginException() {
        `when`(authenticator.isTerminated()).thenReturn(false)
        asyncRunner.prepareRunForTest()
        doThrow(DiscordException::class.java).`when`(authenticator).login(client)

        module.onDisconnect(disconnectEvent)

        verify(authenticator).login(client)
    }

    @Test
    fun testOnDisconnect_doesNothingIfTerminated() {
        `when`(authenticator.isTerminated()).thenReturn(true)
        asyncRunner.prepareRunForTest()

        module.onDisconnect(disconnectEvent)

        verify(authenticator, Mockito.times(0)).login(client)
    }
}