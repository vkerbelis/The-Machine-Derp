package org.thederps

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.thederps.client.ClientRetriever
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.EventDispatcher
import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.modules.IModule
import sx.blah.discord.modules.ModuleLoader
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas on 2016-10-07.
 */
class BotControllerTest {
    private lateinit var clientRetriever: ClientRetriever
    private lateinit var authenticator: Authenticator
    private lateinit var moduleLoader: ModuleLoader
    private lateinit var dispatcher: EventDispatcher
    private lateinit var controller: BotController
    private lateinit var client: IDiscordClient

    @Before
    fun setUp() {
        clientRetriever = mock(ClientRetriever::class.java)
        authenticator = mock(Authenticator::class.java)
        moduleLoader = mock(ModuleLoader::class.java)
        dispatcher = mock(EventDispatcher::class.java)
        client = mock(IDiscordClient::class.java)
        `when`(clientRetriever.getClient()).thenReturn(client)
        `when`(client.moduleLoader).thenReturn(moduleLoader)
        `when`(client.dispatcher).thenReturn(dispatcher)
        controller = BotController(clientRetriever)
    }

    @Test
    fun testSetUp_returnsLaunchTrue() {
        val launched = controller.setUp(authenticator)

        verify(authenticator).login(client)
        assertTrue("Not launched", launched)
    }

    @Test
    fun testSetUp_doesNotCrashOnAuthenticatorException() {
        doThrow(DiscordException("")).`when`(authenticator).login(client)

        val launched = controller.setUp(authenticator)

        verify(authenticator).login(client)
        assertFalse("Launched", launched)
    }

    @Test
    fun testSetUp_registersSelfAsMessageReceiver() {
        controller.setUp(authenticator)

        verify(dispatcher).registerListener(controller)
    }

    @Test
    fun testLaunchModule_loadsModuleIntoModuleLoader() {
        val module = mock(IModule::class.java)
        controller.setUp(authenticator)

        controller.launchModule(module)

        verify(moduleLoader).loadModule(module)
    }

    @Test
    fun testOnMessage() {
        controller.onMessage(mock(MessageReceivedEvent::class.java))
    }
}