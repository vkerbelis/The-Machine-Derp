package org.thederps

import org.slf4j.LoggerFactory
import org.thederps.client.ClientRetriever
import org.thederps.module.Module
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.util.DiscordException
import java.util.*

/**
 * @author Vidmantas K. on 2016-10-07.
 */
class BotController(val clientRetriever: ClientRetriever) {
    private val log = LoggerFactory.getLogger(BotController::class.java)
    private val activeModules = HashMap<String, Module>()
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

    fun launchModule(module: Module) {
        client.dispatcher.registerListener(module)
        activeModules.putIfAbsent(module.key, module)
    }
}