package org.thederps

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.thederps.client.ClientRetriever
import org.thederps.tools.MessageCreator
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.EventDispatcher
import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.handle.impl.obj.Channel
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.modules.IModule
import sx.blah.discord.modules.ModuleLoader
import sx.blah.discord.util.DiscordException
import sx.blah.discord.util.MessageBuilder

/**
 * @author Vidmantas on 2016-10-07.
 */
class BotControllerTest {
    private lateinit var clientRetriever: ClientRetriever
    private lateinit var messageBuilder: MessageBuilder
    private lateinit var authenticator: Authenticator
    private lateinit var messageEvent: MessageReceivedEvent
    private lateinit var moduleLoader: ModuleLoader
    private lateinit var dispatcher: EventDispatcher
    private lateinit var controller: BotController
    private lateinit var message: IMessage
    private lateinit var client: IDiscordClient
    private lateinit var user: IUser

    @Before
    fun setUp() {
        clientRetriever = mock(ClientRetriever::class.java)
        messageBuilder = mock(MessageBuilder::class.java)
        authenticator = mock(Authenticator::class.java)
        messageEvent = mock(MessageReceivedEvent::class.java)
        moduleLoader = mock(ModuleLoader::class.java)
        dispatcher = mock(EventDispatcher::class.java)
        message = mock(IMessage::class.java)
        client = mock(IDiscordClient::class.java)
        user = mock(IUser::class.java)
        `when`(clientRetriever.getClient()).thenReturn(client)
        `when`(messageEvent.message).thenReturn(message)
        `when`(client.moduleLoader).thenReturn(moduleLoader)
        `when`(client.dispatcher).thenReturn(dispatcher)
        `when`(message.author).thenReturn(user)
        val messageCreator = mock(MessageCreator::class.java)
        `when`(messageCreator.with(client)).thenReturn(messageBuilder)
        `when`(messageBuilder.withChannel(
                org.thederps.testing.extensions.any<Channel>())
        ).thenReturn(messageBuilder)
        `when`(messageBuilder.withContent(
                org.thederps.testing.extensions.any<String>())
        ).thenReturn(messageBuilder)
        controller = BotController(clientRetriever, messageCreator)
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
    fun testOnMessage_sendsMessageToSameChannel() {
        `when`(message.content).thenReturn(BotController.COMMAND_HELP)
        val channel = mock(Channel::class.java)
        `when`(message.channel).thenReturn(channel)
        `when`(user.isBot).thenReturn(false)
        controller.setUp(authenticator)

        controller.onMessage(messageEvent)

        verify(messageBuilder).withChannel(channel)
        verify(messageBuilder).build()
    }

    @Test
    fun testOnMessage_doesNotSendMessageWhenCommandIsHelpUserIsBot() {
        `when`(message.content).thenReturn(BotController.COMMAND_HELP)
        val channel = mock(Channel::class.java)
        `when`(message.channel).thenReturn(channel)
        `when`(user.isBot).thenReturn(true)
        controller.setUp(authenticator)

        controller.onMessage(messageEvent)

        verify(messageBuilder, Mockito.times(0)).withChannel(channel)
        verify(messageBuilder, Mockito.times(0)).build()
    }

    @Test
    fun testOnMessage_doesNotSendMessageWhenCommandNotHelp() {
        `when`(message.content).thenReturn("__")
        val channel = mock(Channel::class.java)
        `when`(message.channel).thenReturn(channel)
        `when`(user.isBot).thenReturn(false)
        controller.setUp(authenticator)

        controller.onMessage(messageEvent)

        verify(messageBuilder, Mockito.times(0)).withChannel(channel)
        verify(messageBuilder, Mockito.times(0)).build()
    }

    @Test
    fun testOnMessage_doesNotSendMessageWhenCommandNotHelpAndUserIsBot() {
        `when`(message.content).thenReturn("__")
        val channel = mock(Channel::class.java)
        `when`(message.channel).thenReturn(channel)
        `when`(user.isBot).thenReturn(true)
        controller.setUp(authenticator)

        controller.onMessage(messageEvent)

        verify(messageBuilder, Mockito.times(0)).withChannel(channel)
        verify(messageBuilder, Mockito.times(0)).build()
    }
}