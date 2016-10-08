package org.thederps

import org.slf4j.LoggerFactory
import org.thederps.client.ClientRetriever
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas K. on 2016-10-07.
 */
class BotController(val clientRetriever: ClientRetriever) {
    private val log = LoggerFactory.getLogger(BotController::class.java)

    fun launch(authenticator: Authenticator) {
        try {
            authenticator.login(clientRetriever.getClient())
        } catch (cause: DiscordException) {
            log.warn("Could not launch bot", cause)
        }
    }
}