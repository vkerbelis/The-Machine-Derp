package org.thederps

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.thederps.client.ClientRetriever
import org.thederps.command.Command
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.EventDispatcher
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas on 2016-10-07.
 */
class BotControllerTest {
    private lateinit var clientRetriever: ClientRetriever
    private lateinit var authenticator: Authenticator
    private lateinit var dispatcher: EventDispatcher
    private lateinit var controller: BotController
    private lateinit var client: IDiscordClient

    @Before
    fun setUp() {
        authenticator = mock(Authenticator::class.java)
        clientRetriever = mock(ClientRetriever::class.java)
        client = mock(IDiscordClient::class.java)
        dispatcher = mock(EventDispatcher::class.java)
        `when`(clientRetriever.getClient()).thenReturn(client)
        `when`(client.dispatcher).thenReturn(dispatcher)
        controller = BotController(clientRetriever)
    }

    @Test
    fun testLaunch_callsLoginAction() {
        controller.launch(authenticator)

        verify(authenticator).login(client)
    }

    @Test
    fun testLaunch_doesNotCrashOnAuthenticatorException() {
        doThrow(DiscordException("")).`when`(authenticator).login(client)

        controller.launch(authenticator)

        verify(authenticator).login(client)
    }

    @Test
    fun testRegisterCommand_registersDispatcherListener() {
        val command = mock(Command::class.java)
        controller.launch(authenticator)

        controller.registerCommand(command)

        verify(dispatcher).registerListener(command)
    }
}