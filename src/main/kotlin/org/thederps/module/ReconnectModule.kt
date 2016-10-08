package org.thederps.module

import org.slf4j.LoggerFactory
import org.thederps.Authenticator
import org.thederps.tools.AsyncRunner
import sx.blah.discord.handle.impl.events.DiscordDisconnectedEvent
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas on 2016-10-08.
 */
class ReconnectModule(val authenticator: Authenticator, val asyncRunner: AsyncRunner) : Module, DisconnectReceiver {
    private val log = LoggerFactory.getLogger(ReconnectModule::class.java)
    override val key = "Reconnect"

    override fun onDisconnect(event: DiscordDisconnectedEvent) {
        asyncRunner.run({
            if (!authenticator.isTerminated()) {
                log.info("Reconnecting bot")
                try {
                    authenticator.login(event.client)
                } catch (cause: DiscordException) {
                    log.warn("Failed to reconnect bot", cause)
                }
            }
        })
    }

}