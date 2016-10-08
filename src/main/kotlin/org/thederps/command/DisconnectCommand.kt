package org.thederps.command

import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.DiscordDisconnectedEvent

/**
 * @author Vidmantas on 2016-10-08.
 */
interface DisconnectCommand : Command {
    @EventSubscriber
    fun onDisconnect(event: DiscordDisconnectedEvent)
}
