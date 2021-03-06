package org.thederps.module

import org.slf4j.LoggerFactory
import org.thederps.Authenticator
import org.thederps.module.receiver.DisconnectReceiver
import org.thederps.tools.AsyncRunner
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.handle.impl.events.DiscordDisconnectedEvent
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas on 2016-10-08.
 */
class ReconnectModule(
        val authenticator: Authenticator,
        val asyncRunner: AsyncRunner
) : Module, DisconnectReceiver {
    private val log = LoggerFactory.getLogger(ReconnectModule::class.java)

    override val command = ""

    override val description = "Tries to automatically reconnect the bot on a disconnect event"

    override fun getName() = "Reconnect"

    override fun enable(client: IDiscordClient) = true

    override fun getVersion() = "1.0"

    override fun getMinimumDiscord4JVersion() = "2.6.1"

    override fun getAuthor() = "Vidmantas K."

    override fun disable() {
        // Do nothing
    }

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