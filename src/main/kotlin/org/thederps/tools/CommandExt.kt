package org.thederps.tools

import org.thederps.module.Module
import org.thederps.module.ModuleRetriever
import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.util.MessageBuilder

/**
 * @author Vidmantas on 2016-10-09.
 */
fun Module.commandValid(event: MessageReceivedEvent): Boolean {
    val message = event.message
    return message.content.startsWith(this.command) && !message.author.isBot
}

fun MessageBuilder.appendCommands(moduleRetriever: ModuleRetriever): MessageBuilder {
    var position = 1
    moduleRetriever.getModules().forEach { module ->
        if (module.hasExecutableCommand()) {
            appendCommand(position, module.command, module.description)
            position++
        }
    }
    return this
}

fun MessageBuilder.appendCommand(position: Int, command: String, description: String) {
    this.appendContent(position.toString() + ". " + command + " - " + description + "\n")
}

fun Module.hasExecutableCommand() = !command.isEmpty()