package org.thederps

import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.thederps.client.ClientRetriever
import org.thederps.tools.AsyncRunner
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.EventDispatcher
import sx.blah.discord.handle.impl.events.ReadyEvent

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
        authenticator = DiscordAuthenticator()
    }

    @Test
    fun testLogin_callsClientLogin() {
        authenticator.login(clientRetriever.getClient())

        verify(client).login()
    }

    @Test
    fun testOnReady_passesWithoutException() {
        authenticator.onReady(mock(ReadyEvent::class.java))
    }

    @Test
    fun testIsTerminated_returnsFalseByDefault() {
        val terminated = authenticator.isTerminated()

        assertFalse("Terminated", terminated)
    }
}


