package org.thederps.tools

import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.modules.IModule

/**
 * @author Vidmantas on 2016-10-09.
 */
fun IModule.commandValid(event: MessageReceivedEvent, command: String): Boolean {
    val message = event.message
    return message.content.startsWith(command) && !message.author.isBot
}