package org.thederps.testing

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.thederps.module.Module
import org.thederps.module.ModuleRetriever
import org.thederps.testing.extensions.any
import org.thederps.tools.MessageCreator
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.handle.impl.obj.Channel
import sx.blah.discord.handle.obj.IChannel
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.MessageBuilder

/**
 * @author Vidmantas on 2016-10-09.
 */
abstract class CommandModuleTest<out M : Module> : ModuleTest<M>() {
    lateinit var moduleRetriever: ModuleRetriever
    lateinit var messageBuilder: MessageBuilder
    lateinit var messageCreator: MessageCreator
    lateinit var messageEvent: MessageReceivedEvent
    lateinit var message: IMessage
    lateinit var channel: IChannel
    lateinit var client: IDiscordClient
    lateinit var user: IUser

    @Before
    open fun setUp() {
        moduleRetriever = Mockito.mock(ModuleRetriever::class.java)
        messageBuilder = Mockito.mock(MessageBuilder::class.java)
        messageCreator = Mockito.mock(MessageCreator::class.java)
        messageEvent = Mockito.mock(MessageReceivedEvent::class.java)
        message = Mockito.mock(IMessage::class.java)
        channel = Mockito.mock(IChannel::class.java)
        client = Mockito.mock(IDiscordClient::class.java)
        user = Mockito.mock(IUser::class.java)
        Mockito.`when`(messageEvent.message).thenReturn(message)
        Mockito.`when`(message.channel).thenReturn(channel)
        Mockito.`when`(message.author).thenReturn(user)
        Mockito.`when`(messageCreator.with(client)).thenReturn(messageBuilder)
        Mockito.`when`(messageEvent.message).thenReturn(message)
        Mockito.`when`(messageEvent.client).thenReturn(client)
        Mockito.`when`(messageBuilder.withChannel(
                any<Channel>())
        ).thenReturn(messageBuilder)
        Mockito.`when`(messageBuilder.withContent(
                any<String>())
        ).thenReturn(messageBuilder)
        Mockito.`when`(messageBuilder.appendContent(
                any<String>())
        ).thenReturn(messageBuilder)
    }

    @Test
    fun testGetCommand_startsWithCommandPrefix() {
        Assert.assertTrue("Doesn't start with prefix", module.command.startsWith("!"))
    }
}
