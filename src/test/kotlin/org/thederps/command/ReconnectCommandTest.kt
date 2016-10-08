package org.thederps.command

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.thederps.Authenticator
import org.thederps.testing.extensions.prepareRunForTest
import org.thederps.tools.AsyncRunner
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.handle.impl.events.DiscordDisconnectedEvent
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas on 2016-10-08.
 */
class ReconnectCommandTest {
    private lateinit var disconnectEvent: DiscordDisconnectedEvent
    private lateinit var authenticator: Authenticator
    private lateinit var asyncRunner: AsyncRunner
    private lateinit var command: ReconnectCommand
    private lateinit var client: IDiscordClient

    @Before
    fun setUp() {
        client = mock(IDiscordClient::class.java)
        asyncRunner = mock(AsyncRunner::class.java)
        authenticator = mock(Authenticator::class.java)
        disconnectEvent = mock(DiscordDisconnectedEvent::class.java)
        `when`(disconnectEvent.client).thenReturn(client)
        command = ReconnectCommand(authenticator, asyncRunner)
    }

    @Test
    fun testOnDisconnect_callsClientLoginIfNotTerminated() {
        `when`(authenticator.isTerminated()).thenReturn(false)
        asyncRunner.prepareRunForTest()

        command.onDisconnect(disconnectEvent)

        verify(authenticator).login(client)
    }

    @Test
    fun testOnDisconnect_doesNotCrashOnClientLoginException() {
        `when`(authenticator.isTerminated()).thenReturn(false)
        asyncRunner.prepareRunForTest()
        doThrow(DiscordException::class.java).`when`(authenticator).login(client)

        command.onDisconnect(disconnectEvent)

        verify(authenticator).login(client)
    }

    @Test
    fun testOnDisconnect_doesNothingIfTerminated() {
        `when`(authenticator.isTerminated()).thenReturn(true)
        asyncRunner.prepareRunForTest()

        command.onDisconnect(disconnectEvent)

        verify(authenticator, Mockito.times(0)).login(client)
    }
}