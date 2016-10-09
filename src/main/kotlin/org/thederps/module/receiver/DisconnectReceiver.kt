package org.thederps.module.receiver

import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.DiscordDisconnectedEvent

/**
 * @author Vidmantas on 2016-10-08.
 */
interface DisconnectReceiver {
    @EventSubscriber
    fun onDisconnect(event: DiscordDisconnectedEvent)
}
