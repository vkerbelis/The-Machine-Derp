package org.thederps.tools

import org.thederps.module.Module
import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.util.MessageBuilder

/**
 * @author Vidmantas on 2016-10-09.
 */
fun Module.commandValid(event: MessageReceivedEvent): Boolean {
    val message = event.message
    return message.content.startsWith(this.command) && !message.author.isBot
}

fun MessageBuilder.withCommand(position: Int, command: String) {
    this.withContent(position.toString() + ". " + command + "\n")
}