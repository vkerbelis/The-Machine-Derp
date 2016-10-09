package org.thederps.module

import org.thederps.module.receiver.MessageReceiver
import org.thederps.tools.MessageCreator
import org.thederps.tools.commandValid
import org.thederps.tools.hasExecutableCommand
import org.thederps.tools.withCommand
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.util.MessageBuilder

/**
 * @author Vidmantas on 2016-10-09.
 */
class HelpModule(
        val messageCreator: MessageCreator,
        val moduleRetriever: ModuleRetriever
) : Module, MessageReceiver {
    override val command = "!help"

    override val description = "Shows the available commands"

    override fun getName() = "Help"

    override fun enable(client: IDiscordClient) = true

    override fun getVersion() = "1.0"

    override fun getMinimumDiscord4JVersion() = "2.6.1"

    override fun getAuthor() = "Vidmantas K."

    override fun disable() {
        // Do nothing
    }

    @EventSubscriber
    override fun onMessage(event: MessageReceivedEvent) {
        val message = event.message
        if (commandValid(event)) {
            val builder = messageCreator.with(event.client)
                    .withChannel(message.channel)
                    .withContent("Hi there, I can accept the following commands:\n")
            appendCommands(builder)
            builder.build()
        }
    }

    private fun appendCommands(builder: MessageBuilder) {
        var position = 1
        moduleRetriever.getModules().forEach { module ->
            if (module.hasExecutableCommand()) {
                builder.withCommand(position, module.command, module.description)
                position++
            }
        }
    }
}
