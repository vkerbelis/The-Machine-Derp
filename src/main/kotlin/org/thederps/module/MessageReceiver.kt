package org.thederps.module

import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.MessageReceivedEvent

/**
 * @author Vidmantas on 2016-10-08.
 */
interface MessageReceiver {
    @EventSubscriber
    fun onMessage(event: MessageReceivedEvent)
}