package org.thederps.command

import org.slf4j.LoggerFactory
import org.thederps.Authenticator
import org.thederps.tools.AsyncRunner
import sx.blah.discord.handle.impl.events.DiscordDisconnectedEvent
import sx.blah.discord.util.DiscordException
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author Vidmantas on 2016-10-08.
 */
class ReconnectCommand(val authenticator: Authenticator, val asyncRunner: AsyncRunner) : DisconnectCommand {
    private val log = LoggerFactory.getLogger(ReconnectCommand::class.java)
    private val reconnect = AtomicBoolean(true)

    override fun onDisconnect(event: DiscordDisconnectedEvent) {
        asyncRunner.run({
            if (reconnect.get()) {
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