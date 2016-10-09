package org.thederps

import org.slf4j.LoggerFactory
import org.thederps.client.ClientRetriever
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.modules.IModule
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas K. on 2016-10-07.
 */
class BotController(val clientRetriever: ClientRetriever) {
    private val log = LoggerFactory.getLogger(BotController::class.java)
    private lateinit var client: IDiscordClient

    fun setUp(authenticator: Authenticator): Boolean {
        try {
            client = clientRetriever.getClient()
            authenticator.login(client)
            return true
        } catch (cause: DiscordException) {
            log.warn("Could not launch bot", cause)
        }
        return false
    }

    fun launchModule(module: IModule) {
        client.moduleLoader.loadModule(module)
    }
}