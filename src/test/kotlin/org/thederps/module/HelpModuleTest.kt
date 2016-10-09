package org.thederps.module

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.thederps.testing.extensions.any
import org.thederps.tools.MessageCreator
import org.thederps.tools.withCommand
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
    private lateinit var moduleRetriever: ModuleRetriever
    private lateinit var messageBuilder: MessageBuilder
    private lateinit var messageCreator: MessageCreator
    private lateinit var messageEvent: MessageReceivedEvent
    private lateinit var message: IMessage
    private lateinit var module: HelpModule
    private lateinit var client: IDiscordClient
    private lateinit var user: IUser

    @Before
    fun setUp() {
        moduleRetriever = mock(ModuleRetriever::class.java)
        messageBuilder = mock(MessageBuilder::class.java)
        messageCreator = mock(MessageCreator::class.java)
        messageEvent = mock(MessageReceivedEvent::class.java)
        message = mock(IMessage::class.java)
        client = mock(IDiscordClient::class.java)
        user = mock(IUser::class.java)
        `when`(messageEvent.message).thenReturn(message)
        `when`(message.author).thenReturn(user)
        `when`(messageCreator.with(client)).thenReturn(messageBuilder)
        `when`(messageEvent.message).thenReturn(message)
        `when`(messageEvent.client).thenReturn(client)
        `when`(messageBuilder.withChannel(
                any<Channel>())
        ).thenReturn(messageBuilder)
        `when`(messageBuilder.withContent(
                any<String>())
        ).thenReturn(messageBuilder)
        module = HelpModule(messageCreator, moduleRetriever)
    }

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
    fun testEnable_returnsTrue() {
        val enabled = module.enable(client)

        Assert.assertTrue("Disabled", enabled)
    }

    @Test
    fun testDisable() {
        module.disable()
    }

    @Test
    fun testGetCommand_empty() {
        Assert.assertTrue("Command empty", !module.command.isEmpty())
    }

    @Test
    fun testGetCommand_startsWithCommandPrefix() {
        Assert.assertTrue("Doesn't start with prefix", module.command.startsWith("!"))
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
    fun testOnMessage_sendsMessageToSameChannel() {
        setUpMessagePass()
        val channel = mock(Channel::class.java)
        `when`(message.channel).thenReturn(channel)

        module.onMessage(messageEvent)

        verify(messageBuilder).withChannel(channel)
        verify(messageBuilder).build()
    }

    @Test
    fun testOnMessage_doesNotSendMessageWhenCommandIsHelpUserIsBot() {
        `when`(message.content).thenReturn(module.command)
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

    @Test
    fun testOnMessage_appendsMessageWithModuleCommands() {
        setUpMessagePass()
        val listModule = mock(Module::class.java)
        `when`(listModule.command).thenReturn("command")
        `when`(listModule.description).thenReturn("")
        val expectedModules = listOf<Module>(listModule)
        `when`(moduleRetriever.getModules()).thenReturn(expectedModules)

        module.onMessage(messageEvent)
        verify(messageBuilder).withCommand(1, listModule.command, listModule.description)
    }

    @Test
    fun testOnMessage_skipsNonExecutableCommandAppend() {
        setUpMessagePass()
        val listModule = mock(Module::class.java)
        `when`(listModule.command).thenReturn("")
        `when`(listModule.description).thenReturn("description")
        val expectedModules = listOf<Module>(listModule)
        `when`(moduleRetriever.getModules()).thenReturn(expectedModules)

        module.onMessage(messageEvent)
        verify(messageBuilder, Mockito.times(0)).withCommand(1, listModule.command, listModule.description)
    }

    private fun setUpMessagePass() {
        `when`(message.content).thenReturn(module.command)
        `when`(user.isBot).thenReturn(false)
    }
}




