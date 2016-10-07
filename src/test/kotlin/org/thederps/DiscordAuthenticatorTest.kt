package org.thederps

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.thederps.client.ClientRetriever
import org.thederps.testing.extensions.prepareRunForTest
import org.thederps.tools.AsyncRunner
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.EventDispatcher
import sx.blah.discord.handle.impl.events.DiscordDisconnectedEvent
import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.handle.impl.events.ReadyEvent
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas on 2016-10-07.
 */
class DiscordAuthenticatorTest {
    private lateinit var clientRetriever: ClientRetriever
    private lateinit var client: IDiscordClient
    private lateinit var authenticator: DiscordAuthenticator
    private lateinit var dispatcher: EventDispatcher
    private lateinit var asyncRunner: AsyncRunner

    @Before
    fun setUp() {
        clientRetriever = mock(ClientRetriever::class.java)
        client = mock(IDiscordClient::class.java)
        `when`(clientRetriever.getClient()).thenReturn(client)
        dispatcher = mock(EventDispatcher::class.java)
        `when`(client.dispatcher).thenReturn(dispatcher)
        asyncRunner = mock(AsyncRunner::class.java)
        authenticator = DiscordAuthenticator(clientRetriever, asyncRunner)
    }

    @Test
    fun testLogin_callsClientLogin() {
        authenticator.login()

        verify(client).login()
    }

    @Test
    fun testLogin_registersEventDispatcher() {
        authenticator.login()

        verify(dispatcher).registerListener(authenticator)
    }

    @Test
    fun testOnReady_passesWithoutException() {
        authenticator.onReady(mock(ReadyEvent::class.java))
    }

    @Test
    fun testOnMessage_passesWithoutException() {
        authenticator.onMessage(mock(MessageReceivedEvent::class.java))
    }

    @Test
    fun testOnDisconnect_callsClientLogin() {
        asyncRunner.prepareRunForTest()

        authenticator.onDisconnect(mock(DiscordDisconnectedEvent::class.java))

        verify(client).login()
    }

    @Test
    fun testOnDisconnect_doesNotCrashOnClientLoginException() {
        asyncRunner.prepareRunForTest()
        doThrow(DiscordException::class.java).`when`(client).login()

        authenticator.onDisconnect(mock(DiscordDisconnectedEvent::class.java))

        verify(client).login()
    }
}


