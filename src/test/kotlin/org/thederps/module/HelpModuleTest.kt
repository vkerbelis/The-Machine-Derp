package org.thederps.module

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.thederps.testing.extensions.any
import org.thederps.tools.MessageCreator
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.handle.impl.obj.Channel
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.MessageBuilder

/**
 * @author Vidmantas on 2016-10-09.
 */
class HelpModuleTest {
    private lateinit var messageBuilder: MessageBuilder
    private lateinit var messageEvent: MessageReceivedEvent
    private lateinit var message: IMessage
    private lateinit var module: HelpModule
    private lateinit var client: IDiscordClient
    private lateinit var user: IUser

    @Before
    fun setUp() {
        messageBuilder = mock(MessageBuilder::class.java)
        messageEvent = mock(MessageReceivedEvent::class.java)
        message = mock(IMessage::class.java)
        client = mock(IDiscordClient::class.java)
        user = mock(IUser::class.java)
        `when`(messageEvent.message).thenReturn(message)
        `when`(message.author).thenReturn(user)
        val messageCreator = mock(MessageCreator::class.java)
        `when`(messageCreator.with(client)).thenReturn(messageBuilder)
        `when`(messageEvent.message).thenReturn(message)
        `when`(messageEvent.client).thenReturn(client)
        `when`(messageBuilder.withChannel(
                any<Channel>())
        ).thenReturn(messageBuilder)
        `when`(messageBuilder.withContent(
                any<String>())
        ).thenReturn(messageBuilder)
        module = HelpModule(messageCreator)
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
    fun testOnMessage_sendsMessageToSameChannel() {
        `when`(message.content).thenReturn(HelpModule.COMMAND_HELP)
        val channel = mock(Channel::class.java)
        `when`(message.channel).thenReturn(channel)
        `when`(user.isBot).thenReturn(false)

        module.onMessage(messageEvent)

        verify(messageBuilder).withChannel(channel)
        verify(messageBuilder).build()
    }

    @Test
    fun testOnMessage_doesNotSendMessageWhenCommandIsHelpUserIsBot() {
        `when`(message.content).thenReturn(HelpModule.COMMAND_HELP)
        val channel = mock(Channel::class.java)
        `when`(message.channel).thenReturn(channel)
        `when`(user.isBot).thenReturn(true)

        module.onMessage(messageEvent)

        verify(messageBuilder, Mockito.times(0)).withChannel(channel)
        verify(messageBuilder, Mockito.times(0)).build()
    }

    @Test
    fun testOnMessage_doesNotSendMessageWhenCommandNotHelp() {
        `when`(message.content).thenReturn("__")
        val channel = mock(Channel::class.java)
        `when`(message.channel).thenReturn(channel)
        `when`(user.isBot).thenReturn(false)

        module.onMessage(messageEvent)

        verify(messageBuilder, Mockito.times(0)).withChannel(channel)
        verify(messageBuilder, Mockito.times(0)).build()
    }

    @Test
    fun testOnMessage_doesNotSendMessageWhenCommandNotHelpAndUserIsBot() {
        `when`(message.content).thenReturn("__")
        val channel = mock(Channel::class.java)
        `when`(message.channel).thenReturn(channel)
        `when`(user.isBot).thenReturn(true)

        module.onMessage(messageEvent)

        verify(messageBuilder, Mockito.times(0)).withChannel(channel)
        verify(messageBuilder, Mockito.times(0)).build()
    }
}




