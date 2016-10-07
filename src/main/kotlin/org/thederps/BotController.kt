package org.thederps

import org.slf4j.LoggerFactory
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas K. on 2016-10-07.
 */
class BotController(val authenticator: Authenticator) {
    private val log = LoggerFactory.getLogger(BotController::class.java)

    fun launch() {
        try {
            authenticator.login()
        } catch (cause: DiscordException) {
            log.warn("Could not launch bot", cause)
        }
    }
}